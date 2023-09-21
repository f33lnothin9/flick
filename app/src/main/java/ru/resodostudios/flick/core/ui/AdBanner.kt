package ru.resodostudios.flick.core.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mobile.ads.banner.BannerAdSize
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest

@Composable
fun AdBanner(id: Int) {

    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            BannerAdView(context).apply {
                setAdUnitId(context.getString(id))
                setAdSize(BannerAdSize.stickySize(context, 350))

                val adRequest = AdRequest.Builder().build()
                loadAd(adRequest)
            }
        }
    )
}