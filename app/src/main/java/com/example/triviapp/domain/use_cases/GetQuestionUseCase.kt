package com.example.triviapp.domain.use_cases

import com.example.triviapp.data.models.Question
import com.example.triviapp.domain.repository.TriviaRepository
import com.example.triviapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetQuestionUseCase @Inject constructor(
    private val triviaRepository: TriviaRepository
) {

    operator fun invoke(diff : String, cat : String): Flow<Resource<Question>> = flow {
        try {
            emit(Resource.Loading<Question>())
            emit(Resource.Success<Question>(triviaRepository.getQuestion(diff,cat)))
        } catch (e: HttpException) {
            emit(Resource.Error<Question>(e.localizedMessage ?: "An error occurred"))
        } catch (e: IOException) {
            emit(
                Resource.Error<Question>(
                    e.localizedMessage ?: "Internet error. Check your connection"
                )
            )
        }
    }
}