package god.bless.you

import android.os.Handler
import android.os.Looper
import god.bless.you.app.GbuActivityThread

object Gbu {
    var debug = false

    init {
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
        GbuActivityThread
    }
}