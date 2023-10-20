package com.unfunnyguy.capsule_asn.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.unfunnyguy.capsule_asn.domain.model.Quiz
import com.unfunnyguy.capsule_asn.domain.model.QuizOption

@Composable
fun AnswerCapsule(
    questions: List<Quiz>,
    userResponse: List<QuizOption>
) {


    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            itemsIndexed(
                items = questions
            ) { index, question ->

                //Q
                Box(
                    modifier = Modifier
                        .padding(
                            vertical = 8.dp
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
                        .padding(8.dp)
                ) {
                    Text(
                        text = question.question,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Start
                    )
                }

                val isCorrect = remember(userResponse) {
                    userResponse.getOrNull(index)?.isAnswer ?: false
                }
                //A
                Box(
                    modifier = Modifier
                        .padding(vertical = 6.dp)
                        .fillMaxWidth()
                        .background(
                            color = if (isCorrect) Color.Green.copy(0.65f) else MaterialTheme.colorScheme
                                .errorContainer
                                .copy(0.75f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = if (isCorrect) Color.Green.copy(0.65f) else Color.Transparent,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = question.correctAnswer.option,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        textAlign = TextAlign.Start
                    )
                }


            }
        }
    }


}