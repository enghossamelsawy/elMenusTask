package com.elmenus.elmenustask.base.data.local.di

import android.content.Context


import com.elmenus.elmenustask.base.data.local.AppPreference
import com.elmenus.elmenustask.base.data.local.LocalConstants.PREFERENCE_NAME


import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val localModule = module {
    single { androidApplication().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE) }
    single { AppPreference(get()) }

}