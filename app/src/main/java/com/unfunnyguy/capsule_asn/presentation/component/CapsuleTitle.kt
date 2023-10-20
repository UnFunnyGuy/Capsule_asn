package com.unfunnyguy.capsule_asn.presentation.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.unfunnyguy.capsule_asn.presentation.Capsule
import com.unfunnyguy.capsule_asn.util.cap
import java.util.Locale

@Composable
fun CapsuleTitle(
    currentPageIndex: Int,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Capsule.content.forEach {
            CapsuleTitleItem(
                title = it.name.cap(),
                isFilled = it.isFilled(currentPageIndex),
                modifier = Modifier.width(75.dp)
            )
        }


    }

}

@Composable
private fun CapsuleTitleItem(
    title: String,
    isFilled: Boolean,
    modifier: Modifier = Modifier
) {

    val pillFill by animateFloatAsState(
        targetValue = if (!isFilled) 0f else 1f,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
        label = "pillFill"
    )
    val titleColor by animateColorAsState(
        targetValue = if (!isFilled) MaterialTheme.colorScheme.onBackground.copy(0.65f) else MaterialTheme.colorScheme.onBackground,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
        label = "titleColor"
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                color = titleColor
            )
        )

        LinearProgressIndicator(
            progress = pillFill,
            modifier = Modifier
                .requiredHeight(4.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
        )


    }
}