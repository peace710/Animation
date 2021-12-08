package me.peace.animation.activity.scene

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.squareup.picasso.Picasso
import me.peace.animation.R


class ActivitySceneTransition:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scene_grid)

        val grid = findViewById<GridView>(R.id.grid)
        grid.setOnItemClickListener { parent, view, position, _ ->
            val item = parent.getItemAtPosition(position) as Item
            intent = Intent(this,DetailActivity::class.java)
            intent.putExtra(DetailActivity.Engine.EXTRA_PARAM_ID,item.getId())


            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                Pair<View, String>(
                    view.findViewById(R.id.image_item),
                    DetailActivity.Engine.VIEW_NAME_HEADER_IMAGE
                ),
                Pair<View, String>(
                    view.findViewById(R.id.text_name),
                    DetailActivity.Engine.VIEW_NAME_HEADER_TITLE
                )
            )

            ActivityCompat.startActivity(this,intent,activityOptions.toBundle())
        }

        grid.adapter = GridAdapter(this)
    }

    private class GridAdapter(val context: Context) : BaseAdapter() {
        override fun getCount(): Int {
            return Item.Engine.ITEMS.size
        }

        override fun getItem(position: Int): Item {
            return Item.Engine.ITEMS[position]
        }

        override fun getItemId(position: Int): Long {
            return getItem(position).getId().toLong()
        }

        override fun getView(position: Int, v: View?, parent: ViewGroup): View? {
            var view: View? = v
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.activity_scene_grid_item, parent, false)
            }
            val item = getItem(position)

            val image = view?.findViewById<AppCompatImageView>(R.id.image_item)
            Picasso.with(image?.context).load(item?.getThumbnailUrl()).into(image)

            val name = view?.findViewById<AppCompatTextView>(R.id.text_name)
            name?.text = item?.name
            return view
        }
    }
}