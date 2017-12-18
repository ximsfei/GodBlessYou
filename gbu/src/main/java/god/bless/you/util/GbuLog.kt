package god.bless.you.util

import android.util.Log
import god.bless.you.Gbu

object GbuLog {
    private val TAG = "GbuLog"
    fun d(msg: String) {
        if (Gbu.debug) Log.d(TAG, msg)
    }
}