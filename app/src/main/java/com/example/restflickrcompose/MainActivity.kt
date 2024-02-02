package com.example.restflickrcompose

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.restflickrcompose.screens.detailPhoto.DetailPhoto
import com.example.restflickrcompose.screens.routes.Routes
import com.example.restflickrcompose.screens.viewerImage.ViewerScreen
import com.example.restflickrcompose.screens.viewerImage.ViewerViewModel
import com.example.restflickrcompose.ui.theme.RestFlickrComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewerViewModel: ViewerViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val loadPhotos = async { viewerViewModel.getPhotos() }
            loadPhotos.await()
        }

        setContent {
            RestFlickrComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navigationController = rememberNavController()
                    NavHost(
                        navController = navigationController,
                        startDestination = Routes.Screen1.route
                    ) {
                        composable(Routes.Screen1.route) {
                            ViewerScreen(
                                viewerViewModel,
                                navigationController
                            )
                        }
                        composable(Routes.Screen2.route) { backStackEntry ->
                            val photoObtain = backStackEntry.arguments?.getString("photo")
                            DetailPhoto(navigationController, photoObtain, viewerViewModel)
                        }
                    }


                }
            }
        }


    }
}

