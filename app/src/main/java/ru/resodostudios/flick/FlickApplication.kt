package ru.resodostudios.flick

import android.app.Application
import com.yandex.mobile.ads.common.MobileAds
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FlickApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this) { }
    }
}