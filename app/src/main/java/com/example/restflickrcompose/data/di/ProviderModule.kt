package com.example.restflickrcompose.data.di

import android.content.Context
import androidx.room.Room
import com.example.restflickrcompose.data.database.AppDatabase
import com.example.restflickrcompose.data.database.dao.PhotoDao
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
object ProviderModule {

    private val DatabaseName = "AppDatabase"

    /**
     * Proporciona una instancia de la base de datos de Room.
     *
     *      * @param context El contexto de la aplicación.
     *      * @return Una instancia de [IpharmaDatabase].
     */
    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, DatabaseName).build()

    @Singleton
    @Provides
    fun provideParameterDao(db: AppDatabase) = db.photoDao()


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
        dao: PhotoDao
    ): Repository = RepositoryImpl(service, dao)


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