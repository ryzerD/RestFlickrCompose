package com.example.restflickrcompose.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.restflickrcompose.domain.model.PhotoObtain

@Entity(tableName = "photos")
data class PhotoEntity(
    @PrimaryKey
    @ColumnInfo("id") val id: String,
    @ColumnInfo("url") val url: String,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("page") val page: Int
)

fun PhotoObtain.toDatabase() = PhotoEntity(
    id = id,
    url = url,
    title = title,
    page = page
)