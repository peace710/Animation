package me.peace.animation.drawable.animation.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import me.peace.animation.R


class AnimatedFragment:Fragment() {

    private lateinit var icon: AppCompatImageView
    private lateinit var start: AppCompatButton
    private lateinit var stop: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =
            inflater.inflate(R.layout.activity_drawable_animation_fragment, container, false)!!
        icon = view.findViewById(R.id.icon)
        start = view.findViewById(R.id.start)
        stop = view.findViewById(R.id.stop)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (icon.drawable as? AnimatedVectorDrawableCompat)?.clearAnimationCallbacks()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val drawable = AnimatedVectorDrawableCompat.create(
            requireContext(),
            R.drawable.ic_hourglass_animated
        )!!

        drawable.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
            override fun onAnimationStart(drawable: Drawable?) {
                start.isEnabled = false
                stop.isEnabled = true
            }

            override fun onAnimationEnd(drawable: Drawable?) {
                start.isEnabled = true
                stop.isEnabled = false
            }
        })
        icon.setImageDrawable(drawable)
        start.setOnClickListener { drawable.start() }
        stop.setOnClickListener { drawable.stop() }
    }
}