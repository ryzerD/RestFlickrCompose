package com.example.restflickrcompose.screens.routes


sealed class Routes(val route: String) {
    object Screen1 : Routes("screen1")
    object Screen2 : Routes("screen2/{photo}") {
        fun createRoute(photo: String) = "screen2/$photo"
    }
}