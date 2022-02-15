package com.example.triviapp.presentation.questionScreen

import com.example.triviapp.data.models.Question

data class QuestionState(
    val isLoading: Boolean = false,
    val question: Question? = null,
    val error: String = ""
)