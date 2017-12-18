package god.bless.you.app

import android.content.Context
import java.lang.reflect.Method

object GbuContextImpl {
    private var mContextImplClass: Class<*>? = null
    private var mScheduleFinalCleanupMethod: Method? = null

    init {
        mContextImplClass = Class.forName("android.app.ContextImpl")
        mScheduleFinalCleanupMethod = mContextImplClass?.getDeclaredMethod("scheduleFinalCleanup", String::class.java, String::class.java)
        mScheduleFinalCleanupMethod?.isAccessible = true
    }

    fun isContextImpl(context: Context): Boolean {
        return context.javaClass == mContextImplClass
    }

    fun scheduleFinalCleanup(context: Context, who: String, what: String) {
        mScheduleFinalCleanupMethod?.invoke(context, who, what)
    }
}