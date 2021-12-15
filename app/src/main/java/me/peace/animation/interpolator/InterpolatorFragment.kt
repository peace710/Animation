package me.peace.animation.interpolator

import android.animation.ObjectAnimator
import android.graphics.Path
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import me.peace.animation.R
import me.peace.animation.logger.Log


class InterpolatorFragment : Fragment() {

    private var mView: View? = null

    private var mInterpolatorSpinner: AppCompatSpinner? = null

    private var mDurationSeekbar: SeekBar? = null

    private var mDurationLabel: AppCompatTextView? = null

    private lateinit var interpolator: Array<Interpolator>

    private var pathIn: Path? = null

    var pathOut: Path? = null
        private set

    private var mIsOut = false


    private lateinit var mInterpolatorNames: Array<String>
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInterpolator()
        mInterpolatorNames = resources.getStringArray(R.array.interpolator_names)
        initPaths()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.interpolator_fragment, container, false)
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        initAnimateButton(view)


        mDurationLabel = view.findViewById<View>(R.id.interpolator_duration_label) as AppCompatTextView


        mInterpolatorSpinner = view.findViewById<View>(R.id.interpolator_spinner) as AppCompatSpinner
        val spinnerAdapter = activity?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_dropdown_item, mInterpolatorNames
            )
        }
        mInterpolatorSpinner!!.adapter = spinnerAdapter
        initSeekbar(view)


        mView = view.findViewById(R.id.interpolator_square)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initAnimateButton(view: View) {
        val button = view.findViewById<View>(R.id.interpolator_animate_button)
        button.setOnClickListener {
            val selectedItemPosition = mInterpolatorSpinner!!.selectedItemPosition
            val interpolator = interpolator[selectedItemPosition]
            val duration = mDurationSeekbar!!.progress.toLong()
            val path: Path? = if (mIsOut) pathIn else pathOut

            Log.i(
                TAG, String.format(
                    "Starting animation: [%d ms, %s, %s]",
                    duration, mInterpolatorSpinner!!.selectedItem as String,
                    if (mIsOut) "Out (growing)" else "In (shrinking)"
                )
            )

            startAnimation(interpolator, duration, path)
            mIsOut = !mIsOut
        }
    }

    private fun initSeekbar(view: View) {
        mDurationSeekbar = view.findViewById<View>(R.id.interpolator_duration_seek) as SeekBar

        mDurationSeekbar!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                mDurationLabel?.text =
                    resources.getString(R.string.interpolator_animation_duration, i)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        mDurationSeekbar!!.progress = INITIAL_DURATION_MS
    }

    private fun startAnimation(interpolator: Interpolator?, duration: Long, path: Path?): ObjectAnimator {
        val animator = ObjectAnimator.ofFloat(mView, View.SCALE_X, View.SCALE_Y, path)

        animator.duration = duration
        animator.interpolator = interpolator
        animator.start()
        return animator
    }

    private fun initInterpolator() {
        interpolator = arrayOf(
            AnimationUtils.loadInterpolator(
                activity,
                android.R.interpolator.linear
            ),
            AnimationUtils.loadInterpolator(
                activity,
                android.R.interpolator.fast_out_linear_in
            ),
            AnimationUtils.loadInterpolator(
                activity,
                android.R.interpolator.fast_out_slow_in
            ),
            AnimationUtils.loadInterpolator(
                activity,
                android.R.interpolator.linear_out_slow_in
            )
        )
    }

    private fun initPaths() {
        pathIn = Path()
        pathIn!!.moveTo(0.2f, 0.2f)
        pathIn!!.lineTo(1f, 1f)

        pathOut = Path()
        pathOut!!.moveTo(1f, 1f)
        pathOut!!.lineTo(0.2f, 0.2f)
    }

    companion object {

        private const val INITIAL_DURATION_MS = 750

        const val TAG = "InterpolatorPlaygroundFragment"
    }
}