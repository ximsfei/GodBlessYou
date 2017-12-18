package god.bless.you.app

import android.app.ActivityManager
import android.content.Intent
import android.os.Build
import android.os.IBinder
import java.lang.reflect.Method

object GbuActivityManager {
    private var mServiceDoneExecutingMethod: Method? = null
    private var mPublishServiceMethod: Method? = null

    init {
        val clazz = Class.forName("android.app.IActivityManager")
        mServiceDoneExecutingMethod = clazz.getDeclaredMethod(
                "serviceDoneExecuting", IBinder::class.java, Int::class.java, Int::class.java, Int::class.java)
        mServiceDoneExecutingMethod?.isAccessible = true
        mPublishServiceMethod = clazz.getDeclaredMethod("publishService", IBinder::class.java, Intent::class.java, IBinder::class.java)
        mPublishServiceMethod?.isAccessible = true
    }

    private fun get(): Any? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val method = ActivityManager::class.java.getDeclaredMethod("getServices")
            method.isAccessible = true
            method.invoke(null)
        } else {
            val method = Class.forName("android.app.ActivityManagerNative").getDeclaredMethod("getDefault")
            method.isAccessible = true
            method.invoke(null)
        }
    }

    fun publishService(token: IBinder, intent: Intent, binder: IBinder?) {
        mPublishServiceMethod?.invoke(get(), token, intent, binder)
    }

    fun serviceDoneExecuting(token: IBinder, type: Int, startId: Int, res: Int) {
        mServiceDoneExecutingMethod?.invoke(get(), token, type, startId, res)
    }
}