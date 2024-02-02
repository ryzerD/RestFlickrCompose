package com.example.restflickrcompose.domain.model

import com.example.restflickrcompose.data.network.flickr.model.FlickrResponse

data class FlickrDomain(
    val photos: PhotosDomain,
    val stat: String
)

fun FlickrResponse.toDomain() = FlickrDomain(
    photos = photos.toDomainCategories(),
    stat = stat
)