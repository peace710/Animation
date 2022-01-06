package me.peace.animation.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.BounceInterpolator
import androidx.constraintlayout.widget.ConstraintHelper
import androidx.constraintlayout.widget.ConstraintLayout


open class FlyinBounceHelper : ConstraintHelper {
    private var mContainer: ConstraintLayout? = null

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    override fun updatePreLayout(container: ConstraintLayout) {
        if (mContainer !== container) {
            val views = getViews(container)
            for (i in 0 until mCount) {
                val view = views[i]
                val animator =
                    ObjectAnimator.ofFloat(view, "translationX", -2000f, 0f).setDuration(1000)
                animator.interpolator = BounceInterpolator()
                animator.start()
            }
        }
        mContainer = container
    }
}
