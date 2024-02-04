package com.example.restflickrcompose.domain.model

import com.example.restflickrcompose.data.network.flickr.model.Photos

data class PhotosDomain(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    var photo: List<PhotoDomain>,
    val total: Int
)

fun Photos.toDomainCategories() = PhotosDomain(
    page = page,
    pages = pages,
    perpage = perpage,
    photo = photo.map { it.toDomainCategories() },
    total = total
)