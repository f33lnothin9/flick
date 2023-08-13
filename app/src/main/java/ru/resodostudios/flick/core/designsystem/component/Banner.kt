package ru.resodostudios.flick.core.designsystem.component

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mobile.ads.banner.AdSize
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Composable
fun Banner(id: Int) {

    AndroidView(
        modifier = Modifier.navigationBarsPadding(),
        factory = { context ->
            BannerAdView(context).apply {
                setAdUnitId(context.getString(id))
                setAdSize(AdSize.stickySize(context, 300))

                val executor = Executors.newScheduledThreadPool(1)
                val delaySeconds = 10L

                val runnable = Runnable {
                    val adRequest = AdRequest.Builder().build()
                    loadAd(adRequest)
                }

                executor.scheduleAtFixedRate(runnable, 0, delaySeconds, TimeUnit.SECONDS)
            }
        }
    )
}