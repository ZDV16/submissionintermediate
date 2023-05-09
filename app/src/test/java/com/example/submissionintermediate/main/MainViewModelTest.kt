package com.example.submissionintermediate.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.submissionintermediate.adapter.StoriesAdapter
import com.example.submissionintermediate.api.ListStoryItem
import com.example.submissionintermediate.data.StoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest{

    private lateinit var mainViewModel: MainViewModel
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()
    @Mock
    private val repository = mock(StoryRepository::class.java)

    @Before
    fun getViewModel() {
        mainViewModel = MainViewModel(repository)
    }
    @Test
    fun `when get Story is Successful `() = runTest {
        val dummyStories = DataDummy.generateDummyStory()
        val data: PagingData<ListStoryItem> = PagingSourceTest.snapshot(dummyStories)
        val expectedStory = MutableLiveData<PagingData<ListStoryItem>>()

        expectedStory.value = data
        `when`(repository.getStory()).thenReturn(expectedStory)

        val actualStory: PagingData<ListStoryItem> =
            mainViewModel.getStories().getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoriesAdapter.DIFF_CALLBACK,
            updateCallback = listUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualStory)

        assertNotNull(differ.snapshot())
        assertEquals(dummyStories, differ.snapshot())
        assertEquals(dummyStories.size, differ.snapshot().size)
        assertEquals(dummyStories[0], differ.snapshot()[0])
    }

    @Test
    fun `when Get Story Empty Should Return No Data`() = runTest {
        val data: PagingData<ListStoryItem> = PagingData.from(emptyList())
        val expectedStory = MutableLiveData<PagingData<ListStoryItem>>()
        expectedStory.value = data
        `when`(repository.getStory()).thenReturn(expectedStory)
        val actualStory: PagingData<ListStoryItem> = mainViewModel.getStories().getOrAwaitValue()
        val differ = AsyncPagingDataDiffer(
            diffCallback = StoriesAdapter.DIFF_CALLBACK,
            updateCallback = listUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualStory)
        assertEquals(0, differ.snapshot().size)
    }

    private val listUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
}

class PagingSourceTest: PagingSource<Int, LiveData<List<ListStoryItem>>>() {
    companion object {
        fun snapshot(items: List<ListStoryItem>): PagingData<ListStoryItem> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<ListStoryItem>>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<ListStoryItem>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}