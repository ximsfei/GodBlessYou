package god.bless.you.app

import android.app.Instrumentation
import android.os.Handler
import android.os.Message
import god.bless.you.Gbu
import god.bless.you.util.GbuLog
import java.lang.reflect.Field
import java.lang.reflect.Method

object GbuActivityThread {
    val LAUNCH_ACTIVITY = 100
    val PAUSE_ACTIVITY = 101
    val PAUSE_ACTIVITY_FINISHING = 102
    val STOP_ACTIVITY_SHOW = 103
    val STOP_ACTIVITY_HIDE = 104
    val SHOW_WINDOW = 105
    val HIDE_WINDOW = 106
    val RESUME_ACTIVITY = 107
    val SEND_RESULT = 108
    val DESTROY_ACTIVITY = 109
    val BIND_APPLICATION = 110
    val EXIT_APPLICATION = 111
    val NEW_INTENT = 112
    val RECEIVER = 113
    val CREATE_SERVICE = 114
    val SERVICE_ARGS = 115
    val STOP_SERVICE = 116

    val CONFIGURATION_CHANGED = 118
    val CLEAN_UP_CONTEXT = 119
    val GC_WHEN_IDLE = 120
    val BIND_SERVICE = 121
    val UNBIND_SERVICE = 122
    val DUMP_SERVICE = 123
    val LOW_MEMORY = 124
    val ACTIVITY_CONFIGURATION_CHANGED = 125
    val RELAUNCH_ACTIVITY = 126
    val PROFILER_CONTROL = 127
    val CREATE_BACKUP_AGENT = 128
    val DESTROY_BACKUP_AGENT = 129
    val SUICIDE = 130
    val REMOVE_PROVIDER = 131
    val ENABLE_JIT = 132
    val DISPATCH_PACKAGE_BROADCAST = 133
    val SCHEDULE_CRASH = 134
    val DUMP_HEAP = 135
    val DUMP_ACTIVITY = 136
    val SLEEPING = 137
    val SET_CORE_SETTINGS = 138
    val UPDATE_PACKAGE_COMPATIBILITY_INFO = 139
    val TRIM_MEMORY = 140
    val DUMP_PROVIDER = 141
    val UNSTABLE_PROVIDER_DIED = 142
    val REQUEST_ASSIST_CONTEXT_EXTRAS = 143
    val TRANSLUCENT_CONVERSION_COMPLETE = 144
    val INSTALL_PROVIDER = 145
    val ON_NEW_ACTIVITY_OPTIONS = 146
    val CANCEL_VISIBLE_BEHIND = 147
    val BACKGROUND_VISIBLE_BEHIND_CHANGED = 148
    val ENTER_ANIMATION_COMPLETE = 149
    val START_BINDER_TRACKING = 150
    val STOP_BINDER_TRACKING_AND_DUMP = 151
    val MULTI_WINDOW_MODE_CHANGED = 152
    val PICTURE_IN_PICTURE_MODE_CHANGED = 153
    val LOCAL_VOICE_INTERACTION_STARTED = 154
    val ATTACH_AGENT = 155
    val APPLICATION_INFO_CHANGED = 156
    val ACTIVITY_MOVED_TO_DISPLAY = 157

    private var mActivityThreadClass: Class<*>? = null
    private var mCurrentActivityThreadMethod: Method? = null
    private var mInstrumentationField: Field? = null
    private var mHField: Field? = null
    private var mInitialized = false
    private var mCode = -1

    init {
        mActivityThreadClass = Class.forName("android.app.ActivityThread")
        mCurrentActivityThreadMethod = mActivityThreadClass?.getDeclaredMethod("currentActivityThread")
        mCurrentActivityThreadMethod?.isAccessible = true
        mInstrumentationField = mActivityThreadClass?.getDeclaredField("mInstrumentation")
        mInstrumentationField?.isAccessible = true
        mHField = mActivityThreadClass?.getDeclaredField("mH")
        mHField?.isAccessible = true
    }

    fun init() {
        if (!mInitialized) {
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
            mInitialized = true
        }
    }

    fun currentActivityThread(): Any? {
        return mCurrentActivityThreadMethod?.invoke(null)
    }

    fun getInstrumentation(): Instrumentation {
        return mInstrumentationField?.get(currentActivityThread()) as Instrumentation
    }

    fun getH(): Handler {
        return mHField?.get(currentActivityThread()) as Handler
    }

    fun getCode(): Int {
        return mCode
    }

    fun codeToString(code: Int): String {
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
            MULTI_WINDOW_MODE_CHANGED -> return "MULTI_WINDOW_MODE_CHANGED"
            PICTURE_IN_PICTURE_MODE_CHANGED -> return "PICTURE_IN_PICTURE_MODE_CHANGED"
            LOCAL_VOICE_INTERACTION_STARTED -> return "LOCAL_VOICE_INTERACTION_STARTED"
            ATTACH_AGENT -> return "ATTACH_AGENT"
            APPLICATION_INFO_CHANGED -> return "APPLICATION_INFO_CHANGED"
        }
        return code.toString()
    }

    private class HCallback(private val mBase: Handler.Callback?) : Handler.Callback {
        override fun handleMessage(msg: Message?): Boolean {
            mCode = msg?.what!!
            if (Gbu.DEBUG) {
                GbuLog.d(codeToString(mCode))
            }
            return mBase?.handleMessage(msg) ?: false
        }

    }
}