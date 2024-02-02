package com.example.restflickrcompose.data.repositoryImpl

import com.example.restflickrcompose.data.network.flickr.FlickrService
import com.example.restflickrcompose.domain.model.FlickrDomain
import com.example.restflickrcompose.domain.model.toDomain
import com.example.restflickrcompose.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val service: FlickrService
) : Repository {

    private var photoCache: FlickrDomain? = null
    override suspend fun getPhotos(): FlickrDomain {
        // Si el caché no está vacío, devolver los datos del caché
        photoCache?.let { return it }

        // Si el caché está vacío, hacer una llamada a la red
        val response = service.getPhotos()
        if (response.isSuccessful) {
            val flickrResponse = response.body()
            if (flickrResponse != null) {
                // Convertir FlickrResponse a FlickrDomain y almacenar en el caché
                photoCache = flickrResponse.toDomain()
                return photoCache!!
            }
        }
        throw Exception("Failed to fetch photos")
    }

    override suspend fun getMorePhotos(page: Int): FlickrDomain {
        val response = service.getMorePhotos(page)
        if (response.isSuccessful) {
            val flickrResponse = response.body()
            if (flickrResponse != null) {
                return flickrResponse.toDomain()
            }
        }
        throw Exception("Failed to fetch photos")
    }


}
