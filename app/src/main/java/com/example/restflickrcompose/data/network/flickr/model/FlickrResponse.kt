package com.example.restflickrcompose.data.network.flickr.model

import com.example.restflickrcompose.domain.model.FlickrDomain

data class FlickrResponse(
    val photos: Photos,
    val stat: String
)

fun FlickrDomain.toDatabase() = FlickrResponse(
    photos = photos.toDatabase(),
    stat = stat
)