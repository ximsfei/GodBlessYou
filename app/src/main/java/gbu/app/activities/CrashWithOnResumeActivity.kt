package gbu.app.activities

import gbu.app.R

class CrashWithOnResumeActivity : CrashActivity() {
    override fun getTitleId(): Int {
        return R.string.crash_with_activity_onResume
    }

    override fun onResume() {
        super.onResume()
        throw RuntimeException(getString(getTitleId()))
    }
}