package com.elmenus.elmenustask.menustags.di

import com.elmenus.elmenustask.menustags.data.ITagRepository
import com.elmenus.elmenustask.menustags.data.TagRepository
import com.elmenus.elmenustask.menustags.data.TagsService
import com.elmenus.elmenustask.menustags.db.TagsDatabase
import com.elmenus.elmenustask.menustags.db.TagsLocalCache
import com.elmenus.elmenustask.menustags.presentation.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val mainModule = module {

    factory { TagsDatabase.getInstance(androidContext()).reposDao() }
    factory { TagsDatabase.getInstance(androidContext()).itemOfTagsDao() }
    factory { TagsLocalCache(get(), get()) }
    factory { get<Retrofit>().create(TagsService::class.java) }
    factory<ITagRepository> { TagRepository(get(), get()) }
    viewModel { MainViewModel(get()) }

}