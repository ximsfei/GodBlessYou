package gbu.app.services

import android.app.Service
import android.content.Intent
import android.os.IBinder

abstract class CrashService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}