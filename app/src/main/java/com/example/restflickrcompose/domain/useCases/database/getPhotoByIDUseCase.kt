package com.example.restflickrcompose.domain.useCases.database

import android.util.Log
import com.example.restflickrcompose.domain.model.PhotoObtain
import com.example.restflickrcompose.domain.model.toDomain
import com.example.restflickrcompose.domain.repository.Repository
import javax.inject.Inject

class getPhotoByIDUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(id: String): PhotoObtain? {
        try {
            val photo = repository.getPhotoById(id)
            if (photo != null) {
                Log.d("getPhotoByIDUseCase", "invoke: $photo")
                return photo.toDomain()
            }
            return null
            // Continue with your logic here...
        } catch (e: Exception) {
            throw Exception("Error al obtener las foto")
            // Handle exception here...
        }
    }
}
