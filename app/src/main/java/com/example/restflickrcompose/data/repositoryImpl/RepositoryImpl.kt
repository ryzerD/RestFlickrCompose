package com.example.restflickrcompose.data.repositoryImpl

import com.example.restflickrcompose.data.network.flickr.FlickrService
import com.example.restflickrcompose.domain.model.FlickrDomain
import com.example.restflickrcompose.domain.model.toDomain
import com.example.restflickrcompose.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val service: FlickrService
) : Repository {
    override suspend fun getAllPhotos(): FlickrDomain {
        val response = service.getPhotos()
        if (response.isSuccessful) {
            val flickrResponse = response.body()
            if (flickrResponse != null) {
                return flickrResponse.toDomain()
            }
        }
        throw Exception("Failed to fetch photos")
    }
}