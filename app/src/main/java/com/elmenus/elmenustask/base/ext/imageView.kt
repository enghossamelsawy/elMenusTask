package com.elmenus.elmenustask.base.ext

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.elmenus.elmenustask.R

/**
 * Created by Hossam Elsawy on 7/21/2019.
 */


fun ImageView.loadImage(
    url: String?,
    placeHolderResource: Int = R.color.image_loading_placeholder,
    roundImageView: Boolean = false, maxSize: Boolean = false
) {
    if (roundImageView) {
        Glide.with(this.context)
            .load(url)
            .placeholder(placeHolderResource)
            .override(this.width, this.height)
            .apply(RequestOptions.circleCropTransform())
            .into(this)
    } else {
        if (maxSize) {
            Glide.with(this.context)
                .load(url)
                .placeholder(placeHolderResource)
                .override(this.width, this.height)
                .into(this)
        } else {
            Glide.with(this.context)
                .load(url)
                .placeholder(placeHolderResource)

                .into(this)
        }
    }
}

fun ImageView.loadImageWithCaredEdgeFormResources(
    path: String?,
    placeHolderResource: Int = R.color.image_loading_placeholder
) {
    Glide.with(this.context)
        .load(path)
        .override(this.width, this.height)
        .centerInside()
        .placeholder(placeHolderResource)
        .into(this)
}