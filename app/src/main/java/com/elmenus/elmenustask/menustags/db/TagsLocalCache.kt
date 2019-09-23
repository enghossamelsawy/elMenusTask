package com.elmenus.elmenustask.menustags.db

import androidx.paging.DataSource
import com.elmenus.elmenustask.menustags.data.ItemOfTags
import com.elmenus.elmenustask.menustags.data.TagObject
import java.util.concurrent.Executors

class TagsLocalCache(
    private val tagsDao: TagsDao, private val itemOfTagsDao: ItemOfTagsDao
) {
    fun insertTags(tags: List<TagObject>, insertFinished: () -> Unit) {
        Executors.newSingleThreadExecutor().execute {
            tagsDao.insert(tags)
            insertFinished()
        }
    }

    suspend fun insertItemOfTags(itemOfTags: List<ItemOfTags>) {
        itemOfTagsDao.insert(itemOfTags)
    }

    suspend fun getTagItemByTagName(name: String): List<ItemOfTags> {
        return itemOfTagsDao.tagsByName(name)
    }

    fun getAllTagData(): DataSource.Factory<Int, TagObject> {
        return tagsDao.tagsByName()
    }
}
