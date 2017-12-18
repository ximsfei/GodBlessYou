package god.bless.you

import android.content.Context
import god.bless.you.app.ActivityThread

object Gbu {
    fun init(context: Context) {
        ActivityThread.init()
    }
}