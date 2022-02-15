package com.example.triviapp.domain.repository

import com.example.triviapp.data.models.Categories
import com.example.triviapp.data.models.Question

interface TriviaRepository {

    suspend fun getQuestion(diff: String, cat : String) : Question

    suspend fun getCategories() : Categories
}