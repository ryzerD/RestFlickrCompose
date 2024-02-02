package com.example.restflickrcompose.domain.useCases.FlickrApi

import com.example.restflickrcompose.domain.model.FlickrDomain
import com.example.restflickrcompose.domain.repository.Repository
import javax.inject.Inject

class GetPhotosUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(): FlickrDomain? {
        val photos = repository.getPhotos()
        if (photos.photos.photo.isNotEmpty()) {
            return photos
        }
        return null
    }

}