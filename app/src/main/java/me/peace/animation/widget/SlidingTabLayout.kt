package me.peace.animation.widget

import android.R
import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

open class SlidingTabLayout @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) :
    HorizontalScrollView(context, attrs, defStyle) {

    interface TabColorizer {

        fun getIndicatorColor(position: Int): Int

        fun getDividerColor(position: Int): Int
    }

    private val mTitleOffset: Int
    private var mTabViewLayoutId = 0
    private var mTabViewTextViewId = 0
    private var mViewPager: ViewPager? = null
    private var mViewPagerPageChangeListener: ViewPager.OnPageChangeListener? = null
    private val mTabStrip: SlidingTabStrip

    fun setCustomTabColorizer(tabColorizer: TabColorizer?) {
        mTabStrip.setCustomTabColorizer(tabColorizer)
    }

    fun setSelectedIndicatorColors(vararg colors: Int) {
        mTabStrip.setSelectedIndicatorColors(*colors)
    }

    fun setDividerColors(vararg colors: Int) {
        mTabStrip.setDividerColors(*colors)
    }


    fun setOnPageChangeListener(listener: ViewPager.OnPageChangeListener?) {
        mViewPagerPageChangeListener = listener
    }


    fun setCustomTabView(layoutResId: Int, textViewId: Int) {
        mTabViewLayoutId = layoutResId
        mTabViewTextViewId = textViewId
    }


    fun setViewPager(viewPager: ViewPager?) {
        mTabStrip.removeAllViews()
        mViewPager = viewPager
        if (viewPager != null) {
            viewPager.setOnPageChangeListener(InternalViewPagerListener())
            populateTabStrip()
        }
    }


    private fun createDefaultTabView(context: Context?): TextView {
        val textView = TextView(context)
        textView.gravity = Gravity.CENTER
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TAB_VIEW_TEXT_SIZE_SP.toFloat())
        textView.setTypeface(Typeface.DEFAULT_BOLD)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            val outValue = TypedValue()
            getContext().theme.resolveAttribute(
                R.attr.selectableItemBackground,
                outValue, true
            )
            textView.setBackgroundResource(outValue.resourceId)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            textView.isAllCaps = true
        }
        val padding = (TAB_VIEW_PADDING_DIPS * resources.displayMetrics.density).toInt()
        textView.setPadding(padding, padding, padding, padding)
        return textView
    }

    private fun populateTabStrip() {
        val adapter: PagerAdapter? = mViewPager?.adapter
        val tabClickListener: OnClickListener = TabClickListener()
        for (i in 0 until adapter?.count!!) {
            var tabView: View? = null
            var tabTitleView: TextView? = null
            if (mTabViewLayoutId != 0) {
                tabView = LayoutInflater.from(context).inflate(
                    mTabViewLayoutId, mTabStrip,
                    false
                )
                tabTitleView = tabView!!.findViewById<View>(mTabViewTextViewId) as TextView
            }
            if (tabView == null) {
                tabView = createDefaultTabView(context)
            }
            if (tabTitleView == null && TextView::class.java.isInstance(tabView)) {
                tabTitleView = tabView as TextView?
            }
            tabTitleView?.text = adapter.getPageTitle(i)
            tabView.setOnClickListener(tabClickListener)
            mTabStrip.addView(tabView)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (mViewPager != null) {
            scrollToTab(mViewPager!!.currentItem, 0)
        }
    }

    private fun scrollToTab(tabIndex: Int, positionOffset: Int) {
        val tabStripChildCount: Int = mTabStrip.childCount
        if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
            return
        }
        val selectedChild: View = mTabStrip.getChildAt(tabIndex)
        if (selectedChild != null) {
            var targetScrollX = selectedChild.left + positionOffset
            if (tabIndex > 0 || positionOffset > 0) {
                targetScrollX -= mTitleOffset
            }
            scrollTo(targetScrollX, 0)
        }
    }

    private inner class InternalViewPagerListener : ViewPager.OnPageChangeListener {
        private var mScrollState = 0
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            val tabStripChildCount: Int = mTabStrip.childCount
            if (tabStripChildCount == 0 || position < 0 || position >= tabStripChildCount) {
                return
            }
            mTabStrip.onViewPagerPageChanged(position, positionOffset)
            val selectedTitle: View = mTabStrip.getChildAt(position)
            val extraOffset =
                if (selectedTitle != null) (positionOffset * selectedTitle.width).toInt() else 0
            scrollToTab(position, extraOffset)
            mViewPagerPageChangeListener?.onPageScrolled(
                position, positionOffset,
                positionOffsetPixels
            )
        }

        override fun onPageScrollStateChanged(state: Int) {
            mScrollState = state
            mViewPagerPageChangeListener?.onPageScrollStateChanged(state)
        }

        override fun onPageSelected(position: Int) {
            if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                mTabStrip.onViewPagerPageChanged(position, 0f)
                scrollToTab(position, 0)
            }
            mViewPagerPageChangeListener?.onPageSelected(position)
        }
    }

    private inner class TabClickListener : OnClickListener {
        override fun onClick(v: View) {
            for (i in 0 until mTabStrip.childCount) {
                if (v === mTabStrip.getChildAt(i)) {
                    mViewPager?.currentItem = i
                    return
                }
            }
        }
    }

    companion object {
        private const val TITLE_OFFSET_DIPS = 24
        private const val TAB_VIEW_PADDING_DIPS = 16
        private const val TAB_VIEW_TEXT_SIZE_SP = 12
    }

    init {

        isHorizontalScrollBarEnabled = false
        isFillViewport = true
        mTitleOffset = (TITLE_OFFSET_DIPS * resources.displayMetrics.density).toInt()
        mTabStrip = context?.let { SlidingTabStrip(it) }!!
        addView(mTabStrip, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }
}