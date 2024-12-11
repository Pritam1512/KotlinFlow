package com.example.flowconcept.viewmodel

import CommentApiState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowconcept.model.CommentData
import com.example.flowconcept.repo.CommentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CommentRepository) : ViewModel() {


    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getComment(1)
        }
    }
    val comment : LiveData<CommentData>
        get() = repository._commentLiveData

    fun makeApiCall(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getComment(id)
        }
    }
}