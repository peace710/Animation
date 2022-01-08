package me.peace.animation.youtube

import android.content.Context
import android.content.Intent
import me.peace.animation.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class YouTubeActivity : AppCompatActivity() {
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
        val layout = intent.getIntExtra(LAYOUT, R.layout.motion_24_youtube)
        setContentView(layout)
        val motionLayout = findViewById<MotionLayout>(R.id.motion_layout).apply {
            savedInstanceState
        }
        findViewById<RecyclerView>(R.id.recyclerview_front).apply {
            adapter = FrontPhotosAdapter()
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(this@YouTubeActivity)
        }
        val debugMode = if (intent.getBooleanExtra("showPaths", false)) {
            MotionLayout.DEBUG_SHOW_PATH
        } else {
            MotionLayout.DEBUG_SHOW_NONE
        }
        motionLayout.setDebugMode(debugMode)
    }
}