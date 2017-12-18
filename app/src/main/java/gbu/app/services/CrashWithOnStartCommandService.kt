package gbu.app.services

import android.content.Intent
import gbu.app.R

class CrashWithOnStartCommandService : CrashService() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        throw RuntimeException(getString(R.string.crash_with_service_onStartCommand))
    }
}