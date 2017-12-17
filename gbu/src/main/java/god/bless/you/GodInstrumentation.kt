package god.bless.you

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.app.Instrumentation
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle

class GodInstrumentation(base: Instrumentation) : InstrumentationImpl(base) {
    companion object {
        fun init() {
            Inner.init()
        }
    }
    private object Inner {
        @SuppressLint("PrivateApi")
        fun init() {
            val clazz =Class.forName("android.app.ActivityThread")
            val currentActivityThreadMethod = clazz.getDeclaredMethod("currentActivityThread")
            currentActivityThreadMethod.isAccessible = true
            val currentActivityThread = currentActivityThreadMethod.invoke(null)
            val mInstrumentationField = clazz.getDeclaredField("mInstrumentation")
            mInstrumentationField.isAccessible = true
            val mInstrumentation: Instrumentation = mInstrumentationField.get(currentActivityThread) as Instrumentation
            if (mInstrumentation !is GodInstrumentation) {
                val godInstrumentation = GodInstrumentation(mInstrumentation)
                mInstrumentationField.set(currentActivityThread, godInstrumentation)
            }
        }
    }

    override fun callApplicationOnCreate(app: Application?) {
        try {
            super.callApplicationOnCreate(app)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun callActivityOnCreate(activity: Activity?, icicle: Bundle?) {
        try {
            super.callActivityOnCreate(activity, icicle)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun callActivityOnCreate(activity: Activity?, icicle: Bundle?, persistentState: PersistableBundle?) {
        try {
            super.callActivityOnCreate(activity, icicle, persistentState)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun callActivityOnNewIntent(activity: Activity?, intent: Intent?) {
        try {
            super.callActivityOnNewIntent(activity, intent)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun callActivityOnRestart(activity: Activity?) {
        try {
            super.callActivityOnRestart(activity)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun callActivityOnStart(activity: Activity?) {
        try {
            super.callActivityOnStart(activity)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun callActivityOnResume(activity: Activity?) {
        try {
            super.callActivityOnResume(activity)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun callActivityOnPause(activity: Activity?) {
        try {
            super.callActivityOnPause(activity)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun callActivityOnStop(activity: Activity?) {
        try {
            super.callActivityOnStop(activity)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun callActivityOnDestroy(activity: Activity?) {
        try {
            super.callActivityOnDestroy(activity)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun callActivityOnPostCreate(activity: Activity?, icicle: Bundle?) {
        try {
            super.callActivityOnPostCreate(activity, icicle)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun callActivityOnPostCreate(activity: Activity?, icicle: Bundle?, persistentState: PersistableBundle?) {
        try {
            super.callActivityOnPostCreate(activity, icicle, persistentState)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun callActivityOnSaveInstanceState(activity: Activity?, outState: Bundle?) {
        try {
            super.callActivityOnSaveInstanceState(activity, outState)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun callActivityOnSaveInstanceState(activity: Activity?, outState: Bundle?, outPersistentState: PersistableBundle?) {
        try {
            super.callActivityOnSaveInstanceState(activity, outState, outPersistentState)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun callActivityOnRestoreInstanceState(activity: Activity?, savedInstanceState: Bundle?) {
        try {
            super.callActivityOnRestoreInstanceState(activity, savedInstanceState)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun callActivityOnRestoreInstanceState(activity: Activity?, savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        try {
            super.callActivityOnRestoreInstanceState(activity, savedInstanceState, persistentState)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}