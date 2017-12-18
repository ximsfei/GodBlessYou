package gbu.app.receivers

import android.content.Context
import android.content.Intent
import gbu.app.R

class CrashWithOnReceiveReceiver : CrashReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        throw RuntimeException(context?.getString(R.string.crash_with_receiver_onReceive))
    }
}