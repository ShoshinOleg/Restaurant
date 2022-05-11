package com.shoshin.restaurant.common.images

import android.widget.ImageView
import com.shoshin.restaurant.common.images.interfaces.ImageLoader
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PicassoImageLoader @Inject constructor(
    private val picasso: Picasso
): ImageLoader {

    override fun load(imageView: ImageView, url: String?) {
        if(url != null && url.isNotEmpty()) {
            picasso
                .load(url)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .noFade()
                .into(imageView)
        }
    }
}