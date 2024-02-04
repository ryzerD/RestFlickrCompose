package com.example.restflickrcompose.screens.viewerImage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restflickrcompose.NetworkMonitor
import com.example.restflickrcompose.domain.model.PhotoObtain
import com.example.restflickrcompose.domain.useCases.flickrApi.GetMorePhotosUseCase
import com.example.restflickrcompose.domain.useCases.flickrApi.GetPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewerViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase,
    private val getMorePhotos: GetMorePhotosUseCase,
    val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _photosList = MutableLiveData<PhotoState>()
    val photosList: LiveData<PhotoState> get() = _photosList


    fun getPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            _photosList.postValue(PhotoState.Loading)
            try {
                val searchResponse = getPhotosUseCase()
                _photosList.postValue(PhotoState.Success(searchResponse))
            } catch (e: Exception) {
                _photosList.postValue(PhotoState.Error(e.message ?: "Unknown error"))
                return@launch
            }
        }
    }

    fun loadMorePhotos(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val searchResponse = getMorePhotos(page)
                val currentPhotosList =
                    (_photosList.value as? PhotoState.Success)?.photoState ?: emptyList()
                val updatedPhotosList = currentPhotosList + searchResponse
                _photosList.postValue(PhotoState.Success(updatedPhotosList))
            } catch (e: Exception) {
                _photosList.postValue(PhotoState.Error(e.message ?: "Unknown error"))
            }
        }
    }



    // This function is for testing purposes only
    fun setPhotosListForTesting(photoState: PhotoState) {
        _photosList.value = photoState
    }
}


sealed class PhotoState {
    object Loading : PhotoState()
    data class Success(val photoState: List<PhotoObtain>) : PhotoState()
    data class Error(val message: String) : PhotoState()
}