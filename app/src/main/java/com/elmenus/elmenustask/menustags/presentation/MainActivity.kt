package com.elmenus.elmenustask.menustags.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.elmenus.elmenustask.R
import com.elmenus.elmenustask.base.ext.showSnakeBar
import com.elmenus.elmenustask.menustags.data.ItemOfTags
import com.elmenus.elmenustask.menustags.data.TagObject
import com.elmenus.elmenustask.tagsDetails.TagsDetailsActivity
import com.elmenus.elmenustask.tagsDetails.TagsDetailsActivity.Companion.DETAILS_ITEM
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.loading_transparent.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()
    private lateinit var tagsAdapter: TagsAdapter
    private lateinit var itemsOfTagAdapter: ItemsOfTagsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.fetchTags()
        initAdapter()
        initItemsAdapter()
        viewModel.errorLiveData.observe(this, Observer {
            it?.let {
                rootView.showSnakeBar(it)
            }
        })

        viewModel.showLoadingLiveData.observe(this, Observer {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        })
    }

    private fun onTagsClicked(tagName: String) {
        viewModel.getItemByTagName(tagName)
    }


    private fun initAdapter() {
        tagsAdapter = TagsAdapter { onTagsClicked(it) }
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvTags.layoutManager = layoutManager
        rvTags.adapter = tagsAdapter

        viewModel.tags.observe(this, Observer<PagedList<TagObject>> {
            tagsAdapter.submitList(it)
        })
        viewModel.networkErrors.observe(this, Observer {
            it?.let {
                rootView.showSnakeBar(it)
            }

        })
    }


    private fun initItemsAdapter() {
        itemsOfTagAdapter = ItemsOfTagsAdapter(emptyList()) { onItemClicked(it) }
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvItemOfTags.layoutManager = layoutManager
        rvItemOfTags.adapter = itemsOfTagAdapter
        viewModel.itemOfTagLiveData.observe(this, Observer<List<ItemOfTags>> {
            itemsOfTagAdapter.items = it
            itemsOfTagAdapter.notifyDataSetChanged()
        })

    }

    private fun onItemClicked(itemOfTags: ItemOfTags) {
        val bundle = Bundle()
        bundle.putParcelable(DETAILS_ITEM, itemOfTags)
        val intent = Intent(this, TagsDetailsActivity::class.java)
        intent.putExtras(bundle)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        this.startActivity(intent)
    }

    private fun showLoading() {
        transparentLoadingView.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        transparentLoadingView.visibility = View.GONE
    }

}
