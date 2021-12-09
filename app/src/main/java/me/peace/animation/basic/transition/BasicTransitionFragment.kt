package me.peace.animation.basic.transition

import android.os.Bundle
import android.transition.Scene
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import me.peace.animation.R


class BasicTransitionFragment:Fragment(),RadioGroup.OnCheckedChangeListener{

    lateinit var scene1:Scene
    lateinit var scene2:Scene
    lateinit var scene3:Scene

    lateinit var manager: TransitionManager

    lateinit var sceneRoot:ViewGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =
            inflater.inflate(R.layout.activity_basic_transition_fragment, container, false)!!
        val radioGroup = view.findViewById<View>(R.id.basic_transition_select_scene) as RadioGroup
        radioGroup.setOnCheckedChangeListener(this)
        sceneRoot = view.findViewById<View>(R.id.basic_transition_scene_root) as ViewGroup

        scene1 = Scene(sceneRoot, sceneRoot.findViewById<View>(R.id.basic_transition_container) as ViewGroup)
        scene2 = Scene.getSceneForLayout(sceneRoot, R.layout.basic_transition_scene2, activity)
        scene3 = Scene.getSceneForLayout(sceneRoot, R.layout.basic_transition_scene3, activity)

        manager = TransitionInflater.from(activity)
            .inflateTransitionManager(R.transition.scene3_transition_manager, sceneRoot)
        return view
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId){
            R.id.basic_transition_select_scene_1 -> TransitionManager.go(scene1)
            R.id.basic_transition_select_scene_2 -> TransitionManager.go(scene2)
            R.id.basic_transition_select_scene_3 -> manager.transitionTo(scene3)
            R.id.basic_transition_select_scene_4 -> {
                TransitionManager.beginDelayedTransition(sceneRoot)
                val square = sceneRoot.findViewById<View>(R.id.basic_transition_square)
                val size = resources.getDimensionPixelSize(R.dimen.square_size_expanded)
                square.layoutParams.width = size
                square.layoutParams.height = size
            }
        }
    }
}

