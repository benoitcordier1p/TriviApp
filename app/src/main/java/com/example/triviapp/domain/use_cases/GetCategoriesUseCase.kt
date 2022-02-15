package com.example.triviapp.domain.use_cases

import com.example.triviapp.data.models.Categories
import com.example.triviapp.data.models.Question
import com.example.triviapp.domain.repository.TriviaRepository
import com.example.triviapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val triviaRepository: TriviaRepository
) {

    operator fun invoke(): Flow<Resource<Categories>> = flow {
        try {
            emit(Resource.Loading<Categories>())
            emit(Resource.Success<Categories>(triviaRepository.getCategories()))
        } catch (e: HttpException) {
            emit(Resource.Error<Categories>(e.localizedMessage ?: "An error occurred"))
        } catch (e: IOException) {
            emit(
                Resource.Error<Categories>(
                    e.localizedMessage ?: "Internet error. Check your connection"
                )
            )
        }
    }

}