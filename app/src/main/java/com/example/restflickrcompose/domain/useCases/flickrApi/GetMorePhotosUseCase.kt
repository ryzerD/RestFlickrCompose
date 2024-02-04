package com.example.restflickrcompose.domain.useCases.flickrApi

import com.example.restflickrcompose.data.database.model.toDatabase
import com.example.restflickrcompose.domain.model.PhotoDomain
import com.example.restflickrcompose.domain.model.PhotoObtain
import com.example.restflickrcompose.domain.model.toDomain
import com.example.restflickrcompose.domain.repository.Repository
import javax.inject.Inject

class GetMorePhotosUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(page: Int): List<PhotoObtain> {
        return try {
            val photos = repository.getMorePhotos(page)

            if (photos != null) {
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

                obtain
            } else {
                // Cargar más información de la base de datos si getMorePhotos devuelve null
                repository.getPhotosFromDb().map { it.toDomain() }
            }
        } catch (e: Exception) {
            // Aquí puedes manejar la excepción como prefieras
            throw Exception("Error al obtener mas fotos")
        }
    }

    private fun createPhotoUrl(photo: PhotoDomain): String {
        return "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg"
    }
}