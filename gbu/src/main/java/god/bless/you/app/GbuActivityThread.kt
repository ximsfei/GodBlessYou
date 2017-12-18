package god.bless.you.app

import android.app.Instrumentation
import android.os.Handler
import android.os.Message
import java.lang.reflect.Field
import java.lang.reflect.Method

object GbuActivityThread {
    private var mActivityThreadClass: Class<*>? = null
    private var mCurrentActivityThreadMethod: Method? = null
    private var mInstrumentationField: Field? = null
    private var mHField: Field? = null
    private var mInitialized = false

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

    class HCallback(val mBase: Handler.Callback?) : Handler.Callback {
        override fun handleMessage(msg: Message?): Boolean {
            return false
        }

    }
}