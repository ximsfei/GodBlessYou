package gbu.app.services

import android.content.Intent
import gbu.app.R

class CrashWithOnUnbindService : CrashService() {
    override fun onUnbind(intent: Intent?): Boolean {
        super.onUnbind(intent)
        throw RuntimeException(getString(R.string.crash_with_service_onUnbind))
    }
}