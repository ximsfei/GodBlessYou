package gby.app

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import gby.app.activities.*

class MainActivity : AppCompatActivity() {
    companion object {
        private val TYPE_ACTIVITY = 1
        private val TYPE_SERVICE = 2
        private val mItems: ArrayList<Item> = ArrayList()

        init {
            mItems.add(Item(R.string.crash_with_activity_onCreate, TYPE_ACTIVITY, CrashWithOnCreateActivity::class.java))
            mItems.add(Item(R.string.crash_with_activity_onStart, TYPE_ACTIVITY, CrashWithOnStartActivity::class.java))
            mItems.add(Item(R.string.crash_with_activity_onResume, TYPE_ACTIVITY, CrashWithOnResumeActivity::class.java))
            mItems.add(Item(R.string.crash_with_activity_onPause, TYPE_ACTIVITY, CrashWithOnPauseActivity::class.java))
            mItems.add(Item(R.string.crash_with_activity_onStop, TYPE_ACTIVITY, CrashWithOnStopActivity::class.java))
            mItems.add(Item(R.string.crash_with_activity_onDestroy, TYPE_ACTIVITY, CrashWithOnDestroyActivity::class.java))
        }

        fun start(context: Context, item: Item) {
            when (item.type) {
                TYPE_ACTIVITY -> context.startActivity(Intent(context, item.clazz))
                TYPE_SERVICE -> context.startService(Intent(context, item.clazz))
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

    data class Item(val name: Int, val type: Int, val clazz: Class<*>)
}
