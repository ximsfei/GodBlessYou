package gbu.app

import android.app.Application
import android.content.Context
import god.bless.you.Gbu
import java.io.File

class App : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        if (File(base?.filesDir, "open_gbu").exists()) {
            Gbu.init(this)
                    .setDebug(true)
                    .enableLifecycleCrash(true)
                    .enableOtherCrash(false)
                    .install()
        }
    }
}