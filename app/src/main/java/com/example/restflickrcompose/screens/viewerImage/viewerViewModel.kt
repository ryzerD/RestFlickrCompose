package com.example.restflickrcompose.screens.viewerImage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restflickrcompose.domain.model.PhotoObtain
import com.example.restflickrcompose.domain.useCases.FlickrApi.GetPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class viewerViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase
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
                        title = photo.title
                    )
                } ?: emptyList()
                _photosList.postValue(PhotoState.Success(photosList))
            } catch (e: Exception) {
                _photosList.postValue(PhotoState.Error(e.message ?: "Unknown error"))
            }
        }
    }

}


sealed class PhotoState {
    object Loading : PhotoState()
    data class Success(val categories: List<PhotoObtain>) : PhotoState()
    data class Error(val message: String) : PhotoState()
}