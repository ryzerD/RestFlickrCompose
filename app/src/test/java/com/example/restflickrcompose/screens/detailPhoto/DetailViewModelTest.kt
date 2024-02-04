package com.example.restflickrcompose.screens.detailPhoto


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.restflickrcompose.domain.model.PhotoObtain
import com.example.restflickrcompose.domain.useCases.database.GetPhotoByIDUseCase
import com.example.restflickrcompose.screens.viewerImage.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel

    @RelaxedMockK
    private lateinit var getPhotoByIDUseCase: GetPhotoByIDUseCase


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        getPhotoByIDUseCase = mockk(relaxed = true)
        viewModel = DetailViewModel(getPhotoByIDUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `filterPhotosById returns expected result`() = runBlocking {
        // Given
        val expectedPhoto = PhotoObtain(
            id = "1",
            url = "https://farm1.staticflickr.com/1/1_1.jpg",
            title = "1",
            page = 1
        )
        coEvery { getPhotoByIDUseCase("1") } returns expectedPhoto

        // When
        viewModel.filterPhotosById("1")

        // Then
        val value = viewModel.photosList.getOrAwaitValue()
        when (value) {
            is PhotoDetailState.Success -> assertEquals(expectedPhoto, value.photoState)
            else -> fail("Unexpected value: $value")
        }
    }


    @Test
    fun `filterPhotosById applies filter correctly`() = runBlocking {
        // Given
        val expectedPhoto = PhotoObtain(
            id = "1",
            url = "https://farm1.staticflickr.com/1/1_1.jpg",
            title = "1",
            page = 1
        )
        coEvery { getPhotoByIDUseCase("1") } returns expectedPhoto

        // When
        viewModel.filterPhotosById("1")

        // Then
        val value = viewModel.photosList.getOrAwaitValue()
        when (value) {
            is PhotoDetailState.Success -> assertEquals(expectedPhoto, value.photoState)
            else -> fail("Unexpected value: $value")
        }
    }
}