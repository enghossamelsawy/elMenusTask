package com.elmenus.elmenustask.menustags.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.elmenus.elmenustask.menustags.db.TagsLocalCache
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TagsBoundaryCallback(
    private val service: TagsService,
    private val cache: TagsLocalCache
) : PagedList.BoundaryCallback<TagObject>() {

    private var lastRequestedPage = 1

    private val _networkErrors = MutableLiveData<String>()
    // LiveData of network errors.
    val networkErrors: LiveData<String>
        get() = _networkErrors

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    override fun onZeroItemsLoaded() {
        requestAndSaveData()
    }

    override fun onItemAtEndLoaded(itemAtEnd: TagObject) {
        requestAndSaveData()
    }

    private fun requestAndSaveData() {
        if (isRequestInProgress) return

        isRequestInProgress = true
        GlobalScope.launch {
            kotlin.runCatching {
                service.getTagsByPage(lastRequestedPage)
            }
                .onSuccess { tags ->
                    cache.insertTags(tags.tags) {
                        lastRequestedPage++
                        isRequestInProgress = false

                    }

                }.onFailure {
                    _networkErrors.postValue(it.message)
                    isRequestInProgress = false


                }
        }
    }
}