package com.example.restflickrcompose.data.repositoryImpl

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.example.restflickrcompose.data.database.dao.PhotoDao
import com.example.restflickrcompose.data.database.model.PhotoEntity
import com.example.restflickrcompose.data.network.flickr.FlickrService
import com.example.restflickrcompose.domain.model.FlickrDomain
import com.example.restflickrcompose.domain.model.toDomain
import com.example.restflickrcompose.domain.repository.Repository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val service: FlickrService,
    private val dao: PhotoDao
) : Repository {

    override suspend fun getPhotos(): FlickrDomain? {
        return try {
            service.getPhotos().body()?.toDomain()
        } catch (e: IOException) {
            // Handle network errors
            Log.e("ApiAdminService", "Network error", e)
            null
        } catch (e: HttpException) {
            // Handle unsuccessful API responses
            Log.e("ApiAdminService", "Unsuccessful API response", e)
            null
        } catch (e: Exception) {
            // Handle any other exceptions
            Log.e("ApiAdminService", "Unknown error", e)
            null
        }
    }

    override suspend fun getMorePhotos(page: Int): FlickrDomain? {
        return try {
            val response = service.getMorePhotos(page)

            val flickrResponse = response.body()
            if (flickrResponse != null) {
                return flickrResponse.toDomain()
            }
            null
        } catch (e: IOException) {
            // Handle network errors
            Log.e("ApiAdminService", "Network error", e)
            null
        } catch (e: HttpException) {
            // Handle unsuccessful API responses
            Log.e("ApiAdminService", "Unsuccessful API response", e)
            null
        } catch (e: Exception) {
            // Handle any other exceptions
            Log.e("ApiAdminService", "Unknown error", e)
            null
        }
    }

    override suspend fun insertPhotos(photoObtain: List<PhotoEntity>) {
        try {
            return dao.insertAll(photoObtain)
        } catch (e: SQLiteConstraintException) {
            // Registra el error
            Log.e("insertMovimientosInventarioDao", "Error al insertar Estados", e)
            // Re-lanza la excepción
            throw e
        }
    }

    override suspend fun getPhotosFromDb(): List<PhotoEntity> {
        return try {
            dao.getAllPhotos()
        } catch (e: Exception) {
            // Maneja el error aquí. Podrías registrar el error, mostrar un mensaje al usuario, etc.
            // Por ahora, simplemente vamos a imprimir el error y devolver una lista vacía.
            Log.e("getCategories", "Error al obtener Categorias", e)
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getPhotoById(id: String): PhotoEntity? {
        return try {
            dao.getPhotoById(id)
        } catch (e: Exception) {
            // Maneja el error aquí. Podrías registrar el error, mostrar un mensaje al usuario, etc.
            // Por ahora, simplemente vamos a imprimir el error y devolver una lista vacía.
            Log.e("getCategories", "Error al obtener Categorias", e)
            e.printStackTrace()
           null
        }
    }


}
