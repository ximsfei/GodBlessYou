package god.bless.you.app

import android.app.Instrumentation
import java.lang.reflect.Field
import java.lang.reflect.Method

object ActivityThread {
    private var mActivityThreadClass: Class<*>? = null
    private var mCurrentActivityThreadMethod: Method? = null
    private var mInstrumentationField: Field? = null
    private var mInitialized = false

    fun init() {
        if (!mInitialized) {
            mActivityThreadClass = Class.forName("android.app.ActivityThread")
            mCurrentActivityThreadMethod = mActivityThreadClass?.getDeclaredMethod("currentActivityThread")
            mCurrentActivityThreadMethod?.isAccessible = true
            mInstrumentationField = mActivityThreadClass?.getDeclaredField("mInstrumentation")
            mInstrumentationField?.isAccessible = true
            val mInstrumentation: Instrumentation = getInstrumentation()
            if (mInstrumentation !is InstrumentationImpl) {
                val instrumentationImpl = InstrumentationImpl(mInstrumentation)
                mInstrumentationField?.set(currentActivityThread(), instrumentationImpl)
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
}