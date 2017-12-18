package gbu.app.services

import android.content.Intent
import gbu.app.R

class CrashWithOnStartService : CrashService() {
    override fun onStart(intent: Intent?, startId: Int) {
        throw RuntimeException(getString(R.string.crash_with_service_onStart))
    }
}