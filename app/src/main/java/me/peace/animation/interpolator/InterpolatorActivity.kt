package me.peace.animation.interpolator

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ViewAnimator
import androidx.fragment.app.FragmentTransaction
import me.peace.animation.R
import me.peace.animation.basic.transition.BasicTransition
import me.peace.animation.logger.Log
import me.peace.animation.logger.LogFragment
import me.peace.animation.logger.LogWrapper
import me.peace.animation.logger.MessageOnlyLogFilter

class InterpolatorActivity : BasicTransition() {
    private var logShown = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interpolator_main)
        if (savedInstanceState == null) {
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            val fragment = InterpolatorFragment()
            transaction.replace(R.id.interpolator_content, fragment)
            transaction.commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val logToggle = menu.findItem(R.id.menu_toggle_log)
        logToggle.isVisible = findViewById<ViewAnimator>(R.id.basic_transition_output) is ViewAnimator
        logToggle.setTitle(if (logShown) R.string.sample_hide_log else R.string.sample_show_log)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_toggle_log -> {
                logShown = !logShown
                val output = findViewById<ViewAnimator>(R.id.basic_transition_output)
                if (logShown) {
                    output.displayedChild = 1
                } else {
                    output.displayedChild = 0
                }
                supportInvalidateOptionsMenu()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun initializeLogging() {
        val logWrapper = LogWrapper()
        Log.setLogNode(logWrapper)
        val msgFilter = MessageOnlyLogFilter()
        logWrapper.next = msgFilter

        val logFragment: LogFragment? = supportFragmentManager
            .findFragmentById(R.id.basic_transition_log) as LogFragment?
        msgFilter.next = logFragment?.logView
        Log.i(TAG, "Ready")
    }

    companion object {
        const val TAG = "MainActivity"
    }
}