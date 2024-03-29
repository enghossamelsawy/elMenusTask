package com.elmenus.elmenustask.menustags.data

import retrofit2.http.GET
import retrofit2.http.Path

interface TagsService {

    @GET("tags/{page}")
    suspend fun getTagsByPage(@Path("page") pageNumber: Int): TagResponse

    @GET("items/{tagName}")
    suspend fun getAvailableItems(@Path("tagName") tagName: String): ItemResponse
}