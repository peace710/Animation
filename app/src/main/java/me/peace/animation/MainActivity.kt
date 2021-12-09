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

class MainActivity : AppCompatActivity() {
    private val list = arrayListOf<String>("SceneTransition","BasicTransition")

    private val clazz = arrayListOf<Class<*>>(ActivitySceneTransition::class.java,
        BasicTransitionActivity::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ItemAdapter(this,list,clazz)
    }

    class ItemAdapter(private val context: Context, private val list:List<String>,private val clazz:List<Class<*>>):RecyclerView.Adapter<ItemViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val view =
                LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false)
            return ItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int){
            val text = holder.itemView.findViewById<AppCompatTextView>(R.id.text)
            text.text = list[position]
            text.setOnClickListener {
                context?.startActivity(Intent(context,clazz[position]))
            }
        }

        override fun getItemCount(): Int = list?.size


    }

    class ItemViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
}