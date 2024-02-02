package com.example.restflickrcompose.data.network.flickr

import android.util.Log
import com.example.restflickrcompose.data.network.flickr.model.FlickrResponse
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlickrService @Inject constructor(
    private val flickrClient: FlickrClient
) {
    suspend fun getPhotos(): Response<FlickrResponse> {
        return try {
            flickrClient.getPhotos()
        } catch (e: IOException) {
            // Maneja errores de red
            Log.e("ApiAdminService", "Error de red", e)
            Response.error(400, ResponseBody.create(null, "Error de red: ${e.message}"))
        } catch (e: HttpException) {
            // Maneja respuestas no exitosas de la API
            Log.e("ApiAdminService", "Respuesta no exitosa de la API", e)
            Response.error(
                e.code(),
                e.response()?.errorBody() ?: ResponseBody.create(
                    null,
                    "Respuesta no exitosa de la API"
                )
            )
        } catch (e: Exception) {
            // Maneja cualquier otra excepción
            Log.e("ApiAdminService", "Error desconocido", e)
            Response.error(400, ResponseBody.create(null, "Error desconocido: ${e.message}"))
        }
    }
}