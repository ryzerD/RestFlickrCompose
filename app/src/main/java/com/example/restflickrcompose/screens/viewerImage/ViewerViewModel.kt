package com.example.restflickrcompose.screens.viewerImage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restflickrcompose.domain.model.PhotoObtain
import com.example.restflickrcompose.domain.useCases.FlickrApi.GetMorePhotosUseCase
import com.example.restflickrcompose.domain.useCases.FlickrApi.GetPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewerViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase,
    private val getMorePhotos: GetMorePhotosUseCase
) : ViewModel() {

    private val _photosList = MutableLiveData<PhotoState>()
    val photosList: LiveData<PhotoState> get() = _photosList


    fun getPhotos() {
        viewModelScope.launch {
            try {
                val searchResponse = getPhotosUseCase()
                val photosList = searchResponse?.photos?.photo?.map { photo ->
                    PhotoObtain(
                        id = photo.id,
                        url = "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg",
                        title = photo.title,
                        page = searchResponse.photos.page

                    )
                } ?: emptyList()
                _photosList.postValue(PhotoState.Success(photosList))
            } catch (e: Exception) {
                _photosList.postValue(PhotoState.Error(e.message ?: "Unknown error"))
            }
        }
    }

    fun loadMorePhotos(page: Int) {
        viewModelScope.launch {
            try {
                val searchResponse = getMorePhotos(page)
                val newPhotosList = searchResponse?.photos?.photo?.map { photo ->
                    PhotoObtain(
                        id = photo.id,
                        url = "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg",
                        title = photo.title,
                        page = searchResponse.photos.page
                    )
                } ?: emptyList()

                val currentPhotosList = (_photosList.value as? PhotoState.Success)?.photoState ?: emptyList()
                val updatedPhotosList = currentPhotosList + newPhotosList
                _photosList.postValue(PhotoState.Success(updatedPhotosList))
            } catch (e: Exception) {
                // Manejo de errores...
            }
        }
    }
}


sealed class PhotoState {
    object Loading : PhotoState()
    data class Success(val photoState: List<PhotoObtain>) : PhotoState()
    data class Error(val message: String) : PhotoState()
}