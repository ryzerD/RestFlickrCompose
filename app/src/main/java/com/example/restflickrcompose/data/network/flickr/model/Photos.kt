package com.example.restflickrcompose.data.network.flickr.model

import com.example.restflickrcompose.domain.model.PhotosDomain

data class Photos(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val photo: List<Photo>,
    val total: Int
)


fun PhotosDomain.toDatabase() = Photos(
    page = page,
    pages = pages,
    perpage = perpage,
    photo = photo.map { it.toDatabase() },
    total = total
)