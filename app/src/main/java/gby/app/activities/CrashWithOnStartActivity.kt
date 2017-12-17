package gby.app.activities

import gby.app.R

class CrashWithOnStartActivity : CrashActivity() {
    override fun getTitleId(): Int {
        return R.string.crash_with_activity_onStart
    }

    override fun onStart() {
        super.onStart()
        throw RuntimeException(getString(getTitleId()))
    }
}