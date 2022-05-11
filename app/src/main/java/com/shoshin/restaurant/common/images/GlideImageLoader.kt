package com.shoshin.restaurant.common.images

import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.shoshin.restaurant.common.images.interfaces.ImageLoader
import javax.inject.Inject

class GlideImageLoader @Inject constructor(
    private val glide: RequestManager
): ImageLoader {
    override fun load(imageView: ImageView, url: String?) {
        if(url != null && url.isNotEmpty()) {
            glide
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
        }

    }
}