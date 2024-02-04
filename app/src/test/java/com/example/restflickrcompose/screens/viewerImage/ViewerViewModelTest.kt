package com.example.restflickrcompose.screens.viewerImage

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.restflickrcompose.NetworkMonitor
import com.example.restflickrcompose.domain.model.FlickrDomain
import com.example.restflickrcompose.domain.useCases.flickrApi.GetMorePhotosUseCase
import com.example.restflickrcompose.domain.useCases.flickrApi.GetPhotosUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations


class ViewerViewModelTest() {
    @RelaxedMockK
    private lateinit var getPhotosUseCase: GetPhotosUseCase

    @RelaxedMockK
    private lateinit var getMorePhotos: GetMorePhotosUseCase

    @RelaxedMockK
    private lateinit var networkMonitor: NetworkMonitor

    private lateinit var viewerViewModel: ViewerViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun onBefore() {
        MockitoAnnotations.initMocks(this)
        viewerViewModel = ViewerViewModel(getPhotosUseCase, getMorePhotos, networkMonitor)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }


/*    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getPhotos should post success state when use case returns valid data`() = runBlockingTest {
        // Arrange
        val flickrDomain = FlickrDomain(photos = mockk(),stat = "ok")

        coEvery { getPhotosUseCase() } returns flickrDomain

        // Act
        viewerViewModel.getPhotos()

        // Assert
        coVerify { getPhotosUseCase() }
        val actualState = viewerViewModel.photosList
        *//*assert(flickrDomain == actualState)*//*
    }*/

    /*    @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun `getPhotos should post error state when use case throws exception`() = runBlockingTest {
            // Arrange
            val expectedState = PhotoState.Error("Unknown error")

            coEvery { getPhotosUseCase() } throws Exception()
            every { _photosList.postValue(any()) } just Runs

            // Act
            viewerViewModel.getPhotos()

            // Assert
            coVerify { getPhotosUseCase() }
            verify { _photosList.postValue(expectedState) }
        }*/


}