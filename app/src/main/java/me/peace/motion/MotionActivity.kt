package me.peace.motion

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.peace.animation.R
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout


class MotionActivity:AppCompatActivity() {

    companion object{
        private const val LAYOUT:String = "layout"
        private const val PATH:String = "path"

        fun start(context: Context, activity: Class<*>, layoutField:Int,showPath:Boolean = true){
            val intent = Intent(context, activity).apply {
                putExtra(LAYOUT, layoutField)
                putExtra(PATH, showPath)
            }
            context.startActivity(intent)
        }
    }

    private lateinit var container:View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = intent.getIntExtra(LAYOUT, R.layout.motion_01_basic)
        setContentView(layout)
        container = findViewById(R.id.motion_layout)

        val debug = if (intent.getBooleanExtra(PATH,false)){
            MotionLayout.DEBUG_SHOW_PATH
        } else{
            MotionLayout.DEBUG_SHOW_NONE
        }
        (container as? MotionLayout)?.setDebugMode(debug)
    }

    fun changeState(v: View?) {
        val motionLayout = container as? MotionLayout?: return
        if (motionLayout.progress > 0.5f) {
            motionLayout.transitionToStart()
        } else {
            motionLayout.transitionToEnd()
        }
    }
}