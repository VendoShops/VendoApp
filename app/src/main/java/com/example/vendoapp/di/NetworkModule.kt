package com.example.vendoapp.di

import com.example.vendoapp.data.remote.api.ApiService
import com.example.vendoapp.domain.repository.AuthRepositoryImpl
import com.example.vendoapp.domain.repository.AuthRepository
import com.example.vendoapp.domain.usecase.LoginUseCase
import com.example.vendoapp.domain.usecase.RegisterUseCase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "http://138.68.111.115:8080/"

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient,  gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideAuthRepository(api: ApiService): AuthRepository =
        AuthRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideRegisterUseCase(repository: AuthRepository) = RegisterUseCase(repository)

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: AuthRepository) = LoginUseCase(repository)
}