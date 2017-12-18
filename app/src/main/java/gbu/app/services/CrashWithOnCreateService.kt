package gbu.app.services

import gbu.app.R

class CrashWithOnCreateService : CrashService() {
    override fun onCreate() {
        super.onCreate()
        throw RuntimeException(getString(R.string.crash_with_service_onCreate))
    }
}