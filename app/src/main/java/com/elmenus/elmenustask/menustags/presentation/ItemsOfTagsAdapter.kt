package com.elmenus.elmenustask.menustags.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmenus.elmenustask.menustags.data.ItemOfTags

class ItemsOfTagsAdapter(
    var items: List<ItemOfTags>,
    private val onItemSelected: (ItemOfTags) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemsTagsViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tagItem = items[holder.adapterPosition]
        if (tagItem != null) {
            (holder as ItemsTagsViewHolder).bind(tagItem)
            holder.itemView.setOnClickListener {
                onItemSelected(tagItem)
            }
        }
    }
}