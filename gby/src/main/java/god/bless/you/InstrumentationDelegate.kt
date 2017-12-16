package god.bless.you

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

open class InstrumentationDelegate : Instrumentation() {
    protected var mBase: Instrumentation? = null

    override fun getComponentName(): ComponentName {
        return mBase!!.componentName
    }

    override fun startActivitySync(intent: Intent?): Activity {
        return mBase!!.startActivitySync(intent)
    }

    override fun setAutomaticPerformanceSnapshots() {
        mBase!!.setAutomaticPerformanceSnapshots()
    }

    override fun waitForIdle(recipient: Runnable?) {
        mBase!!.waitForIdle(recipient)
    }

    override fun stopProfiling() {
        mBase!!.stopProfiling()
    }

    override fun callActivityOnPostCreate(activity: Activity?, icicle: Bundle?) {
        mBase!!.callActivityOnPostCreate(activity, icicle)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun callActivityOnPostCreate(activity: Activity?, icicle: Bundle?, persistentState: PersistableBundle?) {
        mBase!!.callActivityOnPostCreate(activity, icicle, persistentState)
    }

    override fun waitForMonitorWithTimeout(monitor: ActivityMonitor?, timeOut: Long): Activity {
        return mBase!!.waitForMonitorWithTimeout(monitor, timeOut)
    }

    override fun sendStatus(resultCode: Int, results: Bundle?) {
        mBase!!.sendStatus(resultCode, results)
    }

    override fun startProfiling() {
        mBase!!.startProfiling()
    }

    override fun start() {
        mBase!!.start()
    }

    override fun runOnMainSync(runner: Runnable?) {
        mBase!!.runOnMainSync(runner)
    }

    override fun getBinderCounts(): Bundle {
        return mBase!!.binderCounts
    }

    override fun onException(obj: Any?, e: Throwable?): Boolean {
        return mBase!!.onException(obj, e)
    }

    override fun sendCharacterSync(keyCode: Int) {
        mBase!!.sendCharacterSync(keyCode)
    }

    override fun checkMonitorHit(monitor: ActivityMonitor?, minHits: Int): Boolean {
        return mBase!!.checkMonitorHit(monitor, minHits)
    }

    override fun sendKeyDownUpSync(key: Int) {
        mBase!!.sendKeyDownUpSync(key)
    }

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return mBase!!.newApplication(cl, className, context)
    }

    override fun sendTrackballEventSync(event: MotionEvent?) {
        mBase!!.sendTrackballEventSync(event)
    }

    override fun callActivityOnRestoreInstanceState(activity: Activity?, savedInstanceState: Bundle?) {
        mBase!!.callActivityOnRestoreInstanceState(activity, savedInstanceState)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun callActivityOnRestoreInstanceState(activity: Activity?, savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        mBase!!.callActivityOnRestoreInstanceState(activity, savedInstanceState, persistentState)
    }

    override fun callActivityOnStart(activity: Activity?) {
        mBase!!.callActivityOnStart(activity)
    }

    override fun sendKeySync(event: KeyEvent?) {
        mBase!!.sendKeySync(event)
    }

    override fun getContext(): Context {
        return mBase!!.context
    }

    override fun startAllocCounting() {
        mBase!!.startAllocCounting()
    }

    override fun endPerformanceSnapshot() {
        mBase!!.endPerformanceSnapshot()
    }

    override fun callActivityOnRestart(activity: Activity?) {
        mBase!!.callActivityOnRestart(activity)
    }

    override fun setInTouchMode(inTouch: Boolean) {
        mBase!!.setInTouchMode(inTouch)
    }

    override fun getTargetContext(): Context {
        return mBase!!.targetContext
    }

    override fun invokeContextMenuAction(targetActivity: Activity?, id: Int, flag: Int): Boolean {
        return mBase!!.invokeContextMenuAction(targetActivity, id, flag)
    }

    override fun callApplicationOnCreate(app: Application?) {
        mBase!!.callApplicationOnCreate(app)
    }

    override fun stopAllocCounting() {
        mBase!!.stopAllocCounting()
    }

    override fun callActivityOnNewIntent(activity: Activity?, intent: Intent?) {
        mBase!!.callActivityOnNewIntent(activity, intent)
    }

    override fun newActivity(clazz: Class<*>?, context: Context?, token: IBinder?, application: Application?, intent: Intent?, info: ActivityInfo?, title: CharSequence?, parent: Activity?, id: String?, lastNonConfigurationInstance: Any?): Activity {
        return mBase!!.newActivity(clazz, context, token, application, intent, info, title, parent, id, lastNonConfigurationInstance)
    }

    override fun newActivity(cl: ClassLoader?, className: String?, intent: Intent?): Activity {
        return mBase!!.newActivity(cl, className, intent)
    }

    override fun sendPointerSync(event: MotionEvent?) {
        mBase!!.sendPointerSync(event)
    }

    override fun callActivityOnStop(activity: Activity?) {
        mBase!!.callActivityOnStop(activity)
    }

    override fun callActivityOnCreate(activity: Activity?, icicle: Bundle?) {
        mBase!!.callActivityOnCreate(activity, icicle)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun callActivityOnCreate(activity: Activity?, icicle: Bundle?, persistentState: PersistableBundle?) {
        mBase!!.callActivityOnCreate(activity, icicle, persistentState)
    }

    override fun callActivityOnSaveInstanceState(activity: Activity?, outState: Bundle?) {
        mBase!!.callActivityOnSaveInstanceState(activity, outState)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun callActivityOnSaveInstanceState(activity: Activity?, outState: Bundle?, outPersistentState: PersistableBundle?) {
        mBase!!.callActivityOnSaveInstanceState(activity, outState, outPersistentState)
    }

    override fun isProfiling(): Boolean {
        return mBase!!.isProfiling
    }

    override fun onCreate(arguments: Bundle?) {
        mBase!!.onCreate(arguments)
    }

    @TargetApi(Build.VERSION_CODES.O)
    override fun getProcessName(): String {
        return mBase!!.processName
    }

    override fun removeMonitor(monitor: ActivityMonitor?) {
        mBase!!.removeMonitor(monitor)
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun getUiAutomation(): UiAutomation {
        return mBase!!.uiAutomation
    }

    @TargetApi(Build.VERSION_CODES.N)
    override fun getUiAutomation(flags: Int): UiAutomation {
        return mBase!!.getUiAutomation(flags)
    }

    @TargetApi(Build.VERSION_CODES.O)
    override fun acquireLooperManager(looper: Looper?): TestLooperManager {
        return mBase!!.acquireLooperManager(looper)
    }

    override fun onStart() {
        mBase!!.onStart()
    }

    @TargetApi(Build.VERSION_CODES.O)
    override fun addResults(results: Bundle?) {
        mBase!!.addResults(results)
    }

    override fun finish(resultCode: Int, results: Bundle?) {
        mBase!!.finish(resultCode, results)
    }

    override fun callActivityOnUserLeaving(activity: Activity?) {
        mBase!!.callActivityOnUserLeaving(activity)
    }

    override fun waitForIdleSync() {
        mBase!!.waitForIdleSync()
    }

    override fun callActivityOnDestroy(activity: Activity?) {
        mBase!!.callActivityOnDestroy(activity)
    }

    override fun addMonitor(monitor: ActivityMonitor?) {
        mBase!!.addMonitor(monitor)
    }

    override fun addMonitor(filter: IntentFilter?, result: ActivityResult?, block: Boolean): ActivityMonitor {
        return mBase!!.addMonitor(filter, result, block)
    }

    override fun addMonitor(cls: String?, result: ActivityResult?, block: Boolean): ActivityMonitor {
        return mBase!!.addMonitor(cls, result, block)
    }

    override fun sendStringSync(text: String?) {
        mBase!!.sendStringSync(text)
    }

    override fun callActivityOnPause(activity: Activity?) {
        mBase!!.callActivityOnPause(activity)
    }

    override fun startPerformanceSnapshot() {
        mBase!!.startPerformanceSnapshot()
    }

    override fun getAllocCounts(): Bundle {
        return mBase!!.allocCounts
    }

    override fun callActivityOnResume(activity: Activity?) {
        mBase!!.callActivityOnResume(activity)
    }

    override fun waitForMonitor(monitor: ActivityMonitor?): Activity {
        return mBase!!.waitForMonitor(monitor)
    }

    override fun invokeMenuActionSync(targetActivity: Activity?, id: Int, flag: Int): Boolean {
        return mBase!!.invokeMenuActionSync(targetActivity, id, flag)
    }

    override fun onDestroy() {
        mBase!!.onDestroy()
    }
}
