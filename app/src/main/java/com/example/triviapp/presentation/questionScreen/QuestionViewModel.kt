package com.example.triviapp.presentation.questionScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.triviapp.domain.use_cases.GetQuestionUseCase
import com.example.triviapp.util.Constants
import com.example.triviapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val getQuestionUseCase: GetQuestionUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    //State. Updated when new questions are loaded.
    private val _state = mutableStateOf(QuestionState())
    var state: State<QuestionState> = _state

    init {
        getQuestion(
            savedStateHandle.get<String>(Constants.DIFFICULTY)!!,
            savedStateHandle.get<String>(Constants.THEME)!!
        )
    }

    private fun getQuestion(diff :String, cat : String) {
        viewModelScope.launch {
            getQuestionUseCase(diff,cat).collect {
                _state.value = when (it) {
                    is Resource.Success -> {
                        println(it.data)
                        QuestionState(false,it.data,"")
                    }
                    is Resource.Loading -> QuestionState(true,null,"")
                    is Resource.Error -> QuestionState(false,null,it.message!!)
                }
            }
        }
    }

    fun decodedQuestion(question : String) : String {
        return question
            .replace("&#039;","'")
            .replace("&#038;","&")
            .replace("&#040;","(")
            .replace("&#041;",")")
            .replace("&quot;","\"")
    }
}