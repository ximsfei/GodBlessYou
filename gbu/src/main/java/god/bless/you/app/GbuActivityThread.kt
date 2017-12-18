package god.bless.you.app

import android.app.Instrumentation
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.util.ArrayMap
import god.bless.you.Gbu
import god.bless.you.content.GbuBroadcastReceiver
import god.bless.you.util.GbuLog
import java.lang.reflect.Field
import java.lang.reflect.Method

object GbuActivityThread {
    /** Type for IActivityManager.serviceDoneExecuting: anonymous operation  */
    private val SERVICE_DONE_EXECUTING_ANON = 0
    /** Type for IActivityManager.serviceDoneExecuting: done with an onStart call  */
    private val SERVICE_DONE_EXECUTING_START = 1
    /** Type for IActivityManager.serviceDoneExecuting: done stopping (destroying) service  */
    private val SERVICE_DONE_EXECUTING_STOP = 2

    private val LAUNCH_ACTIVITY = 100
    private val PAUSE_ACTIVITY = 101
    private val PAUSE_ACTIVITY_FINISHING = 102
    private val STOP_ACTIVITY_SHOW = 103
    private val STOP_ACTIVITY_HIDE = 104
    private val SHOW_WINDOW = 105
    private val HIDE_WINDOW = 106
    private val RESUME_ACTIVITY = 107
    private val SEND_RESULT = 108
    private val DESTROY_ACTIVITY = 109
    private val BIND_APPLICATION = 110
    private val EXIT_APPLICATION = 111
    private val NEW_INTENT = 112
    private val RECEIVER = 113
    private val CREATE_SERVICE = 114
    private val SERVICE_ARGS = 115
    private val STOP_SERVICE = 116

    private val CONFIGURATION_CHANGED = 118
    private val CLEAN_UP_CONTEXT = 119
    private val GC_WHEN_IDLE = 120
    private val BIND_SERVICE = 121
    private val UNBIND_SERVICE = 122
    private val DUMP_SERVICE = 123
    private val LOW_MEMORY = 124
    private val ACTIVITY_CONFIGURATION_CHANGED = 125
    private val RELAUNCH_ACTIVITY = 126
    private val PROFILER_CONTROL = 127
    private val CREATE_BACKUP_AGENT = 128
    private val DESTROY_BACKUP_AGENT = 129
    private val SUICIDE = 130
    private val REMOVE_PROVIDER = 131
    private val ENABLE_JIT = 132
    private val DISPATCH_PACKAGE_BROADCAST = 133
    private val SCHEDULE_CRASH = 134
    private val DUMP_HEAP = 135
    private val DUMP_ACTIVITY = 136
    private val SLEEPING = 137
    private val SET_CORE_SETTINGS = 138
    private val UPDATE_PACKAGE_COMPATIBILITY_INFO = 139
    private val TRIM_MEMORY = 140
    private val DUMP_PROVIDER = 141
    private val UNSTABLE_PROVIDER_DIED = 142
    private val REQUEST_ASSIST_CONTEXT_EXTRAS = 143
    private val TRANSLUCENT_CONVERSION_COMPLETE = 144
    private val INSTALL_PROVIDER = 145
    private val ON_NEW_ACTIVITY_OPTIONS = 146
    private val CANCEL_VISIBLE_BEHIND = 147
    private val BACKGROUND_VISIBLE_BEHIND_CHANGED = 148
    private val ENTER_ANIMATION_COMPLETE = 149
    private val START_BINDER_TRACKING = 150
    private val STOP_BINDER_TRACKING_AND_DUMP = 151
    private val MULTI_WINDOW_MODE_CHANGED = 152
    private val PICTURE_IN_PICTURE_MODE_CHANGED = 153
    private val LOCAL_VOICE_INTERACTION_STARTED = 154
    private val ATTACH_AGENT = 155
    private val APPLICATION_INFO_CHANGED = 156
    private val ACTIVITY_MOVED_TO_DISPLAY = 157

    private var mActivityThreadClass: Class<*>? = null
    private var mCurrentActivityThreadMethod: Method? = null
    private var mInstrumentationField: Field? = null
    private var mServicesField: Field?
    private var mHField: Field? = null
    private var mMsg: Message? = null

    init {
        mActivityThreadClass = Class.forName("android.app.ActivityThread")
        mCurrentActivityThreadMethod = mActivityThreadClass?.getDeclaredMethod("currentActivityThread")
        mCurrentActivityThreadMethod?.isAccessible = true
        mInstrumentationField = mActivityThreadClass?.getDeclaredField("mInstrumentation")
        mInstrumentationField?.isAccessible = true
        mHField = mActivityThreadClass?.getDeclaredField("mH")
        mHField?.isAccessible = true
        mServicesField = mActivityThreadClass?.getDeclaredField("mServices")
        mServicesField?.isAccessible = true

        val mInstrumentation: Instrumentation = getInstrumentation()
        if (mInstrumentation !is GbuInstrumentationImpl) {
            val instrumentationImpl = GbuInstrumentationImpl(mInstrumentation)
            mInstrumentationField?.set(currentActivityThread(), instrumentationImpl)
        }

        val mCallbackField = Handler::class.java.getDeclaredField("mCallback")
        mCallbackField.isAccessible = true
        val mCallback: Handler.Callback? = mCallbackField.get(getH()) as Handler.Callback?
        if (mCallback !is HCallback) {
            mCallbackField.set(getH(), HCallback(mCallback))
        }
    }

    fun currentActivityThread(): Any? {
        return mCurrentActivityThreadMethod?.invoke(null)
    }

    fun getInstrumentation(): Instrumentation {
        return mInstrumentationField?.get(currentActivityThread()) as Instrumentation
    }

    fun getServices(): ArrayMap<IBinder, Service> {
        return mServicesField?.get(currentActivityThread()) as ArrayMap<IBinder, Service>
    }

    fun getH(): Handler {
        return mHField?.get(currentActivityThread()) as Handler
    }

    private fun codeToString(code: Int): String {
        when (code) {
            LAUNCH_ACTIVITY -> return "LAUNCH_ACTIVITY"
            PAUSE_ACTIVITY -> return "PAUSE_ACTIVITY"
            PAUSE_ACTIVITY_FINISHING -> return "PAUSE_ACTIVITY_FINISHING"
            STOP_ACTIVITY_SHOW -> return "STOP_ACTIVITY_SHOW"
            STOP_ACTIVITY_HIDE -> return "STOP_ACTIVITY_HIDE"
            SHOW_WINDOW -> return "SHOW_WINDOW"
            HIDE_WINDOW -> return "HIDE_WINDOW"
            RESUME_ACTIVITY -> return "RESUME_ACTIVITY"
            SEND_RESULT -> return "SEND_RESULT"
            DESTROY_ACTIVITY -> return "DESTROY_ACTIVITY"
            BIND_APPLICATION -> return "BIND_APPLICATION"
            EXIT_APPLICATION -> return "EXIT_APPLICATION"
            NEW_INTENT -> return "NEW_INTENT"
            RECEIVER -> return "RECEIVER"
            CREATE_SERVICE -> return "CREATE_SERVICE"
            SERVICE_ARGS -> return "SERVICE_ARGS"
            STOP_SERVICE -> return "STOP_SERVICE"
            CONFIGURATION_CHANGED -> return "CONFIGURATION_CHANGED"
            CLEAN_UP_CONTEXT -> return "CLEAN_UP_CONTEXT"
            GC_WHEN_IDLE -> return "GC_WHEN_IDLE"
            BIND_SERVICE -> return "BIND_SERVICE"
            UNBIND_SERVICE -> return "UNBIND_SERVICE"
            DUMP_SERVICE -> return "DUMP_SERVICE"
            LOW_MEMORY -> return "LOW_MEMORY"
            ACTIVITY_CONFIGURATION_CHANGED -> return "ACTIVITY_CONFIGURATION_CHANGED"
            ACTIVITY_MOVED_TO_DISPLAY -> return "ACTIVITY_MOVED_TO_DISPLAY"
            RELAUNCH_ACTIVITY -> return "RELAUNCH_ACTIVITY"
            PROFILER_CONTROL -> return "PROFILER_CONTROL"
            CREATE_BACKUP_AGENT -> return "CREATE_BACKUP_AGENT"
            DESTROY_BACKUP_AGENT -> return "DESTROY_BACKUP_AGENT"
            SUICIDE -> return "SUICIDE"
            REMOVE_PROVIDER -> return "REMOVE_PROVIDER"
            ENABLE_JIT -> return "ENABLE_JIT"
            DISPATCH_PACKAGE_BROADCAST -> return "DISPATCH_PACKAGE_BROADCAST"
            SCHEDULE_CRASH -> return "SCHEDULE_CRASH"
            DUMP_HEAP -> return "DUMP_HEAP"
            DUMP_ACTIVITY -> return "DUMP_ACTIVITY"
            SLEEPING -> return "SLEEPING"
            SET_CORE_SETTINGS -> return "SET_CORE_SETTINGS"
            UPDATE_PACKAGE_COMPATIBILITY_INFO -> return "UPDATE_PACKAGE_COMPATIBILITY_INFO"
            TRIM_MEMORY -> return "TRIM_MEMORY"
            DUMP_PROVIDER -> return "DUMP_PROVIDER"
            UNSTABLE_PROVIDER_DIED -> return "UNSTABLE_PROVIDER_DIED"
            REQUEST_ASSIST_CONTEXT_EXTRAS -> return "REQUEST_ASSIST_CONTEXT_EXTRAS"
            TRANSLUCENT_CONVERSION_COMPLETE -> return "TRANSLUCENT_CONVERSION_COMPLETE"
            INSTALL_PROVIDER -> return "INSTALL_PROVIDER"
            ON_NEW_ACTIVITY_OPTIONS -> return "ON_NEW_ACTIVITY_OPTIONS"
            CANCEL_VISIBLE_BEHIND -> return "CANCEL_VISIBLE_BEHIND"
            BACKGROUND_VISIBLE_BEHIND_CHANGED -> return "BACKGROUND_VISIBLE_BEHIND_CHANGED"
            ENTER_ANIMATION_COMPLETE -> return "ENTER_ANIMATION_COMPLETE"
            START_BINDER_TRACKING -> return "START_BINDER_TRACKING"
            STOP_BINDER_TRACKING_AND_DUMP -> return "STOP_BINDER_TRACKING_AND_DUMP"
            MULTI_WINDOW_MODE_CHANGED -> return "MULTI_WINDOW_MODE_CHANGED"
            PICTURE_IN_PICTURE_MODE_CHANGED -> return "PICTURE_IN_PICTURE_MODE_CHANGED"
            LOCAL_VOICE_INTERACTION_STARTED -> return "LOCAL_VOICE_INTERACTION_STARTED"
            ATTACH_AGENT -> return "ATTACH_AGENT"
            APPLICATION_INFO_CHANGED -> return "APPLICATION_INFO_CHANGED"
        }
        return code.toString()
    }

    fun handleException(obj: Any?) {
        if (Gbu.debug) {
            GbuLog.d(codeToString(mMsg?.what!!))
        }
        if (obj is Service) {
            when (mMsg?.what) {
                CREATE_SERVICE -> handleCreateService(obj)
                SERVICE_ARGS -> handleServiceArgs(obj)
                STOP_SERVICE -> handleStopService(obj)
                BIND_SERVICE -> handleBindService(obj)
                UNBIND_SERVICE -> handleUnbindService(obj)
            }
        } else if (obj is BroadcastReceiver) {
            GbuBroadcastReceiver.setPendingResult(obj, null)
        }
    }

    private fun handleCreateService(service: Service) {
        val token = GbuService.getToken(service)
        getServices().put(token, service)
        GbuActivityManager.serviceDoneExecuting(token, SERVICE_DONE_EXECUTING_ANON, 0, 0)
    }

    private fun handleServiceArgs(service: Service) {
        val token = GbuService.getToken(service)
        GbuActivityManager.serviceDoneExecuting(token, SERVICE_DONE_EXECUTING_START, 0, 0)
    }

    private fun handleStopService(service: Service) {
        val token = GbuService.getToken(service)
        GbuService.detachAndCleanUp(service)
        val context = service.baseContext
        if (GbuContextImpl.isContextImpl(context)) {
            val who = service.javaClass.name
            GbuContextImpl.scheduleFinalCleanup(context, who, "Service")
        }

        GbuActivityManager.serviceDoneExecuting(token, SERVICE_DONE_EXECUTING_STOP, 0, 0)
    }

    private fun handleBindService(service: Service) {
        val token = GbuService.getToken(service)
        val rebindField = mMsg?.obj!!.javaClass.getDeclaredField("rebind")
        rebindField.isAccessible = true
        if (!(rebindField.get(mMsg?.obj) as Boolean)) {
            val intentField = mMsg?.obj!!.javaClass.getDeclaredField("intent")
            intentField.isAccessible = true
            GbuActivityManager.publishService(token, intentField.get(mMsg?.obj) as Intent, null)
        } else {
            GbuActivityManager.serviceDoneExecuting(token, SERVICE_DONE_EXECUTING_ANON, 0, 0)
        }
    }

    private fun handleUnbindService(service: Service) {
        val token = GbuService.getToken(service)
        // TODO: How do I get the unbind method return value
//        val intentField = mMsg?.obj!!.javaClass.getDeclaredField("intent")
//        intentField.isAccessible = true
//        val doRebind = service.onUnbind(intentField.get(mMsg?.obj) as Intent)
//        if (doRebind) {
//            GbuActivityManager.unbindFinished(
//                    token, intentField.get(mMsg?.obj) as Intent, doRebind)
//        } else {
        GbuActivityManager.serviceDoneExecuting(
                token, SERVICE_DONE_EXECUTING_ANON, 0, 0)
//        }
    }

    private class HCallback(private val mBase: Handler.Callback?) : Handler.Callback {
        override fun handleMessage(msg: Message?): Boolean {
            mMsg = msg
            return mBase?.handleMessage(msg) ?: false
        }
    }
}