package com.unfunnyguy.capsule_asn.presentation

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.google.common.collect.ImmutableList
import com.unfunnyguy.capsule_asn.domain.model.Quiz
import com.unfunnyguy.capsule_asn.domain.model.QuizOption
import com.unfunnyguy.capsule_asn.domain.model.dummyQuestions
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds


class MainViewModel() : ViewModel() {

    var player: Player? = null
    private var _timer = MutableStateFlow(3.5.minutes)
    val timer: StateFlow<Duration> = _timer.asStateFlow()

    private var _uiEvent = MutableStateFlow<MainUIEvent?>(null)
    val uiEvent: StateFlow<MainUIEvent?> = _uiEvent.asStateFlow()

    private val _uiState = MutableStateFlow(MainUiState(
        timer = 0,
        isTimeConsumed = false,
        questionIndex = 0,
        questions = dummyQuestions(),
        currentQuestion = dummyQuestions().first(),
        userQuestionResponse = emptyList()
    ))
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private val currentState: MainUiState
        get() = uiState.value

    private var timerJob: Job? = null

    fun initPlayer(player: ExoPlayer) {
        this.player = player
        playVideo("asset:///demo1.mp4".toUri())
        this.player?.prepare()
    }

    init {
        startTimer()
    }


    fun onAction(action: MainUIAction){
        when(action){
            is MainUIAction.NextQuestion -> {
                if (action.index <= currentState.questions.size - 1){
                    _uiState.update { it.copy( questionIndex = action.index ) }
                }
            }

            is MainUIAction.AnswerQuestion -> {
                val userResponse = currentState.userQuestionResponse.toMutableList()
                if (userResponse.isEmpty() || userResponse.size <= action.index) userResponse.add(action.option)
                else userResponse[action.index] = action.option
                _uiState.update { it.copy( userQuestionResponse = userResponse ) }
            }
            MainUIAction.OnNextContent -> emitEvent(MainUIEvent.NextContent)
            is MainUIAction.PreviousQuestion -> {
                if (action.index >= 0){
                    _uiState.update { it.copy( questionIndex = action.index ) }
                }
            }
        }
    }


    private fun startTimer(){
       timerJob =  viewModelScope.launch {
           while (timer.value > 0.seconds) {
               _timer.emit(timer.value.minus(1.seconds))
               delay(1.seconds)
           }
           emitEvent(MainUIEvent.TimerComplete)
        }
    }


    private fun playVideo(uri: Uri) {
        player?.setMediaItem(
            MediaItem.fromUri(uri)
        )
    }

    override fun onCleared() {
        super.onCleared()
        player?.release()
        timerJob?.cancel()
    }

    private fun emitEvent(event: MainUIEvent){
        viewModelScope.launch {
            _uiEvent.emit(event)
            delay(30)
            _uiEvent.emit(null)
        }

    }
}


sealed interface MainUIAction {
    object OnNextContent: MainUIAction
    data class NextQuestion(val index: Int) : MainUIAction
    data class PreviousQuestion(val index: Int) : MainUIAction
    data class AnswerQuestion(val index: Int, val option: QuizOption) : MainUIAction
}

sealed interface MainUIEvent {
    object TimerComplete: MainUIEvent
    object NextContent: MainUIEvent
}

data class MainUiState (
    val timer: Int,
    val isTimeConsumed: Boolean,
    val questions: List<Quiz>,
    val currentQuestion: Quiz,
    val questionIndex: Int,
    val userQuestionResponse: List<QuizOption>
)
