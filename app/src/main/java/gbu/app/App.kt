package gbu.app

import android.app.Application
import android.content.Context
import god.bless.you.app.GbuInstrumentation

class App : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        GbuInstrumentation.init()
    }
}