package com.example.vendoapp.di

import com.example.vendoapp.repository.ProductRepository
import com.example.vendoapp.repository.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindProductRepository(
        impl: ProductRepositoryImpl
    ): ProductRepository
}