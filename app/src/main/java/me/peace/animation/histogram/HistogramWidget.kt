package me.peace.animation.histogram

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.constraintlayout.motion.widget.TransitionBuilder
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import me.peace.animation.R
import java.util.*

class HistogramWidget : MotionLayout {
    companion object {
        private const val TAG = "HistogramWidget"
        private const val DEFAULT_HEIGHT_DP = 1
        private const val DEFAULT_HEIGHT_PERCENT = 0.01f
    }

    private val bars: MutableMap<Int, TextView> = HashMap()

    private var currentBars: MutableList<HistogramBarMetaData> = ArrayList()
    private var nextBars: MutableList<HistogramBarMetaData> = ArrayList()

    private var leftMarginDp = 0

    private val weights: FloatArray

    private var barTransition: MotionScene.Transition? = null

    var barsSize = 0
        private set(value) { field = value }

    val barIds: List<Int> get() = currentBars.map { it.id }

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int):
            super(context, attrs, defStyleAttr) {
        val array = context.theme.obtainStyledAttributes(
                attrs, R.styleable.HistogramWidget, 0, 0)
        try {
            barsSize = array.getInt(R.styleable.HistogramWidget_columns, 0)
            leftMarginDp = array.getInt(R.styleable.HistogramWidget_leftMarginDp, 0)
        } finally {
            array.recycle()
        }
        weights = FloatArray(barsSize)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        createBars(this, barsSize)
        val scene = MotionScene(this)
        barTransition = createTransition(scene)


        scene.addTransition(barTransition)
        scene.setTransition(barTransition)
        setScene(scene)
    }

    private fun createTransition(scene: MotionScene): MotionScene.Transition {
        val startSetId = View.generateViewId()
        val startSet = ConstraintSet()
        startSet.clone(this)
        val endSetId = View.generateViewId()
        val endSet = ConstraintSet()
        endSet.clone(this)
        val transitionId = View.generateViewId()
        return TransitionBuilder.buildTransition(
                scene,
                transitionId,
                startSetId, startSet,
                endSetId, endSet)
    }

    fun setData(newData: List<HistogramBarMetaData>) {
        val startSet: ConstraintSet = getConstraintSet(barTransition!!.startConstraintSetId)
        updateConstraintSet(startSet, currentBars, false)
        val endSet: ConstraintSet = getConstraintSet(barTransition!!.endConstraintSetId)
        updateConstraintSet(endSet, newData)
        nextBars = ArrayList(newData)
    }


    private fun updateConstraintSet(
            set: ConstraintSet,
            list: List<HistogramBarMetaData>,
            useHeightFromMetaData: Boolean = true) {
        list.forEach { metadata ->
            val view = bars[metadata.id]!!
            val height: Int =
                    if (useHeightFromMetaData) (metadata.height * height).toInt()
                    else bars[metadata.id]!!.height
            view.setTextColor(metadata.barTextColour)
            view.text = metadata.name


            set.constrainHeight(view.id, height)
            set.setColorValue(view.id, "BackgroundColor", metadata.barColour)
        }
    }


    fun animateWidget() {
        val startSet: ConstraintSet = getConstraintSet(barTransition!!.startConstraintSetId)
        val endSet: ConstraintSet = getConstraintSet(barTransition!!.endConstraintSetId)

        setTransition(barTransition!!.startConstraintSetId, barTransition!!.endConstraintSetId)
        transitionToEnd()

        // Update the end state to be the current.
        startSet.clone(endSet)
        currentBars = ArrayList(nextBars)
    }


    fun sort(): ArrayList<HistogramBarMetaData> {
        nextBars.sortBy { it.height }
        val startSet: ConstraintSet = getConstraintSet(barTransition!!.startConstraintSetId)
        startSet.createHorizontalChain(
                ConstraintSet.PARENT_ID, ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID, ConstraintSet.RIGHT,
                currentBars.map{ it.id }.toIntArray(), weights, LayoutParams.CHAIN_SPREAD)
        val endSet: ConstraintSet = getConstraintSet(barTransition!!.endConstraintSetId)
        endSet.createHorizontalChain(
                ConstraintSet.PARENT_ID, ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID, ConstraintSet.RIGHT,
                nextBars.map{ it.id }.toIntArray(), weights, LayoutParams.CHAIN_SPREAD)

        return ArrayList(nextBars)
    }


    private fun createBars(layout: MotionLayout, columns: Int) {
        if (columns <= 1) {
            return
        }

        val marginInDp = fromDp(context, leftMarginDp)
        val size = fromDp(context, DEFAULT_HEIGHT_DP)

        val set = ConstraintSet()
        set.clone(layout)
        for (i in 0 until columns) {
            val bar = createBar(layout.context)
            val barColour = ContextCompat.getColor(context, R.color.colorPrimary)

            bar.text = i.toString()
            bar.background = ColorDrawable(barColour)
            val layoutParams = LayoutParams(size, size)
            layout.addView(bar, layoutParams)
            set.constrainHeight(bar.id, size)
            set.connect(
                    bar.id,
                    ConstraintSet.BOTTOM,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.BOTTOM)
            set.setMargin(bar.id, ConstraintSet.END, marginInDp)
            weights[i] = 1f

            currentBars.add(HistogramBarMetaData(bar.id, DEFAULT_HEIGHT_PERCENT, barColour, 0, bar.text.toString()))
            bars[bar.id] = bar
        }
        set.createHorizontalChain(
                ConstraintSet.PARENT_ID, ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID, ConstraintSet.RIGHT,
                barIds.toIntArray(), weights, ConstraintSet.CHAIN_SPREAD
        )
        set.applyTo(layout)
    }

    private fun createBar(context: Context): TextView {
        val bar = TextView(context)
        bar.id = ViewGroup.generateViewId()
        bar.gravity = Gravity.CENTER
        return bar
    }

    private fun fromDp(context: Context, inDp: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (inDp * scale).toInt()
    }
}
