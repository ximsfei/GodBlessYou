package god.bless.you.content

import android.content.BroadcastReceiver
import java.lang.reflect.Field

object GbuBroadcastReceiver {
    private var mPendingResultField: Field? = null

    init {
        mPendingResultField = BroadcastReceiver::class.java.getDeclaredField("mPendingResult")
        mPendingResultField?.isAccessible = true
    }

    fun setPendingResult(receiver: BroadcastReceiver, result: Any?) {
        mPendingResultField?.set(receiver, result)
    }
}