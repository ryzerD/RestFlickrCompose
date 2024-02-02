package com.example.restflickrcompose.data.network

import com.example.restflickrcompose.data.network.flickr.FlickrClient
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitHelper @Inject constructor(
    private val flickrRetrofit: Retrofit
) {

    fun getFlickrService(): FlickrClient {
        return flickrRetrofit.create(FlickrClient::class.java)
    }

}