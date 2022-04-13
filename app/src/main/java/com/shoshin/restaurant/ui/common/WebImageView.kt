package com.shoshin.restaurant.ui.common

import android.widget.ImageView
import com.shoshin.restaurant.R
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

class WebImageView(private val imageView: ImageView) {
    fun load(url: String?) {
        if(url != null) {
            Picasso.get()
                .load(url)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView)
        } else {
            imageView.setImageResource(R.drawable.ic_food)
        }
    }
}