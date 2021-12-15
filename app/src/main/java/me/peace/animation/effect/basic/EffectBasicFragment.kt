package me.peace.animation.effect.basic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import me.peace.animation.R
import me.peace.animation.logger.Log
import kotlin.math.hypot

class EffectBasicFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.effect_basic, container, false)
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        val shape = view.findViewById<View>(R.id.effect_circle)
        val button = view.findViewById<View>(R.id.effect_button)
        button.setOnClickListener {
            val circularReveal = ViewAnimationUtils.createCircularReveal(
                shape,
                0,
                0, 0f,
                hypot(shape.width.toDouble(), shape.height.toDouble()).toFloat()
            )
            circularReveal.interpolator = AccelerateDecelerateInterpolator()

            circularReveal.start()
            Log.d(TAG, "Starting Reveal animation")
        }
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        private const val TAG = "RevealEffectBasicFragment"
    }
}