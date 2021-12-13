package me.peace.animation.grid.pager.fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import me.peace.animation.R
import me.peace.animation.grid.pager.Grid2PagerActivity
import me.peace.animation.grid.pager.adapter.ImagePagerAdapter

class ImagePagerFragment : Fragment() {
    private var viewPager: ViewPager? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewPager = inflater.inflate(R.layout.grid_2_pager_fragment_pager, container, false) as ViewPager?
        viewPager!!.adapter = ImagePagerAdapter(this)
        viewPager!!.currentItem = Grid2PagerActivity.currentPosition
        viewPager!!.addOnPageChangeListener(object : SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                Grid2PagerActivity.currentPosition = position
            }
        })
        prepareSharedElementTransition()

        if (savedInstanceState == null) {
            postponeEnterTransition()
        }
        return viewPager
    }

    private fun prepareSharedElementTransition() {
        val transition = TransitionInflater.from(
            context
        )
            .inflateTransition(R.transition.image_shared_element_transition)
        sharedElementEnterTransition = transition

        setEnterSharedElementCallback(
            object : SharedElementCallback() {
                override fun onMapSharedElements(
                    names: List<String>,
                    sharedElements: MutableMap<String, View>
                ) {
                    val currentFragment = viewPager!!.adapter
                        ?.instantiateItem(viewPager!!, Grid2PagerActivity.currentPosition) as Fragment
                    val view = currentFragment.view ?: return

                    sharedElements[names[0]] = view.findViewById(R.id.grid_2_pager_image)
                }
            })
    }
}