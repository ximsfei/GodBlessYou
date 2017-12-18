package god.bless.you

import android.content.Context
import god.bless.you.app.GbuActivityThread

object Gbu {
    fun init(context: Context) {
        GbuActivityThread.init()
    }
}