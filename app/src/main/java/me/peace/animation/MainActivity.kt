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
import me.peace.animation.basic.transition.BasicTransition
import me.peace.animation.basic.transition.BasicTransitionActivity
import me.peace.animation.custom.transition.CustomTransitionActivity
import me.peace.animation.drawable.animation.DrawableAnimationActivity
import me.peace.animation.effect.basic.EffectActivity
import me.peace.animation.grid.pager.Grid2PagerActivity
import me.peace.animation.interpolator.InterpolatorActivity
import me.peace.motion.MotionActivity

class MainActivity : AppCompatActivity() {
    private val list = arrayListOf<String>("SceneTransition","BasicTransition","CustomTransition","DrawableAnimation","Grid2Pager","Interpolator","Effect","MotionBasic01","MotionBasic02","MotionBasic02AutoCompleteFalse","Motion03CustomAttribute")

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
        MotionActivity::class.java
        )

    private val layout =  arrayListOf<Int>(R.layout.motion_01_basic,R.layout.motion_02_basic,R.layout.motion_02_basic_autocomplete_false,R.layout.motion_03_custom_attribute)

    companion object{
        private const val MOTION_START = 7
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
                    position >= MOTION_START -> MotionActivity.start(context,clazz[position],layout[position - MOTION_START])
                    else -> context?.startActivity(Intent(context,clazz[position]))
                }
            }
        }

        override fun getItemCount(): Int = list?.size


    }

    class ItemViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
}