package com.example.restflickrcompose.data.network.flickr

import com.example.restflickrcompose.data.network.flickr.model.FlickrResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrClient {

    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1&text=dogs&api_key=40bd373bb6a19a078023b06af055d03c")
    suspend fun getPhotos(
    ): Response<FlickrResponse>

    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1&text=dogs&api_key=40bd373bb6a19a078023b06af055d03c")
    suspend fun getMorePhotos(
        @Query("page") page: Int
    ): Response<FlickrResponse>
}