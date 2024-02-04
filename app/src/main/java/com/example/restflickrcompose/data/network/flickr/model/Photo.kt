package com.example.restflickrcompose.data.network.flickr.model

import com.example.restflickrcompose.domain.model.PhotoDomain

data class Photo(
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

fun PhotoDomain.toDatabase() = Photo(
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