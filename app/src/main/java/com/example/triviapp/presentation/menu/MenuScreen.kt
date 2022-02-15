package com.example.triviapp.presentation.menu

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.triviapp.presentation.ui.theme.TriviappTheme
import java.util.*

@Composable
fun MenuScreen(
    navigationController: NavController,
    menuViewModel: MenuViewModel = hiltViewModel()
) {
    val state = remember { menuViewModel.state }
    val selected = remember { mutableStateOf(false)}
    val category = remember { mutableStateOf(0)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {

        Text(
            text = "TriviApp",
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        if (!selected.value) {
            CategoryList(
                state = state,
                onSelectCat = {
                    category.value = it
                    selected.value = true
                }
            )
        } else {
            DifficultyList(
                navigationController = navigationController,
                cat = category.value.toString()
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryList(
    state : State<CategoryState>,
    onSelectCat: (Int) -> Unit
){
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
    ) {

        items(state.value.category.trivia_categories) {
            CategoryItem(
                name = it.name,
                id = it.id,
                onSelectCat = { cat -> onSelectCat(cat) }
            )
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

@Composable
fun CategoryItem(name: String, id: Int,onSelectCat : ((Int)->Unit)) {
    Card(
        modifier = Modifier
            .clickable { onSelectCat(id) }
            .size(60.dp)
            .background(MaterialTheme.colors.surface)
            .border(
                width = 4.dp,
                color = MaterialTheme.colors.secondary,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(12.dp),
        elevation = 8.dp,
    ) {
        Text(
            text = name,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp)
        )
    }
}

@Composable
fun DifficultyList(
    navigationController: NavController,
    cat:String
){
    val difficulties = listOf("easy","medium","hard")

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(difficulties){
            Card(
                modifier = Modifier
                    .clickable { navigationController.navigate("Question/$cat/$it") }
                    .width(100.dp)
                    .height(40.dp)
                    .background(MaterialTheme.colors.surface)
                    .border(
                        width = 4.dp,
                        color = MaterialTheme.colors.secondary,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(top = 10.dp),
                elevation = 8.dp,
            ) {
                Text(
                    text = it.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Preview
@Composable
fun Preview(){
    TriviappTheme() {
        DifficultyList(
            navigationController = rememberNavController(),
            cat = "No")
    }
}