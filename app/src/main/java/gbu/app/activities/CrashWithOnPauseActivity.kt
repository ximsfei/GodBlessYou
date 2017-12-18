package gbu.app.activities

import gbu.app.R

class CrashWithOnPauseActivity : CrashActivity() {
    override fun getTitleId(): Int {
        return R.string.crash_with_activity_onPause
    }

    override fun getContentId(): Int {
        return R.string.fix_with_activity_onPause
    }

    override fun onPause() {
        super.onPause()
        throw RuntimeException(getString(getTitleId()))
    }
}