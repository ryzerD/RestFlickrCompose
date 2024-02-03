package com.example.restflickrcompose.domain.repository

import com.example.restflickrcompose.domain.model.FlickrDomain

interface Repository {
    suspend fun getPhotos(): FlickrDomain?
    suspend fun getMorePhotos(page: Int): FlickrDomain?
}