package me.peace.animation.histogram
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import me.peace.animation.R


class NonAnimatingHistogramWidget : ConstraintLayout {
    companion object {
        private const val TAG = "NonAnimatingHistogramWidget"
        private const val DEFAULT_HEIGHT_DP = 1
        private const val DEFAULT_HEIGHT_PERCENT = 0.01f
    }

    private val bars: MutableMap<Int, TextView> = HashMap()

    private var currentBars: MutableList<HistogramBarMetaData> = ArrayList()
    private var nextBars: ArrayList<HistogramBarMetaData> = ArrayList()

    private var leftMarginDp = 0

    private val weights: FloatArray

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
    }

    fun setData(newData: List<HistogramBarMetaData>) {
        val endSet = ConstraintSet()
        endSet.clone(this)

        updateConstraintSet(endSet, newData)
        nextBars = ArrayList(newData)
        endSet.applyTo(this)
    }

    fun animateWidget() {
    }

    fun sort(): ArrayList<HistogramBarMetaData> {

        return nextBars
    }

    private fun updateConstraintSet(
            set: ConstraintSet,
            list: List<HistogramBarMetaData>) {
        list.forEach { metadata ->
            val view = bars[metadata.id]!!
            val height: Float = metadata.height * height
            view.setTextColor(metadata.barTextColour)
            view.text = metadata.name

            set.constrainHeight(view.id, height.toInt())
            set.setColorValue(view.id, "BackgroundColor", metadata.barColour)
        }
    }

    private fun createBars(layout: ConstraintLayout, columns: Int) {
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
