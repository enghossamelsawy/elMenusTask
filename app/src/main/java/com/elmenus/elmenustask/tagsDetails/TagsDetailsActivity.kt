package com.elmenus.elmenustask.tagsDetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.elmenus.elmenustask.R
import com.elmenus.elmenustask.base.ext.loadImage
import com.elmenus.elmenustask.menustags.data.ItemOfTags

import kotlinx.android.synthetic.main.activity_tags_details.*
import kotlinx.android.synthetic.main.content_tags_details.*

class TagsDetailsActivity : AppCompatActivity() {
    companion object {
        const val DETAILS_ITEM = "details_item"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tags_details)
        initToolbar(toolbar)
        initUi()
    }

    private fun initUi() {
        val itemOfTags = intent.getParcelableExtra<ItemOfTags>(DETAILS_ITEM)
        itemOfTags?.let {
            imgItem.loadImage(itemOfTags.photoUrl)
            supportActionBar!!.title = itemOfTags.name
            tvDetails.text = itemOfTags.description
        }
    }

    private fun initToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
