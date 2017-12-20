# GodBlessYou

![gbu](https://img.shields.io/badge/gbu-0.0.2-green.svg)
![build](https://img.shields.io/badge/build-passing-green.svg)
![license-apache-2.0](https://img.shields.io/badge/Apache-2.0-blue.svg)

In English | [中文](README_CN.md)

GBU is an Android library that tries to fix your app when it crashes. You will never get the hated "X has stopped" dialog.

You can download an [apk](https://raw.githubusercontent.com/ximsfei/Res/master/gbu/gbu.apk) of the demo project.

## Table of Contents

* [Gradle Dependency](#gradle-dependency)
* [Integration](#integration)
* [How it works](#how-it-works)
  * [Fix Activity Crash](#fix-activity-crash)
  * [Fix Service Crash](#fix-service-and-broadcastreceiver-crash)
  * [Fix BroadcastReceiver Crash](#fix-service-and-broadcastreceiver-crash)
  * [Fix Other Crash](#fix-other-crash)
* [About Author](#about-author)
* [LICENSE](#license-apache-20)

## Gradle Dependency

The Gradle Dependency is available via [jCenter](https://bintray.com/pengfeng/ximsfei/gbu)

Add dependencies directly

```
implementation 'god.bless.you:gbu:0.0.2'
```

## Integration

Integrate with kotlin

```
class App : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Gbu.init(this)
                .setDebug(true)
                .enableLifecycleCrash(true)
                .enableOtherCrash(false)
                .install()
    }
}
```

## How it works

### Fix Activity Crash

GBU 'fix' the crash in the Activity lifecycle by hooking `ActivityThread`'s field `mInstrumentation`.

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

### Fix Service and BroadcastReceiver Crash

GBU 'fix' the crash in the Service and BroadcastReceiver lifecycle by hooking `Instrumentation`'s method `onException`.

> GbuInstrumentationImpl.kt

```kotlin
class GbuInstrumentationImpl(base: Instrumentation) : GbuInstrumentationWrapper(base) {

    /**
     * If true is returned, the system will proceed as if the exception didn't happen.
     */
    override fun onException(obj: Any?, e: Throwable?): Boolean {
        GbuActivityThread.handleException(obj)
        return true
    }
}
```

> GbuActivityThread.kt

```kotlin
object GbuActivityThread {
    /**
     * Prevent incomplete lifecycle leads to anr.
     */
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

### Fix Other Crash

```kotlin
object Gbu {
    init {
        Handler(Looper.getMainLooper()).post {
            // Do not let the main thread exit.
            while (true) {
                try {
                    Looper.loop()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
        // Catch all threads crashes
        Thread.setDefaultUncaughtExceptionHandler { _: Thread, throwable: Throwable ->
            throwable.printStackTrace()
        }
    }
}
```
## About Author

Pengfeng Wang(王鹏锋)

email: ximsfei@gmail.com

## [License Apache 2.0](LICENSE)
