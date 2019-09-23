package com.elmenus.elmenustask.menustags.presentation


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.elmenus.elmenustask.R
import com.elmenus.elmenustask.base.ext.loadImageWithCaredEdgeFormResources
import com.elmenus.elmenustask.menustags.data.TagObject
import kotlinx.android.synthetic.main.item_tag.view.*

/**
 * View Holder for a [TagObject] RecyclerView list item.
 */
class TagsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.tvName
    private val tagImage: ImageView = view.tagImage
    private var tag: TagObject? = null

    fun bind(tag: TagObject?) {
        if (tag != null) {
            showRepoData(tag)
        }
    }

    private fun showRepoData(tag: TagObject) {
        this.tag = tag
        name.text = tag.tagName
        tagImage.loadImageWithCaredEdgeFormResources(tag.photoURL)
    }

    companion object {
        fun create(parent: ViewGroup): TagsViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_tag, parent, false)
            return TagsViewHolder(view)
        }
    }
}
