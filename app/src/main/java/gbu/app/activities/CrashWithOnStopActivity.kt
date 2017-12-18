package gbu.app.activities

import gbu.app.R

class CrashWithOnStopActivity : CrashActivity() {
    override fun getTitleId(): Int {
        return R.string.crash_with_activity_onStop
    }

    override fun getContentId(): Int {
        return R.string.fix_with_activity_onStop
    }

    override fun onStop() {
        super.onStop()
        throw RuntimeException(getString(getTitleId()))
    }
}