package me.peace.animation.grid.pager.fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import me.peace.animation.R
import me.peace.animation.grid.pager.Grid2PagerActivity
import me.peace.animation.grid.pager.adapter.GridAdapter

class GridFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        recyclerView = inflater.inflate(R.layout.grid_2_pager_fragment_grid, container, false) as RecyclerView?
        recyclerView?.adapter = GridAdapter(this)
        prepareTransitions()
        postponeEnterTransition()
        return recyclerView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scrollToPosition()
    }
    
    private fun scrollToPosition() {
        recyclerView?.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(
                v: View,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {
                recyclerView?.removeOnLayoutChangeListener(this)
                val layoutManager = recyclerView?.layoutManager
                val viewAtPosition =
                    layoutManager?.findViewByPosition(Grid2PagerActivity.currentPosition)
                if (viewAtPosition == null || layoutManager
                        .isViewPartiallyVisible(viewAtPosition, false, true)
                ) {
                    recyclerView?.post {
                        layoutManager?.scrollToPosition(
                            Grid2PagerActivity.currentPosition
                        )
                    }
                }
            }
        })
    }

 
    private fun prepareTransitions() {
        exitTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.grid_exit_transition)
        
        setExitSharedElementCallback(
            object : SharedElementCallback() {
                override fun onMapSharedElements(
                    names: List<String>,
                    sharedElements: MutableMap<String, View>
                ) {
                    val selectedViewHolder = recyclerView?.findViewHolderForAdapterPosition(Grid2PagerActivity.currentPosition) ?: return

                    sharedElements[names[0]] =
                        selectedViewHolder.itemView.findViewById(R.id.grid_2_pager_card_image)
                }
            })
    }
}