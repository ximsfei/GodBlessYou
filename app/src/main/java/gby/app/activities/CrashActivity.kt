package gby.app.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import gby.app.R

abstract class CrashActivity : AppCompatActivity() {
    abstract fun getTitleId(): Int

    private var mContent: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crash)
        setTitle(getTitleId())
        mContent = findViewById(R.id.text)
        mContent?.setText(getTitleId())
    }
}