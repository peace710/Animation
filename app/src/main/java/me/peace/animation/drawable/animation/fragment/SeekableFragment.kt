package me.peace.animation.drawable.animation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import androidx.vectordrawable.graphics.drawable.SeekableAnimatedVectorDrawable
import me.peace.animation.R


class SeekableFragment : Fragment() {
    private lateinit var icon:AppCompatImageView
    private lateinit var start:AppCompatButton
    private lateinit var stop:AppCompatButton
    private lateinit var seek:SeekBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =
            inflater.inflate(R.layout.activity_drawable_animation_seekable_fragment, container, false)!!
        icon = view.findViewById(R.id.icon)
        start = view.findViewById(R.id.start)
        stop = view.findViewById(R.id.stop)
        seek = view.findViewById(R.id.seek)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (icon.drawable as? AnimatedVectorDrawableCompat)?.clearAnimationCallbacks()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val drawable = SeekableAnimatedVectorDrawable.create(
            requireContext(),
            R.drawable.ic_hourglass_animated
        )!!

        drawable.registerAnimationCallback(object : SeekableAnimatedVectorDrawable.AnimationCallback() {
            override fun onAnimationStart(drawable: SeekableAnimatedVectorDrawable) {
                start.setText(R.string.pause)
                stop.isEnabled = true
            }

            override fun onAnimationPause(drawable: SeekableAnimatedVectorDrawable) {
                start.setText(R.string.resume)
            }

            override fun onAnimationResume(drawable: SeekableAnimatedVectorDrawable) {
                start.setText(R.string.pause)
            }

            override fun onAnimationEnd(drawable: SeekableAnimatedVectorDrawable) {
                start.setText(R.string.start)
                stop.isEnabled = false
            }

            override fun onAnimationUpdate(drawable: SeekableAnimatedVectorDrawable) {
                seek.progress = (seek.max * (drawable.currentPlayTime.toFloat() /
                        drawable.totalDuration.toFloat())).toInt()
            }
        })
        icon.setImageDrawable(drawable)
        start.setOnClickListener {
            when {
                drawable.isPaused -> drawable.resume()
                drawable.isRunning -> drawable.pause()
                else -> drawable.start()
            }
        }
        stop.setOnClickListener { drawable.stop() }
        seek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    drawable.currentPlayTime = (drawable.totalDuration *
                            (progress.toFloat() / seekBar.max.toFloat())).toLong()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }
}