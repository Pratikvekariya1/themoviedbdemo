package com.themoviedbdemo.network.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.themoviedbdemo.models.responcemodel.Person
import com.themoviedbdemo.network.ApiInterface
import com.themoviedbdemo.utills.CustomException
import com.themoviedbdemo.utills.ExceptionType

class PersonPagingSource(
    private val query: String,
    private val api: ApiInterface,
) : PagingSource<Int, Person>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Person> {
        return try {
            val currentPage = params.key ?: 1
            val response = if(query.isEmpty()) {
                 api.getPopularPersons(
                    language = "en-US",
                    page = currentPage
                )
            }else{
                api.searchPersons(
                    query,
                    language = "en-US",
                    page = currentPage
                )
            }

            if (response.success == false) {
                LoadResult.Error(CustomException(ExceptionType.API_ERROR ,"Error ${response.status_code}: ${response.status_message}"))

            }else if (response.results != null) {
                if (response.results.isEmpty()) {
                 LoadResult.Error(CustomException(ExceptionType.NO_DATA_FOUND ,"No data found"))

                } else {
                    LoadResult.Page(
                        data = response.results,
                        prevKey = if (currentPage == 1) null else currentPage - 1,
                        nextKey = if (currentPage < (response.total_pages
                                ?: 0)
                        ) currentPage + 1 else null
                    )
                }
            }else{
                LoadResult.Error(CustomException(ExceptionType.UNKNOWN ,"Unexpected API Response"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Person>): Int? {
        return state.anchorPosition?.let { state.closestPageToPosition(it)?.prevKey?.plus(1) }
    }
}
