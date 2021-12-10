package me.peace.animation.drawable.animation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import me.peace.animation.R
import me.peace.animation.drawable.animation.Data
import me.peace.animation.drawable.animation.DataListAdapter

class HomeFragment : Fragment() {

    private lateinit var list:RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =
            inflater.inflate(R.layout.activity_drawable_animation_home_fragment, container, false)!!
        list = view.findViewById(R.id.list)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = DataListAdapter { data ->
            activity?.let { activity ->
                activity.supportFragmentManager.commit {
                    replace(R.id.drawable_animation_main, data.createFragment())
                    addToBackStack(null)
                }
                activity.title = data.title
            }
        }
        list.adapter = adapter
        adapter.submitList(listOf(
            Data("AnimatedVectorDrawableCompat") { AnimatedFragment() },
            Data("SeekableAnimatedVectorDrawable") { SeekableFragment() }
        ))
    }

    override fun onResume() {
        super.onResume()
        activity?.setTitle(R.string.app_name)
    }
}
