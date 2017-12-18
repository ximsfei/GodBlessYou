package gbu.app.services

import gbu.app.R

class CrashWithOnDestroyService : CrashService() {
    override fun onDestroy() {
        super.onDestroy()
        throw RuntimeException(getString(R.string.crash_with_service_onDestroy))
    }
}