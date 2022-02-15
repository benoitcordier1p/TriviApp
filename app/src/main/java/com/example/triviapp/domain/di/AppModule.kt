package com.example.triviapp.domain.di

import com.example.triviapp.data.remote.TriviaAPI
import com.example.triviapp.domain.repository.TriviaRepository
import com.example.triviapp.domain.repository.TriviaRepositoryImpl
import com.example.triviapp.util.URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTreeApi() : TriviaAPI {
        return Retrofit.Builder()
            .baseUrl(URL.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TriviaAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideTreeRepository(api:TriviaAPI) : TriviaRepository {
        return TriviaRepositoryImpl(api)
    }

}