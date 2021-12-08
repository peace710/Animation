package me.peace.animation.activity.scene

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.ViewCompat
import androidx.transition.Transition
import com.squareup.picasso.Picasso
import me.peace.animation.R


class DetailActivity:AppCompatActivity() {

    object Engine {
        val EXTRA_PARAM_ID = "detail:_id"

        val VIEW_NAME_HEADER_IMAGE = "detail:header:image"

        val VIEW_NAME_HEADER_TITLE = "detail:header:title"
    }

    private lateinit var headerImage: AppCompatImageView
    private lateinit var headerTitle:AppCompatTextView

    var item:Item?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scene_details)

        item = Item.Engine.getItem(intent.getIntExtra(Engine.EXTRA_PARAM_ID,0))

        headerImage = findViewById<AppCompatImageView>(R.id.image_header)
        headerTitle = findViewById<AppCompatTextView>(R.id.text_title)

        ViewCompat.setTransitionName(headerImage,Engine.VIEW_NAME_HEADER_IMAGE)
        ViewCompat.setTransitionName(headerTitle,Engine.VIEW_NAME_HEADER_TITLE)

        loadItem()

    }

    private fun loadItem(){
        headerTitle.text = getString(R.string.image_header,item?.name,item?.author)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && addTransitionListener()) {
            loadThumbnail()
        } else {
            loadFullSizeImage()
        }
    }

    private fun loadThumbnail() {
        Picasso.with(headerImage.context)
            .load(item?.getThumbnailUrl())
            .noFade()
            .into(headerImage)
    }

    private fun loadFullSizeImage() {
        Picasso.with(headerImage.context)
            .load(item?.getPhotoUrl())
            .noFade()
            .noPlaceholder()
            .into(headerImage)
    }

    @RequiresApi(21)
    fun addTransitionListener(): Boolean {

        val sharedElementEnterTransition = window.sharedElementEnterTransition as? Transition ?: return false

        sharedElementEnterTransition?.addListener(object:Transition.TransitionListener{
            override fun onTransitionStart(transition: Transition) {

            }

            override fun onTransitionEnd(transition: Transition) {
                loadFullSizeImage()
                transition.removeListener(this)
            }

            override fun onTransitionCancel(transition: Transition) {
               transition.removeListener(this)
            }

            override fun onTransitionPause(transition: Transition) {

            }

            override fun onTransitionResume(transition: Transition) {

            }

        })

        return true

    }


}