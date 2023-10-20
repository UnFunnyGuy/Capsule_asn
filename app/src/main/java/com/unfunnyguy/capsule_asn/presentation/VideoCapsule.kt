package com.unfunnyguy.capsule_asn.presentation

import android.provider.MediaStore.Video
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.Player
import androidx.media3.ui.PlayerView
import com.unfunnyguy.capsule_asn.R

@Composable
fun VideoCapsule(
    exoPlayer: Player?,
    onNext: () -> Unit,
    modifier: Modifier = Modifier,
){

    Column(
        modifier = modifier
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){

        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                }
            },
            update = {
                //TODO
            },
            modifier = Modifier
                .padding(top = 36.dp)
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
        )

        Spacer(Modifier.weight(1f))

        Button(
            modifier = Modifier.padding(8.dp),
            onClick = onNext,
            shape = RoundedCornerShape(12.dp)
        ){
           Column(
               horizontalAlignment = Alignment.Start,
               verticalArrangement = Arrangement.Center
           ) {
               Text(
                   text =  stringResource(id = R.string.next_to_notes_tile),
                   style = MaterialTheme.typography.titleMedium.copy(
                       fontWeight = FontWeight.Black,
                       fontSize = MaterialTheme.typography.titleMedium.fontSize.times(1.3f)
                   )
               )
               Text(
                   text =  stringResource(id = R.string.next_to_notes_desc),
                   style = MaterialTheme.typography.bodySmall.copy(
                       fontWeight = FontWeight.Bold,
                       fontSize = MaterialTheme.typography.bodySmall.fontSize.times(1.3f)
                   )
               )
           }
            Spacer(Modifier.weight(1f))
            Icon(
               imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = null
            )

        }

    }

}