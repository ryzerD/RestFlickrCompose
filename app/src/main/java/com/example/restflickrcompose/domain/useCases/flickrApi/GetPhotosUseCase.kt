package com.example.restflickrcompose.domain.useCases.flickrApi

import com.example.restflickrcompose.data.database.model.toDatabase
import com.example.restflickrcompose.domain.model.PhotoObtain
import com.example.restflickrcompose.domain.model.toDomain
import com.example.restflickrcompose.domain.repository.Repository
import javax.inject.Inject

class GetPhotosUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(): List<PhotoObtain> {
        try {
            val photos = repository.getPhotos()
            if (photos != null) {
                val obtain = photos?.photos?.photo?.map { photo ->
                    PhotoObtain(
                        id = photo.id,
                        url = "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg",
                        title = photo.title,
                        page = photos.photos.page
                    )
                } ?: emptyList()

                val photoEntities = obtain.map { it.toDatabase() }
                repository.insertPhotos(photoEntities)

                return obtain
            }
            return repository.getPhotosFromDb().map { it.toDomain() }

        } catch (e: Exception) {
            // Aquí puedes manejar la excepción como prefieras.
            // Por ejemplo, puedes devolver una lista vacía:
            throw Exception("Error al obtener las fotos")
            // O puedes lanzar una excepción personalizada:

        }
    }
}
