package me.peace.animation.basic.transition

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import me.peace.animation.logger.Log
import me.peace.animation.logger.LogWrapper


open class BasicTransition : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        initializeLogging()
    }


    open fun initializeLogging() {
        val logWrapper = LogWrapper()
        Log.setLogNode(logWrapper)
        Log.i(TAG, "Ready")
    }

    companion object {
        const val TAG = "SampleActivityBase"
    }
}