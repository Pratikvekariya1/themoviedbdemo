package com.themoviedbdemo.network.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.themoviedbdemo.network.ApiInterface
import com.themoviedbdemo.viewmodel.PersonViewModel

class PersonViewModelFactory(
    private val api: ApiInterface
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PersonViewModel(api) as T
    }
}