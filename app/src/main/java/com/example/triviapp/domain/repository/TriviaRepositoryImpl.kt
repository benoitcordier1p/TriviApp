package com.example.triviapp.domain.repository

import com.example.triviapp.data.models.Categories
import com.example.triviapp.data.models.Question
import com.example.triviapp.data.remote.TriviaAPI
import javax.inject.Inject

class TriviaRepositoryImpl @Inject constructor(
    private val api : TriviaAPI
) : TriviaRepository{

    override suspend fun getQuestion(diff: String, cat : String): Question = api.getQuestion(difficulty = diff, cat = cat)

    override suspend fun getCategories(): Categories = api.getCategories()
}