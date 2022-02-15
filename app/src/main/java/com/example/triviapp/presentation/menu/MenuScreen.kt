package com.example.triviapp.presentation.menu

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MenuScreen(
    navigationController: NavController,
    menuViewModel: MenuViewModel = hiltViewModel()
) {
    val state = remember { menuViewModel.state }
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colors.background)
    ) {
        Text(
            text = "TriviApp",
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(30.dp))
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
        ) {
            items(state.value.category.trivia_categories) {
                CategoryItem(name = it.name, id = it.id, navController = navigationController)
            }
        }
        Column {
            if (state.value.isLoading) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }
            if (state.value.error.isNotEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = state.value.error,
                        color = MaterialTheme.colors.error
                    )
                }
            }
        }
    }

}

@Composable
fun CategoryItem(name: String, id: Int, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .size(40.dp)
            .clickable {
                navController.navigate("Question/$id/easy")
            }
            .size(40.dp)
            .background(MaterialTheme.colors.surface),
        elevation = 8.dp,

    ) {
        Text(
            name,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxSize()
        )
    }
}