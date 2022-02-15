package com.example.triviapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.triviapp.presentation.menu.MenuScreen
import com.example.triviapp.presentation.questionScreen.QuestionScreen
import com.example.triviapp.presentation.ui.theme.TriviappTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TriviappTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "Menu"){

                    composable(route = "Menu"){
                        MenuScreen(navigationController = navController)
                    }

                    composable(route = "Question/{theme}/{difficulty}"){
                        QuestionScreen(navController = navController)
                    }
                }
            }
        }
    }
}