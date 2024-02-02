package com.example.restflickrcompose.screens.viewerImage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restflickrcompose.domain.useCases.FlickrApi.GetPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class viewerViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase
) : ViewModel() {


     fun getPhotos() {
        viewModelScope.launch {
            val result = getPhotosUseCase()
            Log.d("viewerViewModel", "getPhotos: $result")
        }
    }

}