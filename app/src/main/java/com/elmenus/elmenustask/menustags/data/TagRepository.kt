package com.elmenus.elmenustask.menustags.data

import androidx.paging.LivePagedListBuilder
import com.elmenus.elmenustask.menustags.db.TagsLocalCache

class TagRepository(private val service: TagsService, private val cache: TagsLocalCache) :
    ITagRepository {
    override suspend fun getAvailableItems(tagName: String): ItemOfTagsResult {

        val itemOfTagsResult = ItemOfTagsResult()
        runCatching {
            service.getAvailableItems(tagName)
        }.onSuccess { itemResponse ->
            val itemOfTags = itemResponse.itemResponse.map {
                ItemOfTags(
                    it.description,
                    tagName,
                    it.id,
                    it.name,
                    it.photoUrl
                )
            }
            cache.insertItemOfTags(itemOfTags)
            itemResponse.itemResponse = itemOfTags
            itemOfTagsResult.data = itemResponse.itemResponse


        }.onFailure {
            itemOfTagsResult.data = cache.getTagItemByTagName(tagName)
            itemOfTagsResult.networkErrors = it.message
        }
        return itemOfTagsResult
    }

    override suspend fun getTagByNumber(pageNumber: Int): TagsResult {
        return getTagByPageName()
    }

    private fun getTagByPageName(): TagsResult {
        val dataSourceFactory = cache.getAllTagData()
        val boundaryCallback = TagsBoundaryCallback(service, cache)
        val networkErrors = boundaryCallback.networkErrors
        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()
        return TagsResult(data, networkErrors)
    }

    companion object {
        private const val DATABASE_PAGE_SIZE = 20
    }
}