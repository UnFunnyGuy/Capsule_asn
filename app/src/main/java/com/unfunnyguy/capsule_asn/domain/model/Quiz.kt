package com.unfunnyguy.capsule_asn.domain.model

data class Quiz(
    val question: String,
    val options: List<QuizOption>,
    val correctAnswer: QuizOption
)

data class QuizOption(
    val option: String,
    val isAnswer: Boolean = false
)

fun dummyQuestions(): List<Quiz> {

    return listOf(
        Quiz(
            question = "Which of the following is a monosaccharide?",
            options = listOf(
                QuizOption("Glucose", true),
                QuizOption("Sucrose"),
                QuizOption("Fructose"),
                QuizOption("Lactose")
            ),
            correctAnswer = QuizOption("Glucose", true)
        ),
        Quiz(
            question = "What is the process by which DNA makes an RNA copy?",
            options = listOf(
                QuizOption("Replication"),
                QuizOption("Transcription", true),
                QuizOption("Translation"),
                QuizOption("Reverse transcription")
            ),
            correctAnswer = QuizOption("Transcription", true)
        ),
        Quiz(
            question = "Which gas is responsible for the greenhouse effect on Earth?",
            options = listOf(
                QuizOption("Oxygen"),
                QuizOption("Carbon dioxide", true),
                QuizOption("Methane"),
                QuizOption("Nitrogen")
            ),
            correctAnswer = QuizOption("Carbon dioxide", true)
        )
    )
}
