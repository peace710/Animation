package me.peace.animation.grid.pager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.peace.animation.R
import me.peace.animation.grid.pager.fragment.GridFragment

class Grid2PagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid_2_pager_main)
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(KEY_CURRENT_POSITION, 0)
            return
        }
        val fragmentManager = supportFragmentManager
        fragmentManager
            .beginTransaction()
            .add(R.id.grid_2_pager_fragment_container, GridFragment(), GridFragment::class.java.simpleName)
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_CURRENT_POSITION, currentPosition)
    }

    companion object {
        var currentPosition = 0
        private const val KEY_CURRENT_POSITION =
            "com.google.samples.gridtopager.key.currentPosition"
    }
}