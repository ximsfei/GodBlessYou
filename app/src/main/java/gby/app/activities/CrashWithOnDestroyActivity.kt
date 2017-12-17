package gby.app.activities

import gby.app.R

class CrashWithOnDestroyActivity : CrashActivity() {
    override fun getTitleId(): Int {
        return R.string.crash_with_activity_onDestroy
    }

    override fun onDestroy() {
        super.onDestroy()
        throw RuntimeException(getString(getTitleId()))
    }

}