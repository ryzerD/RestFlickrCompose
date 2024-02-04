package com.example.restflickrcompose.domain.model

import com.example.restflickrcompose.data.database.model.PhotoEntity

data class PhotoObtain(
    val id: String,
    val url: String,
    val title: String,
    val page: Int
)

fun PhotoEntity.toDomain() = PhotoObtain(
    id = id,
    url = url,
    title = title,
    page = page
)