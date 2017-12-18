# GodBlessYou

![gbu-0.0.1](https://img.shields.io/badge/gbu-0.0.1-green.svg)
![build](https://img.shields.io/badge/build-passing-green.svg)
![license-apache-2.0](https://img.shields.io/badge/apache--2.0-blue.svg)

[In English](README.md) | 中文

GBU 尝试在运行时修复Android应用的崩溃问题，在使部分功能失效的前提下，让应用继续运行。使用GBU以后，应用将不再会出现"X 已停止运行"的对话框。

你可以下载[demo apk]()来体验一下.

## 内容

* [导入](#导入)
* [使用](#使用)
* [实现原理](#实现原理)
  * [修复Activity中的Crash](#修复activity中的crash)
  * [修复Service中的Crash](#修复service和broadcastreceiver中的crash)
  * [修复BroadcastReceiver中的Crash](#修复service和broadcastreceiver中的crash)
  * [修复其他Crash](#修复其他crash)
* [关于作者](#关于作者)
* [LICENSE](#license-apache-2.0)

## 导入

The Gradle Dependency is available via [jCenter](https://bintray.com/pengfeng/ximsfei/gbu)

Add dependencies directly

```
implementation 'god.bless.you:gbu:0.0.1'
```

## 使用

Integrate with kotlin

```
class App : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Gbu
    }
}
```

## 实现原理

### 修复Activity中的Crash

Gbu 'fix' the crash in the Activity lifecycle by hooking `ActivityThread`'s member `mInstrumentation`

> GbuInstrumentationImpl.kt

```kotlin
override fun callActivityOnCreate(activity: Activity?, icicle: Bundle?) {
    try {
        super.callActivityOnCreate(activity, icicle)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

override fun callActivityOnCreate(activity: Activity?, icicle: Bundle?, persistentState: PersistableBundle?) {
    try {
        super.callActivityOnCreate(activity, icicle, persistentState)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

override fun callActivityOnNewIntent(activity: Activity?, intent: Intent?) {
    try {
        super.callActivityOnNewIntent(activity, intent)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

override fun callActivityOnRestart(activity: Activity?) {
    try {
        super.callActivityOnRestart(activity)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

override fun callActivityOnStart(activity: Activity?) {
    try {
        super.callActivityOnStart(activity)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

override fun callActivityOnResume(activity: Activity?) {
    try {
        super.callActivityOnResume(activity)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

override fun callActivityOnPause(activity: Activity?) {
    try {
        super.callActivityOnPause(activity)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

override fun callActivityOnStop(activity: Activity?) {
    try {
        super.callActivityOnStop(activity)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

override fun callActivityOnDestroy(activity: Activity?) {
    try {
        super.callActivityOnDestroy(activity)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

override fun callActivityOnPostCreate(activity: Activity?, icicle: Bundle?) {
    try {
        super.callActivityOnPostCreate(activity, icicle)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

override fun callActivityOnPostCreate(activity: Activity?, icicle: Bundle?, persistentState: PersistableBundle?) {
    try {
        super.callActivityOnPostCreate(activity, icicle, persistentState)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

override fun callActivityOnSaveInstanceState(activity: Activity?, outState: Bundle?) {
    try {
        super.callActivityOnSaveInstanceState(activity, outState)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

override fun callActivityOnSaveInstanceState(activity: Activity?, outState: Bundle?, outPersistentState: PersistableBundle?) {
    try {
        super.callActivityOnSaveInstanceState(activity, outState, outPersistentState)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

override fun callActivityOnRestoreInstanceState(activity: Activity?, savedInstanceState: Bundle?) {
    try {
        super.callActivityOnRestoreInstanceState(activity, savedInstanceState)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

override fun callActivityOnRestoreInstanceState(activity: Activity?, savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
    try {
        super.callActivityOnRestoreInstanceState(activity, savedInstanceState, persistentState)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}
```

### 修复Service和BroadcastReceiver中的Crash

Gbu 'fix' the crash in the Service&BroadcastReceiver lifecycle by hooking `Instrumentation`'s method `onException`

> GbuInstrumentationImpl.kt

```kotlin
class GbuInstrumentationImpl(base: Instrumentation) : GbuInstrumentationWrapper(base) {

    override fun onException(obj: Any?, e: Throwable?): Boolean {
        GbuActivityThread.handleException(obj)
        return true
    }
}
```

> GbuActivityThread.kt

```kotlin
object GbuActivityThread {
    fun handleException(obj: Any?) {
        if (Gbu.debug) {
            GbuLog.d(codeToString(mMsg?.what!!))
        }
        if (obj is Service) {
            when (mMsg?.what) {
                CREATE_SERVICE -> handleCreateService(obj)
                SERVICE_ARGS -> handleServiceArgs(obj)
                STOP_SERVICE -> handleStopService(obj)
                BIND_SERVICE -> handleBindService(obj)
                UNBIND_SERVICE -> handleUnbindService(obj)
            }
        } else if (obj is BroadcastReceiver) {
            GbuBroadcastReceiver.setPendingResult(obj, null)
        }
    }
}
```

### 修复其他Crash

```kotlin
object Gbu {
    init {
        Handler(Looper.getMainLooper()).post {
            while (true) {
                try {
                    Looper.loop()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
        Thread.setDefaultUncaughtExceptionHandler { _: Thread, throwable: Throwable ->
            throwable.printStackTrace()
        }
    }
}
```
## 关于作者

姓名: 王鹏锋

email: ximsfei@gmail.com

## [LICENSE Apache 2.0](LICENSE)