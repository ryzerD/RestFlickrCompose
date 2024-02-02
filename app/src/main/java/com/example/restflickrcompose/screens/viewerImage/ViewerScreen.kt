package com.example.restflickrcompose.screens.viewerImage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.restflickrcompose.domain.model.PhotoObtain
import com.example.restflickrcompose.screens.routes.Routes


@Composable
fun ViewerScreen(
    viewerViewModel: ViewerViewModel = viewModel(),
    navigationController: NavHostController
) {
    val photosState by viewerViewModel.photosList.observeAsState()

    when (photosState) {
        is PhotoState.Loading -> DisplayLoadingState()
        is PhotoState.Success -> DisplaySuccessState(
            photosState as PhotoState.Success,
            viewerViewModel,
            navigationController
        )

        is PhotoState.Error -> DisplayErrorState()
        else -> {}
    }
}


@Composable
fun ImageItem(photo: PhotoObtain, onItemSelected: (String) -> Unit) {
    Card(modifier = Modifier
        .padding(16.dp)
        .clickable {
            onItemSelected(
                photo.id
            )
        }) {
        AsyncImage(
            model = photo.url,
            contentDescription = "Image from Flickr",
            modifier = Modifier
                .width(200.dp)
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
        Text(text = photo.title)
    }

}


@Composable
fun DisplayLoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }

}

@Composable
fun DisplaySuccessState(
    photoState: PhotoState.Success,
    viewerViewModel: ViewerViewModel,
    navigationController: NavHostController
) {
    val photosList = photoState.photoState
    LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
        itemsIndexed(photosList) { index, photo ->
            ImageItem(photo) { item ->
                navigationController.navigate(Routes.Screen2.createRoute(item))
            }
            if (index == photosList.lastIndex - 10) {
                viewerViewModel.loadMorePhotos(photo.page + 1)
            }
        }
    })
}


@Composable
fun DisplayErrorState() {
    // Mostrar un mensaje de error
}

