package gbu.app

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import gbu.app.activities.*
import gbu.app.receivers.CrashWithOnReceiveReceiver
import gbu.app.services.*

class MainActivity : AppCompatActivity() {
    companion object {
        private val TYPE_NONE = 0
        private val TYPE_ACTIVITY = 1
        private val TYPE_SERVICE = 2
        private val TYPE_RECEIVER = 3
        private val mItems: ArrayList<Item> = ArrayList()

        init {
            mItems.add(Item(R.string.crash_with_view_onClick, TYPE_NONE, null))

            mItems.add(Item(R.string.crash_with_activity_onCreate, TYPE_ACTIVITY, CrashWithOnCreateActivity::class.java))
            mItems.add(Item(R.string.crash_with_activity_onStart, TYPE_ACTIVITY, CrashWithOnStartActivity::class.java))
            mItems.add(Item(R.string.crash_with_activity_onResume, TYPE_ACTIVITY, CrashWithOnResumeActivity::class.java))
            mItems.add(Item(R.string.crash_with_activity_onPause, TYPE_ACTIVITY, CrashWithOnPauseActivity::class.java))
            mItems.add(Item(R.string.crash_with_activity_onStop, TYPE_ACTIVITY, CrashWithOnStopActivity::class.java))
            mItems.add(Item(R.string.crash_with_activity_onDestroy, TYPE_ACTIVITY, CrashWithOnDestroyActivity::class.java))

            mItems.add(Item(R.string.crash_with_service_onCreate, TYPE_SERVICE, CrashWithOnCreateService::class.java))
            mItems.add(Item(R.string.crash_with_service_onStartCommand, TYPE_SERVICE, CrashWithOnStartCommandService::class.java))
            mItems.add(Item(R.string.crash_with_service_onStart, TYPE_SERVICE, CrashWithOnStartService::class.java))
            mItems.add(Item(R.string.crash_with_service_onBind, TYPE_SERVICE, CrashWithOnBindService::class.java))
            mItems.add(Item(R.string.crash_with_service_onUnbind, TYPE_SERVICE, CrashWithOnUnbindService::class.java))
            mItems.add(Item(R.string.crash_with_service_onRebind, TYPE_SERVICE, CrashWithOnRebindService::class.java))
            mItems.add(Item(R.string.crash_with_service_onDestroy, TYPE_SERVICE, CrashWithOnDestroyService::class.java))

            mItems.add(Item(R.string.crash_with_receiver_onReceive, TYPE_RECEIVER, CrashWithOnReceiveReceiver::class.java))
        }

        fun start(context: Context, item: Item) {
            when (item.type) {
                TYPE_NONE -> throw RuntimeException(context.getString(R.string.crash_with_view_onClick))
                TYPE_ACTIVITY -> context.startActivity(Intent(context, item.clazz))
                TYPE_RECEIVER -> context.sendBroadcast(Intent(context, item.clazz))
                TYPE_SERVICE -> {
                    when (item.clazz) {
                        CrashWithOnCreateService::class.java -> {
                            context.startService(Intent(context, item.clazz))
                            context.stopService(Intent(context, item.clazz))
                        }
                        CrashWithOnStartCommandService::class.java -> {
                            context.startService(Intent(context, item.clazz))
                            context.stopService(Intent(context, item.clazz))
                        }
                        CrashWithOnStartService::class.java -> {
                            context.startService(Intent(context, item.clazz))
                            context.stopService(Intent(context, item.clazz))
                        }
                        CrashWithOnBindService::class.java -> {
                            context.startService(Intent(context, item.clazz))
                            val sc: ServiceConnection = SC()
                            context.bindService(Intent(context, item.clazz), sc, Context.BIND_AUTO_CREATE)
                            context.unbindService(sc)
                            context.stopService(Intent(context, item.clazz))
                        }
                        CrashWithOnUnbindService::class.java -> {
                            context.startService(Intent(context, item.clazz))
                            val sc: ServiceConnection = SC()
                            context.bindService(Intent(context, item.clazz), sc, Context.BIND_AUTO_CREATE)
                            context.unbindService(sc)
                            context.stopService(Intent(context, item.clazz))
                        }
                        CrashWithOnRebindService::class.java -> {
                            context.startService(Intent(context, item.clazz))
                            val sc = SC()
                            context.bindService(Intent(context, item.clazz), sc, Context.BIND_AUTO_CREATE)
                            context.unbindService(sc)
                            val nsc: ServiceConnection = SC()
                            context.bindService(Intent(context, item.clazz), nsc, Context.BIND_ABOVE_CLIENT)
                            context.unbindService(nsc)
//                            context.stopService(Intent(context, item.clazz))
                        }
                        CrashWithOnDestroyService::class.java -> {
                            context.startService(Intent(context, item.clazz))
                            context.stopService(Intent(context, item.clazz))
                        }
                    }
                }
            }
        }

        class SC : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            }
        }
    }

    private var mRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerView = findViewById(R.id.recycler_view)
        mRecyclerView?.layoutManager = LinearLayoutManager(this)
        mRecyclerView?.adapter = Adapter(this@MainActivity)
    }

    private class Adapter(val context: Context) : RecyclerView.Adapter<ViewHolder>() {
        override fun getItemCount(): Int {
            return mItems.size
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_main, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            holder?.mBtn?.text = context.getString(mItems[position].name)
            holder?.mBtn?.setOnClickListener {
                start(context, mItems[position])
            }
        }
    }

    private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mBtn: Button = itemView.findViewById(R.id.btn)
    }

    data class Item(val name: Int, val type: Int, val clazz: Class<*>?)
}
