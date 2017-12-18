package god.bless.you.app

import android.app.Service
import android.os.IBinder
import java.lang.reflect.Field

object GbuService {
    private var mTokenField: Field? = null

    init {
        mTokenField = Service::class.java.getDeclaredField("mToken")
        mTokenField?.isAccessible = true
    }

    fun getToken(service: Service): IBinder {
        return mTokenField?.get(service) as IBinder
    }

    fun detachAndCleanUp(service: Service) {
        mTokenField?.set(service, null)
    }
}