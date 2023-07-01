package com.dranoer.rijksmuseum

import androidx.paging.PagingSource
import com.dranoer.rijksmuseum.domain.ArtRepository
import com.dranoer.rijksmuseum.networking.WebService
import com.dranoer.rijksmuseum.networking.mapper.ArtMapper
import com.dranoer.rijksmuseum.networking.mapper.DetailMapper
import com.dranoer.rijksmuseum.networking.model.ArtHeaderImage
import com.dranoer.rijksmuseum.networking.model.ArtImage
import com.dranoer.rijksmuseum.networking.model.ArtItem
import com.dranoer.rijksmuseum.networking.model.ArtObject
import com.dranoer.rijksmuseum.networking.model.ArtResponse
import com.dranoer.rijksmuseum.networking.model.DetailImage
import com.dranoer.rijksmuseum.networking.model.DetailItem
import com.dranoer.rijksmuseum.networking.model.DetailObject
import com.dranoer.rijksmuseum.networking.model.DetailResponse
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows

class ArtRepositoryTest {
    private lateinit var repository: ArtRepository
    private lateinit var webService: WebService
    private lateinit var artMapper: ArtMapper
    private lateinit var detailMapper: DetailMapper
    private lateinit var pagingSource: PagingSource<Int, ArtItem>

    @Before
    fun setup() {
        webService = mockk()
        artMapper = mockk()
        detailMapper = mockk()
        pagingSource = mockk()
        repository = ArtRepository(webService, artMapper, detailMapper)
    }

    @Test
    fun `WHEN response is mapped THEN return ArtItem`() {
        // GIVEN
        val artObject = ArtObject(
            id = "1",
            objectNumber = "",
            artist = "Artist 1",
            title = "Title 1",
            description = "This is a description for artwork number 1",
            image = ArtImage(
                guid = "guid",
                url = ""
            ),
            headerImage = ArtHeaderImage(
                guid = "guid",
                url = ""
            )
        )
        val mockResponse = ArtResponse(artObjects = listOf(artObject))
        val expectedArtItem = ArtItem(
            id = "1",
            objectNumber = "",
            artist = "Artist 1",
            title = "Title 1",
            description = "This is a description for artwork number 1",
            imageUrl = "",
            headerImageUrl = ""
        )
        val artMapper = ArtMapper()

        // WHEN
        val actualArtItems = artMapper.map(mockResponse)

        // THEN
        assertEquals(
            expectedArtItem,
            actualArtItems.first()
        )
    }

    @Test
    fun `WHEN art detail is retrieved successfully THEN fetchArtDetail returns DetailItem`() =
        runBlocking {
            // GIVEN
            val objectNumber = "objectNumber"
            val mockDetailItem = DetailItem(
                id = "1",
                artist = "Artist 1",
                title = "Title 1",
                description = "This is a description for artwork number 1",
                imageUrl = "",
            )
            val mockArtDetailResponse = DetailResponse(
                artDetail = DetailObject(
                    id = "1",
                    artist = "Artist 1",
                    title = "Title 1",
                    description = "This is a description for artwork number 1",
                    image = DetailImage(
                        guid = "guid",
                        url = ""
                    )
                )
            )
            coEvery { webService.fetchArtDetail(objectNumber) } returns mockArtDetailResponse
            every { detailMapper.map(any()) } returns mockDetailItem

            // WHEN
            val actualDetailItem = repository.fetchArtDetail(objectNumber)

            // THEN
            assertEquals(mockDetailItem, actualDetailItem)
        }

    @Test
    fun `WHEN exception occurs THEN fetchArtDetail throws error`() =
        runBlocking {
            // GIVEN
            val objectNumber = "objectNumber"
            val exceptionMessage = "Something went wrong"
            coEvery { webService.fetchArtDetail(objectNumber) } throws Exception(exceptionMessage)

            // WHEN
            val exception = assertThrows<Exception> { repository.fetchArtDetail(objectNumber) }

            // THEN
            assertEquals(exceptionMessage, exception.message)
        }
}