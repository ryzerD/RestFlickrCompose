package com.example.restflickrcompose.screens.detailPhoto

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restflickrcompose.domain.model.PhotoObtain
import com.example.restflickrcompose.domain.useCases.database.GetPhotoByIDUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getPhotoByID: GetPhotoByIDUseCase
) : ViewModel() {

    private val _photosList = MutableLiveData<PhotoDetailState>()
    val photosList: LiveData<PhotoDetailState> get() = _photosList


    fun filterPhotosById(id: String): PhotoObtain? {
        Log.d("DetailViewModel", "filterPhotosById: $id")

        val result: PhotoObtain? = null
        viewModelScope.launch(Dispatchers.IO) {
            val photoState = async { getPhotoByID(id) }
            Log.d("DetailViewModel", "filterPhotosById: $photoState")
            _photosList.postValue(photoState.await()?.let { PhotoDetailState.Success(it) })
        }
        return result
    }
}


sealed class PhotoDetailState {
    object Loading : PhotoDetailState()
    data class Success(val photoState: PhotoObtain) : PhotoDetailState()
    data class Error(val message: String) : PhotoDetailState()
}
