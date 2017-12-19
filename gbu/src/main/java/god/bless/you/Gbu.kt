package god.bless.you

import android.content.Context
import android.os.Handler
import android.os.Looper
import god.bless.you.app.GbuActivityThread

object Gbu {
    private var debug = false
    private var enableLifecycleCrash = true
    private var enableCommonCrash = false

    fun init(context: Context): Gbu {
        return this@Gbu
    }

    fun setDebug(debug: Boolean): Gbu {
        this.debug = debug
        return this@Gbu
    }

    fun enableLifecycleCrash(enable: Boolean): Gbu {
        enableLifecycleCrash = enable
        return this@Gbu
    }

    fun enableOtherCrash(enable: Boolean): Gbu {
        enableCommonCrash = enable
        return this@Gbu
    }

    fun install() {
        if (enableCommonCrash) {
            Handler(Looper.getMainLooper()).post {
                while (true) {
                    try {
                        Looper.loop()
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }
                }
            }
            Thread.setDefaultUncaughtExceptionHandler { _: Thread, throwable: Throwable ->
                throwable.printStackTrace()
            }
        }
        if (enableLifecycleCrash) {
            GbuActivityThread
        }
    }
}