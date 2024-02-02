package com.example.restflickrcompose.domain.useCases.FlickrApi

import android.util.Log
import com.example.restflickrcompose.domain.model.FlickrDomain
import com.example.restflickrcompose.domain.repository.Repository
import javax.inject.Inject

class GetMorePhotos @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(page: Int): FlickrDomain? {
        val photos = repository.getMorePhotos(page)
        if (photos.photos.photo.isNotEmpty()) {
            Log.d("GetMorePhotos", "MAS")
            return photos
        }
        return null
    }
}