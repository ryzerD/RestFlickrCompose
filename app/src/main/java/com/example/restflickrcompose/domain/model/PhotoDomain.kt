package com.example.restflickrcompose.domain.model

import com.example.restflickrcompose.data.network.flickr.model.Photo

data class PhotoDomain(
    val farm: Int,
    val id: String,
    val isfamily: Int,
    val isfriend: Int,
    val ispublic: Int,
    val owner: String,
    val secret: String,
    val server: String,
    val title: String
)

fun Photo.toDomainCategories() = PhotoDomain(
    farm = farm,
    id = id,
    isfamily = isfamily,
    isfriend = isfriend,
    ispublic = ispublic,
    owner = owner,
    secret = secret,
    server = server,
    title = title
)