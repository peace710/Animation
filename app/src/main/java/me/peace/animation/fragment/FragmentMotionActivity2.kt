package me.peace.animation.fragment

import android.content.Context
import android.content.Intent
import me.peace.animation.R

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import me.peace.animation.viewpager.ViewPagerActivity
import kotlin.math.abs


class FragmentMotionActivity2 : AppCompatActivity(), View.OnClickListener, MotionLayout.TransitionListener {

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

    private var lastProgress = 0f
    private var fragment: Fragment? = null
    private var last: Float = 0f

    override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
        if (p3 - lastProgress > 0) {
            // from start to end
            val atEnd = abs(p3 - 1f) < 0.1f
            if (atEnd && fragment is MainFragment) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction
                    .setCustomAnimations(R.animator.show, 0)
                fragment = ListFragment.newInstance().also {
                    transaction
                        .replace(R.id.container, it)
                        .commitNow()
                }
            }
        } else {
            // from end to start
            val atStart = p3 < 0.9f
            if (atStart && fragment is ListFragment) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction
                    .setCustomAnimations(0, R.animator.hide)
                fragment = MainFragment.newInstance().also {
                    transaction
                        .replace(R.id.container, it)
                        .commitNow()
                }
            }
        }
        lastProgress = p3
    }

    override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
    }

    override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
    }

    override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = intent.getIntExtra(LAYOUT, R.layout.activity_fragment_main)
        setContentView(layout)
        if (savedInstanceState == null) {

            fragment = MainFragment.newInstance().also {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, it)
                    .commitNow()
            }
        }
        findViewById<MotionLayout>(R.id.motion_layout).setTransitionListener(this)
        findViewById<Button>(R.id.toggle).setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.toggle) {
            val transaction = supportFragmentManager.beginTransaction()
            fragment = if (fragment == null || fragment is MainFragment) {
                last = 1f
                transaction
                    .setCustomAnimations(R.animator.show, 0)
                SecondFragment.newInstance()
            } else {
                transaction
                    .setCustomAnimations(0, R.animator.hide)
                MainFragment.newInstance()
            }.also {
                transaction
                    .replace(R.id.container, it)
                    .commitNow()
            }
        }
    }
}
