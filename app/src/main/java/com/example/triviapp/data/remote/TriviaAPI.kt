package com.example.triviapp.data.remote

import com.example.triviapp.data.models.Categories
import com.example.triviapp.data.models.Question
import retrofit2.http.GET
import retrofit2.http.Query

interface TriviaAPI {

    @GET("/api.php?")
    suspend fun getQuestion(
        @Query("amount") amount : String = "1",
        @Query("difficulty") difficulty : String,
        @Query("category") cat :String
    ) : Question

    @GET("/api_category.php")
    suspend fun getCategories() : Categories

}