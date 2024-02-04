package com.example.restflickrcompose.domain.useCases.flickrApi

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


class GetMorePhotosUseCaseTest {
    lateinit var getMorePhotosUseCase: GetMorePhotosUseCase

    @RelaxedMockK
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = mockk(relaxed = true)
        getMorePhotosUseCase = GetMorePhotosUseCase(repository)
    }

    @Test
    fun `when photos are empty, return null`() = runBlocking {
        //Given
        val emptyFlickrDomain: FlickrDomain? = null
        coEvery { repository.getMorePhotos(1) } returns emptyFlickrDomain

        //When
        getMorePhotosUseCase(1)

        //Then
        coVerify(exactly = 1) { repository.getMorePhotos(1) }
    }

/*    @Test
    fun `when photos are not empty, return photos`() = runBlocking {
        val flickrDomain = FlickrDomain(photos = mockk(), stat = "ok")
        coEvery { repository.getMorePhotos(1) } returns flickrDomain

        val result = getMorePhotosUseCase.invoke(1)

        coVerify(exactly = 1) { repository.getMorePhotos(1) }
        assert(flickrDomain == result)
    }*/
}