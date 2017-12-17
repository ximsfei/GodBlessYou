package gby.app.activities

import gby.app.R

class CrashWithOnStopActivity : CrashActivity() {
    override fun getTitleId(): Int {
        return R.string.crash_with_activity_onStop
    }

    override fun onStop() {
        super.onStop()
        throw RuntimeException(getString(getTitleId()))
    }
}