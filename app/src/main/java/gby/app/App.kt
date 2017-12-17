package gby.app

import android.app.Application
import android.content.Context
import god.bless.you.GodInstrumentation

class App : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        GodInstrumentation.init()
    }
}