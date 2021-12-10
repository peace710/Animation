package me.peace.animation.drawable.animation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
import me.peace.animation.R
import me.peace.animation.drawable.animation.fragment.HomeFragment


class DrawableAnimationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawable_animation_main)
        if (savedInstanceState == null) {
            supportFragmentManager.commitNow {
                replace(R.id.drawable_animation_main, HomeFragment())
            }
        }
    }
}