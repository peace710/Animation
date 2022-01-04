package me.peace.animation.viewpager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import me.peace.animation.R
import me.peace.animation.widget.ViewPagerHeader

class ViewPagerActivity : AppCompatActivity() {

    companion object{
        private const val LAYOUT:String = "layout"
        private const val PATH:String = "path"

        fun start(context: Context, activity: Class<*>, layoutField:Int, showPath:Boolean = true){
            val intent = Intent(context, activity).apply {
                putExtra(LAYOUT, layoutField)
                putExtra(PATH, showPath)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = intent.getIntExtra(LAYOUT, R.layout.motion_16_viewpager)
        setContentView(layout)


        val pager = findViewById<ViewPager>(R.id.pager)
        val tabs = findViewById<TabLayout>(R.id.tabs)
        val viewPagerHeader = findViewById<ViewPagerHeader>(R.id.motion_layout)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addPage("Page 1", R.layout.motion_16_viewpager_page1)
        adapter.addPage("Page 2", R.layout.motion_16_viewpager_page2)
        adapter.addPage("Page 3", R.layout.motion_16_viewpager_page3)
        pager.adapter = adapter
        tabs.setupWithViewPager(pager)
        if (viewPagerHeader != null) {
            pager.addOnPageChangeListener(viewPagerHeader)
        }

        val debugMode = if (intent.getBooleanExtra("showPaths", false)) {
            MotionLayout.DEBUG_SHOW_PATH
        } else {
            MotionLayout.DEBUG_SHOW_NONE
        }
        viewPagerHeader.setDebugMode(debugMode)
    }
}