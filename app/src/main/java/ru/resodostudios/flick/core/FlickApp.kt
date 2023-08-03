package ru.resodostudios.flick.core

import android.app.Application
import com.yandex.mobile.ads.common.MobileAds
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FlickApp : Application() {

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this) { }
    }
}