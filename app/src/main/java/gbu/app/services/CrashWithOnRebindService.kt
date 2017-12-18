package gbu.app.services

import android.content.Intent
import gbu.app.R

class CrashWithOnRebindService : CrashService() {
    override fun onUnbind(intent: Intent?): Boolean {
        return true
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        throw RuntimeException(getString(R.string.crash_with_service_onRebind))
    }
}