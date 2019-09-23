package com.elmenus.elmenustask.base.javakoin

import android.app.Application


import com.elmenus.elmenustask.base.data.local.di.localModule
import com.elmenus.elmenustask.base.data.remote.di.remoteModule
import com.elmenus.elmenustask.base.resources.di.resourcesModule
import com.elmenus.elmenustask.menustags.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


fun start(application: Application) {
    startKoin {
        androidContext(application)
        printLogger()
        modules(
            listOf(
                localModule
                , remoteModule
                , resourcesModule
                , mainModule
            )
        )
    }
}