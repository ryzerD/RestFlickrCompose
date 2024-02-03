package com.example.restflickrcompose.data.di

import android.content.Context
import com.example.restflickrcompose.data.network.flickr.FlickrClient
import com.example.restflickrcompose.data.network.flickr.FlickrService
import com.example.restflickrcompose.data.repositoryImpl.RepositoryImpl
import com.example.restflickrcompose.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideFlickrRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.flickr.com/services/rest/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideRepository(
        service: FlickrService,
    ): Repository = RepositoryImpl(service)


    @Singleton
    @Provides
    fun provideFlickrClient(retrofit: Retrofit): FlickrClient {
        return retrofit.create(FlickrClient::class.java)
    }



    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }
}