package gbu.app.activities

import gbu.app.R

class CrashWithOnDestroyActivity : CrashActivity() {
    override fun getTitleId(): Int {
        return R.string.crash_with_activity_onDestroy
    }

    override fun getContentId(): Int {
        return R.string.fix_with_activity_onDestroy
    }

    override fun onDestroy() {
        super.onDestroy()
        throw RuntimeException(getString(getTitleId()))
    }

}