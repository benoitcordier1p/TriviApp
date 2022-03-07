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
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    val listOfIds = remember { menuViewModel.listOfIds}
    val randomSelection = remember{menuViewModel.randomSelection}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {

        Text(
            text = "TriviApp",
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.surface
        )

        Spacer(modifier = Modifier.height(30.dp))

        if (!selected.value) {
            CategoryList(
                state = state,
                onSelectCat = {
                    category.value = it
                    selected.value = true
                },
                listCat = listOfIds,
                randomSelection = randomSelection.value
            )
        } else {
            if(category.value!=-1){
                DifficultyList(
                    navigationController = navigationController,
                    cat = category.value.toString()
                )
            } else {
                LaunchedEffect(key1 = Unit, block = {
                    menuViewModel.randomSelection()
                })
                CategoryList(
                    state = state,
                    onSelectCat = {
                        category.value = it
                        selected.value = true
                    },
                    listCat = listOfIds,
                    randomSelection = randomSelection.value
                )
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryList(
    state : State<CategoryState>,
    onSelectCat: (Int) -> Unit,
    listCat : List<Int>,
    randomSelection : Int
){

    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
    ) {
        if(listCat.isNotEmpty()){
            item {
                CategoryItem(
                    name = "Random",
                    id = -1,
                    onSelectCat = { cat -> onSelectCat(cat) },
                    selected = false
                )
            }
            item {
                CompetitionItem(name = "Duel")
            }
            items(state.value.category.trivia_categories) {
                CategoryItem(
                    name = it.name,
                    id = it.id,
                    onSelectCat = { cat -> onSelectCat(cat) },
                    selected = it.id==randomSelection
                )
            }
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
fun CategoryItem(
    name: String,
    id: Int,
    onSelectCat : ((Int)->Unit),
    selected :Boolean) {
    Card(
        elevation = 0.dp,
        modifier = Modifier
            .clickable { onSelectCat(id) }
            .size(90.dp)
            .padding(15.dp),
        backgroundColor =  if(selected) MaterialTheme.colors.secondary
        else MaterialTheme.colors.primary,
    ) {
        Text(
            text = name,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp),
            color = MaterialTheme.colors.surface
        )
    }
}

@Composable
fun CompetitionItem(
    name: String) {
    Card(
        elevation = 8.dp,
        modifier = Modifier
            .clickable {  }
            .size(90.dp)
            .padding(15.dp),
        backgroundColor =  MaterialTheme.colors.primary,
    ) {
        Text(
            text = name,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp),
            color = MaterialTheme.colors.surface
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
                    .width(120.dp)
                    .padding(15.dp),
                elevation = 8.dp,
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Text(
                    text = it.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 5.dp),
                    color = MaterialTheme.colors.surface
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