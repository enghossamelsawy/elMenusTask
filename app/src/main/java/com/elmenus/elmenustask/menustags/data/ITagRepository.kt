package com.elmenus.elmenustask.menustags.data

interface ITagRepository {

    suspend fun getTagByNumber(pageNumber: Int): TagsResult
    suspend fun getAvailableItems(tagName: String): ItemOfTagsResult
}