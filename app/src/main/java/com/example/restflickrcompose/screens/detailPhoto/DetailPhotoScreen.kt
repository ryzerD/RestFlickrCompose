package com.example.restflickrcompose.screens.detailPhoto

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.restflickrcompose.domain.model.PhotoObtain
import com.example.restflickrcompose.screens.routes.Routes

@Composable
fun DetailPhoto(
    navigationController: NavController,
    arguments: String?,
    viewerViewModel: DetailViewModel
) {
    val photosState by viewerViewModel.photosList.observeAsState()
    viewerViewModel.filterPhotosById(arguments!!)

    Column {
        MyTopAppBar(navigationController)
        DetailContent(photosState)
    }

}

@Composable
fun DetailContent(
    photosState: PhotoDetailState?
) {
    when (photosState) {
        is PhotoDetailState.Loading -> {
            DisplayLoadingState()
        }

        is PhotoDetailState.Success -> {
            Body(photosState.photoState)
        }

        is PhotoDetailState.Error -> {
            DisplayErrorState()
        }

        else -> {}
    }
}

@Composable
fun DisplayLoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Cargando...")
            CircularProgressIndicator()
        }
    }

}


@Composable
fun Body(photo: PhotoObtain) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = photo.url,
            contentDescription = "Image from Flickr",
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentScale = ContentScale.Fit
        )
        Text(text = photo.title, fontWeight = FontWeight.Bold)
    }
}


@Composable
fun MyTopAppBar(navigationController: NavController) {
    BottomAppBar(actions = {
        IconButton(onClick = { navigationController.navigate(Routes.Screen1.route) }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Build description")
        }
    })
}


@Composable
fun CustomSnackbar(message: String, actionLabel: String = "Dismiss") {
    val snackbarHostState = remember { SnackbarHostState() }

    Box(modifier = Modifier.fillMaxSize()) {
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

    LaunchedEffect(key1 = snackbarHostState) {
        snackbarHostState.showSnackbar(
            message = message,
            actionLabel = actionLabel,
            duration = SnackbarDuration.Short
        )
    }
}


@Composable
fun DisplayErrorState() {
   CustomSnackbar("Ha ocurrido un error")
}

