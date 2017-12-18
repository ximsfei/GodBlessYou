package gbu.app

import android.app.Application
import android.content.Context
import god.bless.you.Gbu

class App : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Gbu.debug = true
    }
}