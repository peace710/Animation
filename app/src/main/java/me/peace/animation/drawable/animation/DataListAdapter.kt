package me.peace.animation.drawable.animation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.peace.animation.R

internal class DataListAdapter(
    private val dataClick: (Data) -> Unit
) : ListAdapter<Data, DataViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(LayoutInflater.from(parent.context), parent).apply {
            itemView.setOnClickListener {
                dataClick(getItem(bindingAdapterPosition))
            }
        }
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val title = holder.itemView.findViewById<AppCompatTextView>(R.id.title)
        title.text = getItem(position).title
    }
}

internal val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Data>() {

    override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem == newItem
    }
}

internal class DataViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    inflater.inflate(R.layout.drawable_animation_item, parent, false)
)