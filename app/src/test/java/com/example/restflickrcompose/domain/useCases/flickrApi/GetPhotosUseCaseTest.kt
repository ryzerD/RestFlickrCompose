package com.example.restflickrcompose.domain.useCases.flickrApi

import com.example.restflickrcompose.data.database.model.toDatabase
import com.example.restflickrcompose.domain.model.FlickrDomain
import com.example.restflickrcompose.domain.model.PhotoDomain
import com.example.restflickrcompose.domain.model.PhotoObtain
import com.example.restflickrcompose.domain.model.PhotosDomain
import com.example.restflickrcompose.domain.repository.Repository
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
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
        coVerify(exactly = 0) { repository.insertPhotos(any()) }
        coVerify(exactly = 1) { repository.getPhotos() }
        coVerify(exactly = 1) { repository.getPhotosFromDb() }
    }


    @Test
    fun `invoke returns expected result`() = runBlockingTest {
        // 1. Crear un objeto simulado (mock) de Repository
        val repository = mockk<Repository>()

        // 2. Configurar el comportamiento de los métodos en el objeto simulado de Repository
        val expectedPhotos = listOf(
            PhotoObtain(
                id = "1",
                url = "https://farm1.staticflickr.com/1/1_1.jpg",
                title = "1",
                page = 1
            )
        )
        coEvery { repository.getPhotos() } returns FlickrDomain(
            PhotosDomain(
                1,
                1,
                1,
                listOf(PhotoDomain(1, "1", 1, 1, 1, "1", "1", "1", "1")),
                1
            ), stat = "ok"
        )
        coEvery { repository.getPhotosFromDb() } returns expectedPhotos.map { it.toDatabase() }
        coJustRun { repository.insertPhotos(any()) }

        // 3. Crear una instancia de GetPhotosUseCase pasando el objeto simulado de Repository
        val getPhotosUseCase = GetPhotosUseCase(repository)

        // 4. Llamar a la función invoke() en la instancia de GetPhotosUseCase
        val result = getPhotosUseCase.invoke()

        // 5. Verificar que los métodos en el objeto simulado de Repository se llamaron la cantidad correcta de veces
        coVerify(exactly = 1) { repository.getPhotos() }
        coVerify(exactly = 1) { repository.insertPhotos(any()) }
        coVerify(exactly = 0) { repository.getPhotosFromDb() }

        // 6. Asegurarte de que la función invoke() devuelve el resultado esperado
        assertEquals(expectedPhotos, result)
    }

}