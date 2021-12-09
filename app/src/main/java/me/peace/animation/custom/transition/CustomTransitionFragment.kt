package me.peace.animation.custom.transition

import android.os.Bundle
import androidx.transition.Scene
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.transition.Transition
import androidx.transition.TransitionManager
import me.peace.animation.R


class CustomTransitionFragment:Fragment(),View.OnClickListener {

    private val STATE_CURRENT_SCENE = "current_scene"

    private val TAG = "CustomTransitionFragment"

    private lateinit var scenes: Array<Scene>

    private var currentScene:Int? = 0

    private var transition: Transition? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_custom_transition_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val container = view.findViewById<FrameLayout>(R.id.custom_transition_container)
        view.findViewById<AppCompatButton>(R.id.show_next_scene).setOnClickListener(this)
        currentScene = savedInstanceState?.getInt(STATE_CURRENT_SCENE)?:0
        scenes = arrayOf(Scene.getSceneForLayout(container,R.layout.custom_transition_scene1, context!!),
            Scene.getSceneForLayout(container,R.layout.custom_transition_scene2,context!!),
            Scene.getSceneForLayout(container,R.layout.custom_transition_scene3,context!!))

        transition = ChangeColor()
        TransitionManager.go(scenes[currentScene?.rem(scenes.size)!!])
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        currentScene?.let { outState.putInt(STATE_CURRENT_SCENE, it) }
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.show_next_scene -> {
                currentScene = currentScene?.plus(1 )?.rem(scenes.size)
                TransitionManager.go(scenes[currentScene!!],transition)
            }
        }
    }

}