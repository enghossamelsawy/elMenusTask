package com.elmenus.elmenustask

import android.app.Application
import com.elmenus.elmenustask.base.javakoin.start

class elMenusApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        start(this)
    }
}