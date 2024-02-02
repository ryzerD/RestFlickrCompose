package com.example.restflickrcompose.screens.viewerImage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ViewerScreen(viewerViewModel: viewerViewModel) {
    /*  LazyColumn {
          items(10) {
              ImageItem()
          }
      }*/

    Button(onClick = { viewerViewModel.getPhotos() }) {
        Text(text = "Pulsar")
    }
}

@Composable
fun ImageItem() {
    Card(modifier = Modifier.width(200.dp)) {
        Column {

            Text("Image Item")

        }
    }
}
