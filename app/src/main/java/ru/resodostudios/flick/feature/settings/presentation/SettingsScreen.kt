package ru.resodostudios.flick.feature.settings.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import ru.resodostudios.flick.R

@ExperimentalMaterial3Api
@Composable
fun SettingsScreen(onBack: () -> Unit) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val lottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_dev))
    val progress by animateLottieCompositionAsState(composition = lottieComposition, iterations = LottieConstants.IterateForever)
    val context = LocalContext.current

    Box(
        contentAlignment = Alignment.BottomCenter
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                LargeTopAppBar(
                    title = { Text(text = "Settings") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Outlined.ArrowBack, contentDescription = null)
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            }
        ) { innerPadding ->

            LazyColumn(
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        LottieAnimation(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(256.dp),
                            composition = lottieComposition,
                            contentScale = ContentScale.Fit,
                            progress = { progress }
                        )
                    }
                }
                item {
                    Text(
                        text = "About",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                item {
                    val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                    ListItem(
                        headlineContent = { Text(text = "Version") },
                        supportingContent = { Text(text = packageInfo.versionName) }
                    )
                }
            }
        }
    }
}