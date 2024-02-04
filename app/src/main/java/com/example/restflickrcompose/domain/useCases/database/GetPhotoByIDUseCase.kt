package com.example.restflickrcompose.domain.useCases.database

import com.example.restflickrcompose.domain.model.PhotoObtain
import com.example.restflickrcompose.domain.model.toDomain
import com.example.restflickrcompose.domain.repository.Repository
import javax.inject.Inject

class GetPhotoByIDUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(id: String): PhotoObtain? {
        try {
            val photo = repository.getPhotoById(id)
            return photo?.toDomain()
        } catch (e: Exception) {
            throw Exception("Error al obtener las foto")
        }
    }
}
