package com.example.triviapp.presentation.menu

import com.example.triviapp.data.models.Categories

data class CategoryState(
    val isLoading: Boolean = false,
    val category: Categories = Categories(emptyList()),
    val error: String = ""
)