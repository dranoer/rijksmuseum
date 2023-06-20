package com.dranoer.rijksmuseum

import androidx.paging.PagingData
import com.dranoer.rijksmuseum.MainViewModel.OverviewUiState
import com.dranoer.rijksmuseum.domain.ArtRepository
import com.dranoer.rijksmuseum.ui.ArtItem
import com.dranoer.rijksmuseum.ui.DetailItem
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private lateinit var repository: ArtRepository
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = MainViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `WHEN art data retrieved successfully THEN fetchArts updates overviewUiState to Success`() =
        runBlocking {
            // GIVEN
            val mockArtItem = ArtItem(
                id = "1",
                objectNumber = "1",
                artist = "Artist 1",
                title = "Title 1",
                description = "This is a description for artwork number 1",
                imageUrl = "",
                headerImageUrl = "",
            )
            val mockPagingData = PagingData.from(listOf(mockArtItem))
            coEvery { repository.fetchArtList(any()) } returns flowOf(mockPagingData)

            // WHEN
            viewModel.fetchArts()

            // THEN
            delay(1000)
            val actualState = viewModel.overviewUiState.value

            // As of my knowledge, Since we cannot directly compare Flow<PagingData<ArtGroup>> from the state with mockPagingData
            // We will just assume that if actualState is an instance of OverviewUiState.Success, the data loading was successful
            assertTrue(actualState is OverviewUiState.Success)
        }

    @Test
    fun `WHEN there is an error retrieving art data THEN fetchArts updates overviewUiState to Error`() =
        runBlocking {
            // GIVEN
            val errorMessage = "Failed to retrieve data"
            coEvery { repository.fetchArtList(any()) } throws Exception(errorMessage)

            // WHEN
            viewModel.fetchArts()

            // THEN
            delay(1000)
            val expectedState = OverviewUiState.Error(errorMessage)
            val actualState = viewModel.overviewUiState.value

            assertEquals(expectedState, actualState)
        }

    @Test
    fun `WHEN art detail data is retrieved successfully THEN fetchArtDetail updates detailUiState to Success`() =
        runBlocking {
            // GIVEN
            val objectNumber = "objectNumber"
            val detailItem = DetailItem(
                id = "1",
                artist = "artist 1",
                title = "title 1",
                description = "Artwork",
                imageUrl = "",
            )
            coEvery { repository.fetchArtDetail(objectNumber) } returns detailItem

            // WHEN
            viewModel.fetchArtDetail(objectNumber)

            // THEN
            delay(1000)
            val expectedState = MainViewModel.DetailUiState.Success(detailItem)
            val actualState = viewModel.detailUiState.value as MainViewModel.DetailUiState.Success

            assertEquals(expectedState.data, actualState.data)
        }

    @Test
    fun `WHEN there is an error retrieving art detail data THEN fetchArtDetail updates detailUiState to Error`() =
        runBlocking {
            // GIVEN
            val objectNumber = "objectNumber"
            val errorMessage = "Failed to retrieve data"
            coEvery { repository.fetchArtDetail(objectNumber) } throws Exception(errorMessage)

            // WHEN
            viewModel.fetchArtDetail(objectNumber)

            // THEN
            delay(1000)
            val expectedState = MainViewModel.DetailUiState.Error(errorMessage)
            val actualState = viewModel.detailUiState.value

            assertEquals(expectedState, actualState)
        }
}