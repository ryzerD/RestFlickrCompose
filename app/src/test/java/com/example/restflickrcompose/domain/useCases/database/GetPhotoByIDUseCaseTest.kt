package com.example.restflickrcompose.domain.useCases.database

import com.example.restflickrcompose.data.database.model.PhotoEntity
import com.example.restflickrcompose.domain.model.toDomain
import com.example.restflickrcompose.domain.repository.Repository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class GetPhotoByIDUseCaseTest {

    lateinit var getPhotoByIDUseCase: GetPhotoByIDUseCase

    @RelaxedMockK
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = mockk(relaxed = true)
        getPhotoByIDUseCase = GetPhotoByIDUseCase(repository)
    }


    @Test
    fun `when database do not return something, return null`() = runBlocking {
        //Given
        val emptyFlickrDomain: PhotoEntity? = null
        coEvery { repository.getPhotoById("1") } returns emptyFlickrDomain

        //When
        val database = getPhotoByIDUseCase("1")

        //Then
        coVerify(exactly = 1) { repository.getPhotoById("1") }
        assert(database == null)
    }


    @Test
    fun `when repository returns a photo, invoke returns the photo`() = runBlocking {
        // Given
        val expectedPhoto = PhotoEntity(
            id = "1",
            url = "https://farm1.staticflickr.com/1/1_1.jpg",
            title = "1",
            page = 1
        )
        val expectedPhotoObtain = expectedPhoto.toDomain()
        coEvery { repository.getPhotoById("1") } returns expectedPhoto

        // When
        val result = getPhotoByIDUseCase("1")

        // Then
        coVerify(exactly = 1) { repository.getPhotoById("1") }
        println(expectedPhotoObtain)
        println(result)
        assertEquals(expectedPhotoObtain, result)
    }


    @Test
    fun `getPhotoByIDUseCase throws an exception`() = runBlocking {
        // Given
        val expectedExceptionMessage = "Error al obtener las foto"

        // Mock the getPhotoByIDUseCase to throw an exception when invoked
        coEvery { repository.getPhotoById("1") } throws Exception(expectedExceptionMessage)

        // When
        var actualExceptionMessage: String? = null
        try {
            getPhotoByIDUseCase("1")
        } catch (e: Exception) {
            actualExceptionMessage = e.message
        }

        // Then
        assertNotNull(actualExceptionMessage)
        assertEquals(expectedExceptionMessage, actualExceptionMessage)
    }


}