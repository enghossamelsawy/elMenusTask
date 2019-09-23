package  com.elmenus.elmenustask.menustags.presentation

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elmenus.elmenustask.menustags.data.TagObject


/**
 * Adapter for the list of tags.
 */
class TagsAdapter(var onItemClicked: (tagName: String) -> Unit) :
    PagedListAdapter<TagObject, RecyclerView.ViewHolder>(REPO_COMPARATOR) {
    var index = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TagsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tagItem = getItem(position)
        if (tagItem != null) {
            (holder as TagsViewHolder).bind(tagItem)
            if (index == -1) {
                onItemClicked(tagItem.tagName)
                index = position
            }
            holder.itemView.setOnClickListener {
                onItemClicked(tagItem.tagName)
                index = position
                notifyDataSetChanged()
            }
            holder.itemView.isSelected = index == position
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<TagObject>() {
            override fun areItemsTheSame(oldItem: TagObject, newItem: TagObject): Boolean =
                oldItem.tagName == newItem.tagName

            override fun areContentsTheSame(oldItem: TagObject, newItem: TagObject): Boolean =
                oldItem == newItem
        }
    }
}
