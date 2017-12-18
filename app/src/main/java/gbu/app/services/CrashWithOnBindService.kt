package gbu.app.services

import android.content.Intent
import android.os.IBinder
import gbu.app.R

class CrashWithOnBindService : CrashService() {
    override fun onBind(intent: Intent?): IBinder {
        super.onBind(intent)
        throw RuntimeException(getString(R.string.crash_with_service_onBind))
    }
}