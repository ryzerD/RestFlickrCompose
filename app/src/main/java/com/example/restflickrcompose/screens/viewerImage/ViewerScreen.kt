package com.example.restflickrcompose.screens.viewerImage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage


@Composable
fun ViewerScreen(viewerViewModel: viewerViewModel = viewModel()) {
    val photosState by viewerViewModel.photosList.observeAsState()

    when (photosState) {
        is PhotoState.Loading -> DisplayLoadingState()
        is PhotoState.Success -> DisplaySuccessState(photosState as PhotoState.Success)
        is PhotoState.Error -> DisplayErrorState()
        else -> {}
    }
}


@Composable
fun ImageItem(url: String, title: String) {
    Card(modifier = Modifier.padding(16.dp)) {
        AsyncImage(
            model = url,
            contentDescription = "Image from Flickr",
            modifier = Modifier
                .width(200.dp)
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
        Text(text = title)
    }

}


@Composable
fun DisplayLoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }

}

@Composable
fun DisplaySuccessState(photoState: PhotoState.Success) {
    val photosList = photoState.categories
    LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
        items(photosList.size) { index ->
            ImageItem(photosList[index].url, photosList[index].title)
        }
    })
}

@Composable
fun DisplayErrorState() {
    // Mostrar un mensaje de error
}

