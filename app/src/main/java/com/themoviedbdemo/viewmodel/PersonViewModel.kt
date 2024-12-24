package com.themoviedbdemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.themoviedbdemo.models.responcemodel.Person
import com.themoviedbdemo.network.ApiInterface
import com.themoviedbdemo.network.pagingsource.PersonPagingSource
import kotlinx.coroutines.flow.Flow

class PersonViewModel(private val api: ApiInterface) : ViewModel() {

    fun getPopularPersons(query: String): Flow<PagingData<Person>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = PAGE_SIZE / 4
            ),
            pagingSourceFactory = { PersonPagingSource(query,api) }
        ).flow
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}
