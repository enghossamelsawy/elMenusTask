package com.elmenus.elmenustask.base.resources.di

import com.elmenus.elmenustask.base.resources.AppResources
import com.elmenus.elmenustask.base.resources.repository.ResourcesRepository
import org.koin.dsl.module

val resourcesModule = module {
    single { AppResources(get()) }
    single { ResourcesRepository(get()) }
}