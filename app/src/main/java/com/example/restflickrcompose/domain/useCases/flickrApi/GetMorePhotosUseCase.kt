package com.example.restflickrcompose.domain.useCases.flickrApi

import com.example.restflickrcompose.data.database.model.toDatabase
import com.example.restflickrcompose.domain.model.PhotoDomain
import com.example.restflickrcompose.domain.model.PhotoObtain
import com.example.restflickrcompose.domain.repository.Repository
import javax.inject.Inject

class GetMorePhotosUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(page: Int): List<PhotoObtain> {
        val photos = repository.getMorePhotos(page)
            ?: throw Exception("Failed to get more photos")

        val obtain = photos.photos.photo.map { photo ->
            val url = createPhotoUrl(photo)
            val photoObtain = PhotoObtain(
                id = photo.id,
                url = url,
                title = photo.title,
                page = photos.photos.page
            )

            val photoEntity = photoObtain.toDatabase()
            repository.insertPhotos(listOf(photoEntity))

            photoObtain
        }

        return obtain
    }

    private fun createPhotoUrl(photo: PhotoDomain): String {
        return "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg"
    }
}