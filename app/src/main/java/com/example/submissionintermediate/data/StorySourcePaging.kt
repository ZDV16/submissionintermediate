package com.example.submissionintermediate.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.submissionintermediate.api.ApiService
import com.example.submissionintermediate.api.ListStoryItem
import com.example.submissionintermediate.settings.UserPreferences
import kotlinx.coroutines.flow.first

class StorySourcePaging(
    private val apiService: ApiService,
    private val pref: UserPreferences
) : PagingSource<Int, ListStoryItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val token = "Bearer ${pref.getUser().first().token}"
            val responseData = apiService.getStories(token, page, params.loadSize).listStory
            LoadResult.Page(
                data = responseData,
                prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                nextKey = if (responseData.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}


