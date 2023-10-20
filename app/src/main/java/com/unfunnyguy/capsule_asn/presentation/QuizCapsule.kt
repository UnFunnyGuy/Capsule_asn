package com.unfunnyguy.capsule_asn.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.unfunnyguy.capsule_asn.domain.model.Quiz
import com.unfunnyguy.capsule_asn.domain.model.QuizOption

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun QuizCapsule(
    modifier: Modifier = Modifier,
    questions: List<Quiz>,
    userResponse: List<QuizOption>,
    questionIndex: Int,
    answeredAll: Boolean,
    onAction: (MainUIAction) -> Unit,
) {


    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                modifier = Modifier
                    .weight(3f)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(10.dp)
                    ),
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(10.dp),
            ) {

                Text(
                    text = "Question ${questionIndex+1}/${questions.size}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Black
                    )
                )

            }
            Row(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(10.dp)
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = null,
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Filled.Info,
                        contentDescription = null,
                    )
                }

            }

        }

        AnimatedContent(
            targetState = questionIndex,
            transitionSpec = { fadeIn() with fadeOut() },
            label = "questionIndex"
        ){

            Column{
                Box(
                    modifier = Modifier
                        .padding(
                            vertical = 16.dp
                        )
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(0.25f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary.copy(0.45f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(16.dp)
                ) {
                    Text(
                        text = questions[questionIndex].question,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Start
                    )
                }
                Spacer(Modifier.height(16.dp))

                val (selected, setSelected) = remember { mutableStateOf<QuizOption?>(null) }

                LaunchedEffect(questionIndex) {
                    setSelected(userResponse.getOrNull(questionIndex))
                }

                questions[questionIndex].options.forEach { opt ->

                    Box(
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme
                                    .surfaceColorAtElevation(6.dp)
                                    .copy(0.75f),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .border(
                                width = 2.dp,
                                color = if (selected == opt) MaterialTheme.colorScheme.primary.copy(
                                    0.85f
                                )
                                else Color.Transparent,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(16.dp)
                            .clickable {
                                setSelected(opt)
                                onAction(MainUIAction.AnswerQuestion(questionIndex, opt))
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = opt.option,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }


        }

        Spacer(Modifier.weight(1f))

        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            Button(
                modifier = Modifier
                    .weight(3f)
                    .height(intrinsicSize = IntrinsicSize.Max),
                onClick = { onAction(MainUIAction.OnNextContent) },
                shape = RoundedCornerShape(12.dp),
                enabled = answeredAll
            ) {

                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "Check Answers",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        //fontSize = MaterialTheme.typography.headlineMedium.fontSize.times(1.05f)
                    ),
                    textAlign = TextAlign.Center
                )

            }

            Row(
                Modifier
                    .weight(1f)
                    .height(intrinsicSize = IntrinsicSize.Max)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(12.dp),
                    ),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ){

                val isPrevEnabled = remember(questionIndex) {
                    questionIndex > 0
                }
                val isNextEnabled = remember(questionIndex) {
                    questionIndex < questions.size - 1
                }

                IconButton(
                    onClick = { onAction(MainUIAction.PreviousQuestion(questionIndex - 1)) },
                    enabled = isPrevEnabled
                ){
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowLeft,
                        contentDescription = null,
                        tint = if (isPrevEnabled) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimary.copy(0.45f)
                    )
                }

                IconButton(
                    onClick = { onAction(MainUIAction.NextQuestion(questionIndex + 1)) },
                    enabled = isNextEnabled
                ){
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = if (isNextEnabled) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimary.copy(0.45f)
                    )
                }
            }

        }
    }


}