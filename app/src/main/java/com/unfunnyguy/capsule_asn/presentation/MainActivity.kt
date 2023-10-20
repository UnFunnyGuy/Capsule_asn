package com.unfunnyguy.capsule_asn.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.media3.exoplayer.ExoPlayer
import com.unfunnyguy.capsule_asn.presentation.theme.CapsuleasnTheme

class MainActivity : ComponentActivity() {


    private val viewModel by viewModels<MainViewModel>()

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        SystemBarStyle.auto(android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT)
        super.onCreate(savedInstanceState)
        val player = ExoPlayer.Builder(applicationContext).build()
        viewModel.initPlayer(player)
        setContent {
            CapsuleasnTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {

                    AppMain(
                        viewModel = viewModel
                    )

                }
            }
        }
    }
}


