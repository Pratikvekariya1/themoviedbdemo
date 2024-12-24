package com.themoviedbdemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.themoviedbdemo.models.responcemodel.Person
import com.themoviedbdemo.network.ApiInterface
import com.themoviedbdemo.service.PersonPagingSource
import kotlinx.coroutines.flow.Flow

class PersonViewModel(private val api: ApiInterface) : ViewModel() {

    fun getPopularPersons(): Flow<PagingData<Person>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PersonPagingSource(api) }
        ).flow
    }
}
