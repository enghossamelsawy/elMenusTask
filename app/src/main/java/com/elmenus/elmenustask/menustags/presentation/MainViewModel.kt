package com.elmenus.elmenustask.menustags.presentation

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.elmenus.elmenustask.menustags.data.ITagRepository
import com.elmenus.elmenustask.menustags.data.ItemOfTags
import com.elmenus.elmenustask.menustags.data.TagObject
import com.elmenus.elmenustask.menustags.data.TagsResult
import kotlinx.coroutines.launch

class MainViewModel(private val tagRepository: ITagRepository) : ViewModel() {

    val filterLiveData by lazy<LiveData<TagsResult>> {
        filterMutableLiveData
    }
    private val filterMutableLiveData by lazy {
        MutableLiveData<TagsResult>()
    }

    val tags: LiveData<PagedList<TagObject>> =
        Transformations.switchMap(filterLiveData) { it.data }

    val networkErrors: LiveData<String> = Transformations.switchMap(filterLiveData) { it ->
        it.networkErrors
    }

    val itemOfTagLiveData by lazy<LiveData<List<ItemOfTags>>> {
        itemOfTagMutableLiveData
    }
    private val itemOfTagMutableLiveData by lazy {
        MutableLiveData<List<ItemOfTags>>()
    }

    val errorLiveData by lazy<LiveData<String>> {
        errorMutableLiveData
    }
    private val errorMutableLiveData by lazy {
        MutableLiveData<String>()
    }

    val showLoadingLiveData by lazy<LiveData<Boolean>> {
        showLoadingMutableLiveData
    }
    private val showLoadingMutableLiveData by lazy {
        MutableLiveData<Boolean>()
    }


    fun fetchTags(pageNumber: Int = 1) {
        showLoadingMutableLiveData.value = true
        viewModelScope.launch {
            runCatching {
                tagRepository.getTagByNumber(pageNumber)
            }.onSuccess {
                filterMutableLiveData.value = it
                showLoadingMutableLiveData.value = false
            }.onFailure {
                errorMutableLiveData.value = it.message
                showLoadingMutableLiveData.value = false
            }
        }
    }

    fun getItemByTagName(tagName: String) {
        showLoadingMutableLiveData.value = true
        viewModelScope.launch {
            kotlin.runCatching {
                tagRepository.getAvailableItems(tagName)
            }.onSuccess {
                itemOfTagMutableLiveData.value = it.data
                showLoadingMutableLiveData.value = false

            }.onFailure {
                errorMutableLiveData.value = it.message
                showLoadingMutableLiveData.value = false
            }
        }
    }

}