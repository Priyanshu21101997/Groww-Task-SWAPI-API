package com.example.skeletonapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.skeletonapplication.repository.Repository
import javax.inject.Inject

class ListFragmentViewModelFactory @Inject constructor
    (private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListFragmentViewModel(repository) as T
    }
}