package gby.app.activities

import gby.app.R

class CrashWithOnPauseActivity : CrashActivity() {
    override fun getTitleId(): Int {
        return R.string.crash_with_activity_onPause
    }

    override fun onPause() {
        super.onPause()
        throw RuntimeException(getString(getTitleId()))
    }
}