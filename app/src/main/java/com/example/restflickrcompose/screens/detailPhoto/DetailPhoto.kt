package com.example.restflickrcompose.screens.detailPhoto

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.restflickrcompose.domain.model.PhotoObtain
import com.example.restflickrcompose.screens.routes.Routes

@Composable
fun DetailPhoto(navigationController: NavController, arguments: String?) {
    Column {
        MyTopAppBar(navigationController)
       /* Body()*/
    }

}

@Composable
fun Body(photo: PhotoObtain) {
    Card(modifier = Modifier
        .padding(16.dp)) {
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
fun MyTopAppBar(navigationController: NavController) {
    BottomAppBar(actions = {
        IconButton(onClick = { navigationController.navigate(Routes.Screen1.route) }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Build description")
        }
    })
}