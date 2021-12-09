package me.peace.animation.custom.transition

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import androidx.transition.Transition
import androidx.transition.TransitionValues

class ChangeColor:Transition() {

    private val PROPNAME_BACKGROUND = "customtransition:change_color:background"


    private fun captureValues(transitionValues: TransitionValues){
        transitionValues.values[PROPNAME_BACKGROUND] = transitionValues.view.background
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
       captureValues(transitionValues)
    }

   override fun createAnimator(
       root: ViewGroup,
       startValues: TransitionValues?,
       endValues: TransitionValues?
   ): Animator? {
        if (startValues == null || endValues == null){
            return null
        }

        val view = endValues.view
        val startBackground = startValues.values[PROPNAME_BACKGROUND] as Drawable
        val endBackground = endValues.values[PROPNAME_BACKGROUND] as Drawable

        if (startBackground is ColorDrawable && endBackground is ColorDrawable){
            val startColor = startBackground
            val endColor = endBackground

            if (startColor != endColor){
                val animator = ValueAnimator.ofObject(ArgbEvaluator(),startColor.color,endColor.color);
                animator.addUpdateListener {
                    val animatedValue = it.animatedValue as? Int
                    if (animatedValue != null){
                        view.setBackgroundColor(animatedValue)
                    }
                }
                return animator
            }
        }
        return null
    }

}