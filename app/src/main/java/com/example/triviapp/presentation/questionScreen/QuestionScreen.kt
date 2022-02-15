package com.example.triviapp.presentation.questionScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.triviapp.data.models.Question
import com.example.triviapp.presentation.menu.CategoryItem

@Composable
fun QuestionScreen(
    questionViewModel: QuestionViewModel = hiltViewModel(),
    navController: NavController
){
    val question = questionViewModel.state.value
    Column(modifier = Modifier
        .background(MaterialTheme.colors.background)
    ){
            question.question?.let {
                QuestionItem(
                    it,
                    navController,
                    (0 until question.question.results[0].incorrect_answers.size).random()
                )
            }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuestionItem(
    question : Question,
    navController: NavController,
    correctPosition : Int
){

    val pressed = remember { mutableStateOf(false)}

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 15.dp, vertical = 25.dp)
    ) {
        Card(elevation = 8.dp) {
            Text(
                text=question.results[0].question,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp)
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(correctPosition) {
                ResponseItem(
                    name = question.results[0].incorrect_answers[it],
                    correct = false,
                    pressed = pressed.value,
                    onPressChange = { press -> pressed.value = press })
            }
            item {
                ResponseItem(
                    name = question.results[0].correct_answer,
                    correct = true,
                    pressed = pressed.value,
                    onPressChange = { press -> pressed.value = press })
            }
            items(question.results[0].incorrect_answers.size-correctPosition) {
                ResponseItem(
                    name = question.results[0].incorrect_answers[it+correctPosition],
                    correct = false,
                    pressed = pressed.value,
                    onPressChange = { press -> pressed.value = press })
            }

        }
        if(pressed.value){
            Button(
                onClick = {
                navController.navigate("Menu")
                })
            {
                Text(text = "Next")
            }
        }
    }

}

@Composable
fun ResponseItem(
    name : String,
    correct : Boolean,
    pressed : Boolean,
    onPressChange : ((Boolean)->Unit)
){

    val color = remember { mutableStateOf(Color.White)}
    Card(
        elevation = 8.dp,
        modifier = Modifier
            .padding(15.dp)
            .clickable {
                if (!pressed) {
                    color.value = if (!correct) Color.Red
                    else Color.Green
                    onPressChange(true)
                }
            },
        backgroundColor = if(pressed && correct) Color.Green else color.value
    ) {
        Text(
            name,
            textAlign = TextAlign.Center
        )
    }
}