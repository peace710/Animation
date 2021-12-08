package me.peace.animation.activity.scene

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class SquareFrameLayout : FrameLayout {
    @JvmOverloads
    constructor(context: Context,attrs:AttributeSet? = null,defStyleAttr:Int = 0):super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        if ( widthSize == 0 && heightSize == 0) {
            super.onMeasure(widthSize, heightSize)

            val minSize = measuredWidth.coerceAtMost(measuredHeight)
            setMeasuredDimension(minSize,minSize)
            return
        }

        val size = if (widthSize == 0 || heightSize == 0){
            widthSize.coerceAtLeast(heightSize)
        }else{
            widthSize.coerceAtMost(heightSize)
        }

        val newMeasureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY)
        super.onMeasure(newMeasureSpec, newMeasureSpec)
    }
}