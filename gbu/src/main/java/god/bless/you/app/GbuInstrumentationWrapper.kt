package god.bless.you.app

import android.annotation.TargetApi
import android.app.Activity
import android.app.Application
import android.app.Instrumentation
import android.app.UiAutomation
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.os.*
import android.view.KeyEvent
import android.view.MotionEvent

open class GbuInstrumentationWrapper(private val base: Instrumentation) : Instrumentation() {
    override fun getComponentName(): ComponentName {
        return base.componentName
    }

    override fun startActivitySync(intent: Intent?): Activity {
        return base.startActivitySync(intent)
    }

    override fun setAutomaticPerformanceSnapshots() {
        base.setAutomaticPerformanceSnapshots()
    }

    override fun waitForIdle(recipient: Runnable?) {
        base.waitForIdle(recipient)
    }

    override fun stopProfiling() {
        base.stopProfiling()
    }

    override fun callActivityOnPostCreate(activity: Activity?, icicle: Bundle?) {
        base.callActivityOnPostCreate(activity, icicle)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun callActivityOnPostCreate(activity: Activity?, icicle: Bundle?, persistentState: PersistableBundle?) {
        base.callActivityOnPostCreate(activity, icicle, persistentState)
    }

    override fun waitForMonitorWithTimeout(monitor: ActivityMonitor?, timeOut: Long): Activity {
        return base.waitForMonitorWithTimeout(monitor, timeOut)
    }

    override fun sendStatus(resultCode: Int, results: Bundle?) {
        base.sendStatus(resultCode, results)
    }

    override fun startProfiling() {
        base.startProfiling()
    }

    override fun start() {
        base.start()
    }

    override fun runOnMainSync(runner: Runnable?) {
        base.runOnMainSync(runner)
    }

    override fun getBinderCounts(): Bundle {
        return base.binderCounts
    }

    override fun onException(obj: Any?, e: Throwable?): Boolean {
        return base.onException(obj, e)
    }

    override fun sendCharacterSync(keyCode: Int) {
        base.sendCharacterSync(keyCode)
    }

    override fun checkMonitorHit(monitor: ActivityMonitor?, minHits: Int): Boolean {
        return base.checkMonitorHit(monitor, minHits)
    }

    override fun sendKeyDownUpSync(key: Int) {
        base.sendKeyDownUpSync(key)
    }

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return base.newApplication(cl, className, context)
    }

    override fun sendTrackballEventSync(event: MotionEvent?) {
        base.sendTrackballEventSync(event)
    }

    override fun callActivityOnRestoreInstanceState(activity: Activity?, savedInstanceState: Bundle?) {
        base.callActivityOnRestoreInstanceState(activity, savedInstanceState)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun callActivityOnRestoreInstanceState(activity: Activity?, savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        base.callActivityOnRestoreInstanceState(activity, savedInstanceState, persistentState)
    }

    override fun callActivityOnStart(activity: Activity?) {
        base.callActivityOnStart(activity)
    }

    override fun sendKeySync(event: KeyEvent?) {
        base.sendKeySync(event)
    }

    override fun getContext(): Context {
        return base.context
    }

    override fun startAllocCounting() {
        base.startAllocCounting()
    }

    override fun endPerformanceSnapshot() {
        base.endPerformanceSnapshot()
    }

    override fun callActivityOnRestart(activity: Activity?) {
        base.callActivityOnRestart(activity)
    }

    override fun setInTouchMode(inTouch: Boolean) {
        base.setInTouchMode(inTouch)
    }

    override fun getTargetContext(): Context {
        return base.targetContext
    }

    override fun invokeContextMenuAction(targetActivity: Activity?, id: Int, flag: Int): Boolean {
        return base.invokeContextMenuAction(targetActivity, id, flag)
    }

    override fun callApplicationOnCreate(app: Application?) {
        base.callApplicationOnCreate(app)
    }

    override fun stopAllocCounting() {
        base.stopAllocCounting()
    }

    override fun callActivityOnNewIntent(activity: Activity?, intent: Intent?) {
        base.callActivityOnNewIntent(activity, intent)
    }

    override fun newActivity(clazz: Class<*>?, context: Context?, token: IBinder?, application: Application?, intent: Intent?, info: ActivityInfo?, title: CharSequence?, parent: Activity?, id: String?, lastNonConfigurationInstance: Any?): Activity {
        return base.newActivity(clazz, context, token, application, intent, info, title, parent, id, lastNonConfigurationInstance)
    }

    override fun newActivity(cl: ClassLoader?, className: String?, intent: Intent?): Activity {
        return base.newActivity(cl, className, intent)
    }

    override fun sendPointerSync(event: MotionEvent?) {
        base.sendPointerSync(event)
    }

    override fun callActivityOnStop(activity: Activity?) {
        base.callActivityOnStop(activity)
    }

    override fun callActivityOnCreate(activity: Activity?, icicle: Bundle?) {
        base.callActivityOnCreate(activity, icicle)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun callActivityOnCreate(activity: Activity?, icicle: Bundle?, persistentState: PersistableBundle?) {
        base.callActivityOnCreate(activity, icicle, persistentState)
    }

    override fun callActivityOnSaveInstanceState(activity: Activity?, outState: Bundle?) {
        base.callActivityOnSaveInstanceState(activity, outState)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun callActivityOnSaveInstanceState(activity: Activity?, outState: Bundle?, outPersistentState: PersistableBundle?) {
        base.callActivityOnSaveInstanceState(activity, outState, outPersistentState)
    }

    override fun isProfiling(): Boolean {
        return base.isProfiling
    }

    override fun onCreate(arguments: Bundle?) {
        base.onCreate(arguments)
    }

    @TargetApi(Build.VERSION_CODES.O)
    override fun getProcessName(): String {
        return base.processName
    }

    override fun removeMonitor(monitor: ActivityMonitor?) {
        base.removeMonitor(monitor)
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun getUiAutomation(): UiAutomation {
        return base.uiAutomation
    }

    @TargetApi(Build.VERSION_CODES.N)
    override fun getUiAutomation(flags: Int): UiAutomation {
        return base.getUiAutomation(flags)
    }

    @TargetApi(Build.VERSION_CODES.O)
    override fun acquireLooperManager(looper: Looper?): TestLooperManager {
        return base.acquireLooperManager(looper)
    }

    override fun onStart() {
        base.onStart()
    }

    @TargetApi(Build.VERSION_CODES.O)
    override fun addResults(results: Bundle?) {
        base.addResults(results)
    }

    override fun finish(resultCode: Int, results: Bundle?) {
        base.finish(resultCode, results)
    }

    override fun callActivityOnUserLeaving(activity: Activity?) {
        base.callActivityOnUserLeaving(activity)
    }

    override fun waitForIdleSync() {
        base.waitForIdleSync()
    }

    override fun callActivityOnDestroy(activity: Activity?) {
        base.callActivityOnDestroy(activity)
    }

    override fun addMonitor(monitor: ActivityMonitor?) {
        base.addMonitor(monitor)
    }

    override fun addMonitor(filter: IntentFilter?, result: ActivityResult?, block: Boolean): ActivityMonitor {
        return base.addMonitor(filter, result, block)
    }

    override fun addMonitor(cls: String?, result: ActivityResult?, block: Boolean): ActivityMonitor {
        return base.addMonitor(cls, result, block)
    }

    override fun sendStringSync(text: String?) {
        base.sendStringSync(text)
    }

    override fun callActivityOnPause(activity: Activity?) {
        base.callActivityOnPause(activity)
    }

    override fun startPerformanceSnapshot() {
        base.startPerformanceSnapshot()
    }

    override fun getAllocCounts(): Bundle {
        return base.allocCounts
    }

    override fun callActivityOnResume(activity: Activity?) {
        base.callActivityOnResume(activity)
    }

    override fun waitForMonitor(monitor: ActivityMonitor?): Activity {
        return base.waitForMonitor(monitor)
    }

    override fun invokeMenuActionSync(targetActivity: Activity?, id: Int, flag: Int): Boolean {
        return base.invokeMenuActionSync(targetActivity, id, flag)
    }

    override fun onDestroy() {
        base.onDestroy()
    }
}
