package com.example.restflickrcompose.screens.viewerImage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage


@Composable
fun ViewerScreen(viewerViewModel: ViewerViewModel = viewModel()) {
    val photosState by viewerViewModel.photosList.observeAsState()

    when (photosState) {
        is PhotoState.Loading -> DisplayLoadingState()
        is PhotoState.Success -> DisplaySuccessState(
            photosState as PhotoState.Success,
            viewerViewModel
        )

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
fun DisplaySuccessState(photoState: PhotoState.Success, viewerViewModel: ViewerViewModel) {
    val photosList = photoState.photoState
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
        itemsIndexed(photosList) { index, photo ->
            ImageItem(photo.url, photo.title)
            if (index ==  photosList.lastIndex) {

                LaunchedEffect(key1 = index) {
                    listState.animateScrollToItem(0) // Scroll to the top of the list
                }
                viewerViewModel.loadMorePhotos(photo.page + 1)
            }
        }
    })
}


@Composable
fun DisplayErrorState() {
    // Mostrar un mensaje de error
}

