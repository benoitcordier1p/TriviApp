package com.example.triviapp.presentation.menu

import android.os.CountDownTimer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.triviapp.data.models.Categories
import com.example.triviapp.data.models.TriviaCategory
import com.example.triviapp.domain.use_cases.GetCategoriesUseCase
import com.example.triviapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : ViewModel() {

    //State. Updated when new questions are loaded.
    private val _state = mutableStateOf(CategoryState())
    var state: State<CategoryState> = _state

    var listOfIds = mutableListOf<Int>()
    var randomSelection = mutableStateOf(1)

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            getCategoriesUseCase().collect {
                _state.value = when (it) {
                    is Resource.Success -> CategoryState(category = it.data ?: Categories(emptyList()))
                    is Resource.Loading -> CategoryState(true, Categories(emptyList()),"")
                    is Resource.Error -> CategoryState(false,Categories(emptyList()),it.message!!)
                }
            }
            getListId()
        }
    }

    private fun getListId(){
        _state.value.category.trivia_categories.forEach {
            listOfIds.add(it.id)
        }
    }

    fun randomSelection(){
        var index=0
        val time = (3000..10000).random().toLong()
        val timer = object: CountDownTimer(time, 100) {
            override fun onTick(p0: Long) {
                if(index<listOfIds.size-1) {
                    randomSelection.value=listOfIds[index]
                }
                else {
                    index=0
                    listOfIds[index]
                }
                index += 1
            }

            override fun onFinish() {

            }
        }
        timer.start()
    }
}