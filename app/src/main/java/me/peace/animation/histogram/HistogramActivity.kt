package me.peace.animation.histogram


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.Button
import android.widget.Switch
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import me.peace.animation.R
import me.peace.animation.motion.MotionActivity
import java.lang.RuntimeException
import java.util.*
import kotlin.collections.ArrayList

class HistogramActivity : AppCompatActivity() {

    companion object{
        private const val DEFAULT_COLOUR_ID = R.color.colorAccent
        private const val HISTOGRAM_BARS_RESTORE_KEY = "HISTOGRAM"
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

    private var bars: ArrayList<HistogramBarMetaData> = ArrayList()

    private var widget: HistogramWidget? = null

    private val animationGuard: HistogramAnimationGuard = HistogramAnimationGuard()

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = intent.getIntExtra(LAYOUT, R.layout.histogram_layout)
        setContentView(layout)
        widget = findViewById(R.id.histogram)
        restoreView(savedInstanceState)
        widget!!.setTransitionListener(animationGuard.animationListener)
    }

    fun onClickAdd(view: View?) {
        if (animationGuard.wait) {
            return
        }
        add()
        widget!!.animateWidget()
    }

    fun onClickSort(view: View?) {
        if (animationGuard.wait) {
            return
        }
        bars = widget!!.sort()
        widget!!.animateWidget()
    }

    fun onClickRandom(view: View) {
        if (animationGuard.wait) {
            return
        }
        add()
        bars = widget!!.sort()
        widget!!.animateWidget()
    }

    private fun add() {
        val rand = Random()
        var barColour = ContextCompat.getColor(this, DEFAULT_COLOUR_ID)
        val barDataList = ArrayList<HistogramBarMetaData>(widget!!.barsSize)
        var name = 0
        for (id in widget!!.barIds) {
            val barData = HistogramBarMetaData(
                    id,
                    rand.nextFloat(),
                    barColour,
                    ColorHelper.getContrastColor(barColour),
                    name.toString())
            barColour = ColorHelper.getNextColor(barColour)
            barDataList.add(barData)
            name++
        }
        bars = barDataList
        widget!!.setData(barDataList)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (bars.isNotEmpty()) {
            outState.putParcelableArrayList(HISTOGRAM_BARS_RESTORE_KEY, bars)
        }
        super.onSaveInstanceState(outState)
    }

    private fun restoreView(savedInstance: Bundle?) {
        if (savedInstance == null) { // nothing to restore.
            return
        }
        bars = savedInstance.getParcelableArrayList(HISTOGRAM_BARS_RESTORE_KEY) ?: ArrayList()
        if (bars.isEmpty()) {
            return
        }

        widget!!.viewTreeObserver.addOnGlobalLayoutListener(object :
                OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (widget!!.barIds.size != bars.size) {
                    throw RuntimeException("Restoring array doesn't match the view size.")
                }

                bars = ArrayList(bars.mapIndexed{ i, metaData ->
                    HistogramBarMetaData(widget!!.barIds[i], metaData)
                })
                widget!!.setData(bars)
                widget!!.animateWidget()
                widget!!.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    fun onClickSwitch(view: View) {
        val animationInterruptible = (view as Switch).isChecked
        animationGuard.interruptible = animationInterruptible

        findViewById<Button>(R.id.sort).setEnabledAndChangeColor(!animationInterruptible)
        findViewById<Button>(R.id.both).setEnabledAndChangeColor(!animationInterruptible)
    }
}

fun View.setEnabledAndChangeColor(enabled: Boolean) {
    if (!enabled) {
        background.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN)
        isClickable = false
    } else {
        background.colorFilter = null
        isClickable = true
    }
    invalidate()
}
