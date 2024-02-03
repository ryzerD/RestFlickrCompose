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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.Coil
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.memory.MemoryCache
import com.example.restflickrcompose.domain.model.PhotoObtain
import com.example.restflickrcompose.screens.routes.Routes


@Composable
fun ViewerScreen(
    viewerViewModel: ViewerViewModel = viewModel(),
    navigationController: NavHostController,
) {
    val photosState by viewerViewModel.photosList.observeAsState()

    ViewerContent(photosState, viewerViewModel, navigationController)
    DisplaySnackbar(viewerViewModel)
}

@Composable
fun ViewerContent(
    photosState: PhotoState?,
    viewerViewModel: ViewerViewModel,
    navigationController: NavHostController
) {
    when (photosState) {
        is PhotoState.Loading -> DisplayLoadingState()
        is PhotoState.Success -> DisplaySuccessState(
            photosState,
            viewerViewModel,
            navigationController
        )

        is PhotoState.Error -> DisplayErrorState()
        else -> {}
    }
}

@Composable
fun ImageCard(photo: PhotoObtain, onItemSelected: (String) -> Unit) {
    Card(modifier = Modifier
        .padding(16.dp)
        .clickable {
            onItemSelected(photo.id)
        }) {
        ImageItem(photo)
        TextItem(photo)
    }
}


@Composable
fun DisplaySnackbar(viewerViewModel: ViewerViewModel) {
    val snackbarHostState = rememberSaveable { SnackbarHostState() }
    val isNetworkAvailable by viewerViewModel.networkMonitor.isNetworkAvailable.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

    if (!isNetworkAvailable) {
        LaunchedEffect(key1 = snackbarHostState) {
            snackbarHostState.showSnackbar(
                message = "Algo no esta iendo bien con tu conexion a internet, no te preocupes intentaremos de nuevo",
                actionLabel = "Dismiss",
                duration = SnackbarDuration.Short
            )
        }
    }
}

@Composable
fun ImageItem(photo: PhotoObtain) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .memoryCache {
            MemoryCache.Builder(context).maxSizePercent(0.25).build()
        } // Use 25% of the application's available memory.
        .crossfade(true) // Show a crossfade when loading images.
        .build()
    Coil.setImageLoader(imageLoader)
    AsyncImage(
        model = photo.url,
        contentDescription = "Image from Flickr",
        modifier = Modifier
            .width(200.dp)
            .height(200.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun TextItem(photo: PhotoObtain) {
    Text(text = photo.title, maxLines = 2, modifier = Modifier.padding(8.dp))
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
            ImageCard(photo) { item ->
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

