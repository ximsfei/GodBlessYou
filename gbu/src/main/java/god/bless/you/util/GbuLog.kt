package god.bless.you.util

import android.util.Log
import god.bless.you.Gbu

object GbuLog {
    private val TAG = GbuLog::class.simpleName
    fun d(msg: String) {
        if (Gbu.DEBUG) Log.d(TAG, msg)
    }
}