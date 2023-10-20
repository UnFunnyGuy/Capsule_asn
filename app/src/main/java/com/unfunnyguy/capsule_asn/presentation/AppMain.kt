package com.unfunnyguy.capsule_asn.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unfunnyguy.capsule_asn.R
import com.unfunnyguy.capsule_asn.domain.model.Note
import com.unfunnyguy.capsule_asn.domain.model.Quiz
import com.unfunnyguy.capsule_asn.domain.model.dummyQuestions
import com.unfunnyguy.capsule_asn.presentation.component.CapsuleTitle
import com.unfunnyguy.capsule_asn.util.toFormatString
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppMain(
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    val timer by viewModel.timer.collectAsStateWithLifecycle()
    val uiEvent by viewModel.uiEvent.collectAsStateWithLifecycle()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    val timerColor by animateColorAsState(
        targetValue = if (timer.inWholeMinutes < 2.minutes.inWholeMinutes)
            MaterialTheme.colorScheme.errorContainer
        else MaterialTheme.colorScheme.primary,
        label = "timerColor"
    )

    val isScrollable by remember(pagerState) {
        derivedStateOf {
            !(pagerState.currentPage == Capsule.QUIZ.index && pagerState.currentPageOffsetFraction > 0)
        }
    }

    val answeredAll by remember(state.userQuestionResponse) {
        derivedStateOf {
            state.userQuestionResponse.size == state.questions.size
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ){
            CapsuleTitle(
                currentPageIndex = pagerState.currentPage
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .width(80.dp)
                    .background(
                        color = timerColor.copy(0.20f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = 2.dp,
                        color = timerColor.copy(0.60f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(vertical = 4.dp, horizontal = 6.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = timer.toFormatString(),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = timerColor
                    )
                )
            }
        }


        HorizontalPager(
            pageCount = 4,
            state = pagerState,
            userScrollEnabled = isScrollable
        ) { pageIndex ->

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {

                when (Capsule.content[pageIndex]) {

                    Capsule.VIDEO -> VideoCapsule(
                        modifier = Modifier,
                        exoPlayer = viewModel.player,
                        onNext = { scope.launch { pagerState.animateScrollToPage(pageIndex + 1) } }
                    )

                    Capsule.NOTES -> NotesCapsule(
                        modifier = Modifier,
                        note = Note.dummy(),
                        onNext = { scope.launch { pagerState.animateScrollToPage(pageIndex + 1) } }
                    )

                    Capsule.QUIZ -> QuizCapsule(
                        modifier = Modifier,
                        questionIndex = state.questionIndex,
                        userResponse = state.userQuestionResponse,
                        questions = state.questions,
                        answeredAll = answeredAll,
                        onAction = viewModel::onAction,
                    )
                    Capsule.ANSWERS -> AnswerCapsule(
                        questions = state.questions,
                        userResponse = state.userQuestionResponse
                    )

                }

            }
        }
    }



    LaunchedEffect(uiEvent){
        uiEvent?.let {
            when(it){
                MainUIEvent.TimerComplete -> Toast.makeText(context,"Time's Up",Toast.LENGTH_SHORT).show()
                MainUIEvent.NextContent ->  scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
            }
        }
    }

}


enum class Capsule(val index: Int) {
    VIDEO(0),
    NOTES(1),
    QUIZ(2),
    ANSWERS(3);

    companion object {
        val content = listOf(VIDEO, NOTES, QUIZ, ANSWERS)
    }

    fun isFilled(currentIndex: Int): Boolean = currentIndex >= this.index

}