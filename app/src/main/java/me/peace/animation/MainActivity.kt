package me.peace.animation

import android.view.View
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.peace.animation.activity.scene.ActivitySceneTransition
import me.peace.animation.basic.transition.BasicTransitionActivity
import me.peace.animation.custom.transition.CustomTransitionActivity
import me.peace.animation.drawable.animation.DrawableAnimationActivity
import me.peace.animation.effect.basic.EffectActivity
import me.peace.animation.fragment.FragmentMotionActivity
import me.peace.animation.fragment.FragmentMotionActivity2
import me.peace.animation.grid.pager.Grid2PagerActivity
import me.peace.animation.interpolator.InterpolatorActivity
import me.peace.animation.viewpager.ViewPagerActivity
import me.peace.animation.viewpager.ViewPagerActivity2
import me.peace.animation.motion.MotionActivity
import me.peace.animation.youtube.YouTubeActivity

class MainActivity : AppCompatActivity() {
    private val list = arrayListOf<String>(
        "SceneTransition",
        "BasicTransition",
        "CustomTransition",
        "DrawableAnimation",
        "Grid2Pager",
        "Interpolator",
        "Effect",
        "MotionBasic01",
        "MotionBasic02",
        "MotionBasic02AutoCompleteFalse",
        "Motion03CustomAttribute",
        "Motion04ImageFilter",
        "Motion05ImageFilter",
        "Motion06FrameKeySet",
        "Motion07FrameKeySet",
        "Motion08Cycle",
        "Motion09Coordinator",
        "Motion10Coordinator",
        "Motion11Coordinator",
        "Motion12Drawer",
        "Motion13Drawer",
        "Motion14SlidePanel",
        "Motion15Parallax",
        "Motion16ViewPager",
        "Motion16ViewPagerLottie",
        "Motion17ComplexMotion",
        "Motion18ComplexMotion",
        "Motion19ComplexMotion",
        "Motion20Reveal",
        "Motion21Fragment",
        "Motion22Fragment",
        "Motion24Youtube",
        "Motion25KeyTrigger",
        "Motion26Multistate"
    )

    private val clazz = arrayListOf<Class<*>>(
        ActivitySceneTransition::class.java,
        BasicTransitionActivity::class.java,
        CustomTransitionActivity::class.java,
        DrawableAnimationActivity::class.java,
        Grid2PagerActivity::class.java,
        InterpolatorActivity::class.java,
        EffectActivity::class.java,
        MotionActivity::class.java,
        MotionActivity::class.java,
        MotionActivity::class.java,
        MotionActivity::class.java,
        MotionActivity::class.java,
        MotionActivity::class.java,
        MotionActivity::class.java,
        MotionActivity::class.java,
        MotionActivity::class.java,
        MotionActivity::class.java,
        MotionActivity::class.java,
        MotionActivity::class.java,
        MotionActivity::class.java,
        MotionActivity::class.java,
        MotionActivity::class.java,
        MotionActivity::class.java,
        ViewPagerActivity::class.java,
        ViewPagerActivity2::class.java,
        MotionActivity::class.java,
        MotionActivity::class.java,
        MotionActivity::class.java,
        MotionActivity::class.java,
        FragmentMotionActivity::class.java,
        FragmentMotionActivity2::class.java,
        YouTubeActivity::class.java,
        MotionActivity::class.java,
        MotionActivity::class.java
        )

    private val layout =  arrayListOf<Int>(R.layout.motion_01_basic,
        R.layout.motion_02_basic,
        R.layout.motion_02_basic_autocomplete_false,
        R.layout.motion_03_custom_attribute,
        R.layout.motion_04_imagefilter,
        R.layout.motion_05_imagefilter,
        R.layout.motion_06_keyframe,
        R.layout.motion_07_keyframe,
        R.layout.motion_08_cycle,
        R.layout.motion_09_coordinatorlayout,
        R.layout.motion_10_coordinatorlayout,
        R.layout.motion_11_coordinatorlayout,
        R.layout.motion_12_drawerlayout,
        R.layout.motion_13_drawerlayout,
        R.layout.motion_14_side_panel,
        R.layout.motion_15_parallax,
        R.layout.motion_16_viewpager,
        R.layout.motion_23_viewpager,
        R.layout.motion_17_coordination,
        R.layout.motion_18_coordination,
        R.layout.motion_19_coordination,
        R.layout.motion_20_reveal,
        R.layout.activity_fragment_main,
        R.layout.activity_fragment_main,
        R.layout.motion_24_youtube,
        R.layout.motion_25_keytrigger,
        R.layout.motion_26_multistate
    )

    companion object{
        private const val MOTION_START = 7
        private const val MOTION_VIEW_PAGE = 23
        private const val MOTION_VIEW_PAGE_LOTTIE = 24
        private const val MOTION_FRAGMENT_MAIN = 29
        private const val MOTION_FRAGMENT_LIST = 30
        private const val MOTION_YOUTUBE = 31
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ItemAdapter(this,list,clazz,layout)
    }

    class ItemAdapter(private val context: Context, private val list:List<String>,private val clazz:List<Class<*>>,private val layout:List<Int>):RecyclerView.Adapter<ItemViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val view =
                LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false)
            return ItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int){
            val text = holder.itemView.findViewById<AppCompatTextView>(R.id.text)
            text.text = list[position]
            text.setOnClickListener {
                when {
                    position == MOTION_YOUTUBE -> YouTubeActivity.start(context,clazz[position],layout[position - MOTION_START])
                    position == MOTION_FRAGMENT_LIST -> FragmentMotionActivity2.start(context,clazz[position],layout[position - MOTION_START])
                    position == MOTION_FRAGMENT_MAIN -> FragmentMotionActivity.start(context,clazz[position],layout[position - MOTION_START])
                    position == MOTION_VIEW_PAGE_LOTTIE -> ViewPagerActivity2.start(context,clazz[position],layout[position - MOTION_START])
                    position == MOTION_VIEW_PAGE -> ViewPagerActivity.start(context,clazz[position],layout[position - MOTION_START])
                    position >= MOTION_START -> MotionActivity.start(context,clazz[position],layout[position - MOTION_START])
                    else -> context?.startActivity(Intent(context,clazz[position]))
                }
            }
        }

        override fun getItemCount(): Int = list?.size


    }

    class ItemViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
}