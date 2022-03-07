package com.example.triviapp.presentation.menu

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.triviapp.data.models.Categories
import com.example.triviapp.domain.use_cases.GetCategoriesUseCase
import com.example.triviapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
        viewModelScope.launch {
            var index=0
            val time = (24..48).random()
            var i=0
            var delay=5
            while(i<time){
                if(index<listOfIds.size-1) {
                    randomSelection.value=listOfIds[index]
                }
                else {
                    index=0
                    listOfIds[index]
                }
                index += 1
                i+=1
                delay(delay.toLong())
                delay+=10
            }
        }
    }
}