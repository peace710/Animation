package me.peace.animation.grid.pager.adapter

import android.graphics.drawable.Drawable
import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import me.peace.animation.R
import me.peace.animation.grid.pager.Grid2PagerActivity
import me.peace.animation.grid.pager.adapter.ImageData.IMAGE_DRAWABLES
import me.peace.animation.grid.pager.fragment.ImagePagerFragment
import java.lang.String
import java.util.concurrent.atomic.AtomicBoolean

class GridAdapter(fragment: Fragment) :
    RecyclerView.Adapter<GridAdapter.ImageViewHolder>() {

    interface ViewHolderListener {
        fun onLoadCompleted(view: ImageView?, adapterPosition: Int)
        fun onItemClicked(view: View?, adapterPosition: Int)
    }

    private var requestManager: RequestManager = Glide.with(fragment)
    private var viewHolderListener: ViewHolderListener = ViewHolderListenerImpl(fragment)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.grid_2_pager_image_card, parent, false)
        return ImageViewHolder(view, requestManager, viewHolderListener)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.onBind()
    }

    override fun getItemCount(): Int {
        return IMAGE_DRAWABLES.size
    }

    private class ViewHolderListenerImpl internal constructor(private val fragment: Fragment) :
        ViewHolderListener {
        private val enterTransitionStarted: AtomicBoolean = AtomicBoolean()
        override fun onLoadCompleted(view: ImageView?, position: Int) {
            if (Grid2PagerActivity.currentPosition != position) {
                return
            }
            if (enterTransitionStarted.getAndSet(true)) {
                return
            }
            fragment.startPostponedEnterTransition()
        }

        override fun onItemClicked(view: View?, adapterPosition: Int) {

            Grid2PagerActivity.currentPosition = adapterPosition

            (fragment.exitTransition as TransitionSet?)!!.excludeTarget(view, true)

            val transitioningView = view!!.findViewById<ImageView>(R.id.grid_2_pager_card_image)
            fragment.fragmentManager
               ?.beginTransaction()
               ?.setReorderingAllowed(true)
               ?.addSharedElement(transitioningView, transitioningView.transitionName)
               ?.replace(
                    R.id.grid_2_pager_fragment_container, ImagePagerFragment(), ImagePagerFragment::class.java
                        .simpleName
                )
                ?.addToBackStack(null)
                ?.commit()
        }


    }

    class ImageViewHolder(
        itemView: View, requestManager: RequestManager,
        viewHolderListener: GridAdapter.ViewHolderListener
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var image: AppCompatImageView = itemView.findViewById(R.id.grid_2_pager_card_image)
        private val requestManager: RequestManager = requestManager
        private val viewHolderListener: GridAdapter.ViewHolderListener = viewHolderListener


        fun onBind() {
            val adapterPosition = adapterPosition
            setImage(adapterPosition)
            image.transitionName = String.valueOf(IMAGE_DRAWABLES[adapterPosition])
        }

        private fun setImage(adapterPosition: Int) {
            requestManager
                .load(IMAGE_DRAWABLES[adapterPosition])
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?, model: Any,
                        target: Target<Drawable?>, isFirstResource: Boolean
                    ): Boolean {
                        viewHolderListener.onLoadCompleted(image, adapterPosition)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any,
                        target: Target<Drawable?>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        viewHolderListener.onLoadCompleted(image, adapterPosition)
                        return false
                    }
                })
                .into(image)
        }

        override fun onClick(view: View) {
            viewHolderListener.onItemClicked(view, adapterPosition)
        }

        init {
            itemView.findViewById<View>(R.id.grid_2_pager_card_view).setOnClickListener(this)
        }
    }


}