package com.example.restflickrcompose.screens.viewerImage

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.restflickrcompose.NetworkMonitor
import com.example.restflickrcompose.domain.model.PhotoObtain
import com.example.restflickrcompose.domain.useCases.flickrApi.GetMorePhotosUseCase
import com.example.restflickrcompose.domain.useCases.flickrApi.GetPhotosUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.fail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


class ViewerViewModelTest() {
    @RelaxedMockK
    private lateinit var getPhotosUseCase: GetPhotosUseCase

    @RelaxedMockK
    private lateinit var getMorePhotos: GetMorePhotosUseCase

    @RelaxedMockK
    private lateinit var networkMonitor: NetworkMonitor

    private lateinit var viewerViewModel: ViewerViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun onBefore() {
        MockitoAnnotations.initMocks(this)
        getPhotosUseCase = mockk(relaxed = true)
        getMorePhotos = mockk(relaxed = true)
        networkMonitor = mockk(relaxed = true)
        viewerViewModel = ViewerViewModel(getPhotosUseCase, getMorePhotos, networkMonitor)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }


    @Test
    fun `getPhotos returns expected result`() = runTest {
        // Given
        val expectedPhotos = listOf(
            PhotoObtain(
                id = "1",
                url = "https://farm1.staticflickr.com/1/1_1.jpg",
                title = "1",
                page = 1
            )

        )
        coEvery { getPhotosUseCase() } returns expectedPhotos

        // When
        viewerViewModel.getPhotos()

        // Then
        coVerify(exactly = 1) { getPhotosUseCase() }
        val actualPhotos = (viewerViewModel.photosList.value as PhotoState.Success).photoState
        assertEquals(expectedPhotos, actualPhotos)
    }

    @Test
    fun `getPhotos posts Error state when operation fails`() = runBlockingTest {
        // Given
        val expectedExceptionMessage = "Error al obtener las fotos"
        coEvery { getPhotosUseCase() } throws Exception(expectedExceptionMessage)

        // When
        viewerViewModel.getPhotos()

        // Then
        advanceUntilIdle() // Asegúrate de que todas las corrutinas hayan terminado
        val actualState = viewerViewModel.photosList.getOrAwaitValue()
        println("viewerViewModel.photosList.value: ${viewerViewModel.photosList.value}")
        println("actualState: $actualState")
        if (actualState is PhotoState.Error) {
            assertEquals(expectedExceptionMessage, actualState.message)
        } else {
            fail("Expected PhotoState.Error but was ${actualState::class.simpleName}")
        }
    }

    @Test
    fun `loadMorePhotos returns expected result`() = runTest {
        // Given
        val expectedPhotos = listOf(
            PhotoObtain(
                id = "1",
                url = "https://farm1.staticflickr.com/1/1_1.jpg",
                title = "1",
                page = 1
            )

        )
        coEvery { getMorePhotos(1) } returns expectedPhotos

        // When
        viewerViewModel.loadMorePhotos(1)

        // Then
        coVerify(exactly = 1) { getMorePhotos(1) }
        assert((viewerViewModel.photosList.value as PhotoState.Success).photoState == expectedPhotos)
    }


    @Test
    fun `loadMorePhotos posts Error state when operation fails`() = runTest {
        // Given
        val expectedExceptionMessage = "Error al obtener mas fotos"
        coEvery { getMorePhotos(1) } throws Exception(expectedExceptionMessage)


        // When
        viewerViewModel.loadMorePhotos(1)

        // Then

        val actualState = viewerViewModel.photosList.getOrAwaitValue()
        println("viewerViewModel.photosList.value: ${viewerViewModel.photosList.value}")
        println("actualState: $actualState")
        assert(actualState is PhotoState.Error)
        assertEquals(expectedExceptionMessage, (actualState as PhotoState.Error).message)
    }

}


fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(value: T) {
            data = value
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)

    try {
        afterObserve.invoke()

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

    } finally {
        this.removeObserver(observer)
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}