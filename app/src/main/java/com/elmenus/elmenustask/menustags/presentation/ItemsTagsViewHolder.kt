package com.elmenus.elmenustask.menustags.presentation


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.elmenus.elmenustask.R
import com.elmenus.elmenustask.base.ext.loadImage
import com.elmenus.elmenustask.menustags.data.ItemOfTags
import com.elmenus.elmenustask.menustags.data.TagObject
import kotlinx.android.synthetic.main.item_tag.view.*

/**
 * View Holder for a [TagObject] RecyclerView list item.
 */
class ItemsTagsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.tvName
    private val tagImage: ImageView = view.tagImage


    private var tag: ItemOfTags? = null


    fun bind(tag: ItemOfTags?) {
        if (tag != null) {
            showRepoData(tag)
        }
    }

    private fun showRepoData(tag: ItemOfTags) {
        this.tag = tag
        name.text = tag.name
        tagImage.loadImage(tag.photoUrl)


    }

    companion object {
        fun create(parent: ViewGroup): ItemsTagsViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_tags_item, parent, false)
            return ItemsTagsViewHolder(view)
        }
    }
}
