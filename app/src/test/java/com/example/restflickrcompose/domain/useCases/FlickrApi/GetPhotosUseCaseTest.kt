package com.example.restflickrcompose.domain.useCases.FlickrApi

import com.example.restflickrcompose.domain.model.FlickrDomain
import com.example.restflickrcompose.domain.repository.Repository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class GetPhotosUseCaseTest {

     lateinit var getPhotosUseCase: GetPhotosUseCase

    @RelaxedMockK
    private lateinit var repository: Repository

    @Before
    fun onBefore() {
        MockitoAnnotations.initMocks(this)
        repository = mockk(relaxed = true)
        getPhotosUseCase = GetPhotosUseCase(repository)
    }

    @Test
    fun `when photos are empty, return null`() = runBlocking {
        //Given
        val emptyFlickrDomain: FlickrDomain? = null
        coEvery { repository.getPhotos() } returns emptyFlickrDomain

        //When
        getPhotosUseCase()

        //Then
        coVerify(exactly = 1) { repository.getPhotos() }
    }



    @Test
    fun `when the api return something then return Data `() = runBlocking {
        //Given
        val flickrDomain = FlickrDomain(photos = mockk(),stat = "ok")
        coEvery { repository.getPhotos() } returns flickrDomain

        //When
        val response = getPhotosUseCase()

        //Then
        coVerify(exactly = 1) { repository.getPhotos() }
        assert(response == flickrDomain)
    }

}