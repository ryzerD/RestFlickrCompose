package com.example.restflickrcompose.domain.repository

import com.example.restflickrcompose.data.database.model.PhotoEntity
import com.example.restflickrcompose.domain.model.FlickrDomain

interface Repository {
    suspend fun getPhotos(): FlickrDomain?
    suspend fun getMorePhotos(page: Int): FlickrDomain?

    suspend fun insertPhotos(photoObtain: List<PhotoEntity>)

    suspend fun getPhotosFromDb(): List<PhotoEntity>
}