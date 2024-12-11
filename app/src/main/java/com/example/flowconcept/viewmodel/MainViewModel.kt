package com.example.flowconcept.viewmodel

import CommentApiState
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowconcept.model.CommentData
import com.example.flowconcept.repo.CommentRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CommentRepository) : ViewModel() {

    private val TAG = "MainViewModel"

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        Log.i(TAG,"Error Occurred ${throwable.localizedMessage}")
        throwable.printStackTrace()
    }

    init {
        Log.i(TAG,"init")


        // unless we catch the emitted data,
        // flow will not produce the data (i.e; It will not make network call)

        // once we add .collect, now it will make call for the first time
        // and then it will keep on collecting the data based on api call


        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            repository.getComment(1)
                .catch {
                    e -> Log.e(TAG, "error occurred ${e.localizedMessage}")
                }
                .collect{
                    Log.d("MainViewModel", "data received $it")  // this is the data from api call
                    comment.postValue(it)
                }
        }
    }

    // private live data to store data from api call
    private val comment = MutableLiveData<CommentData>()

    // public live data for view to observe changes in data
    val _comment : LiveData<CommentData>
        get() = comment


    fun makeApiCall(id: Int) {

        // using flow to collect data from api call and then we can use it as per our requirement
        // data returned from getCommit() --> Flow<CommentData>
        // we can catch the error in flow using catch block and then handle it
        // we can collect the data using .collect method of flow
        // here we are just logging the error but we can do more with it like showing error message to user etc.


        viewModelScope.launch(Dispatchers.IO) {
            repository.getComment(id)
                .catch {
                    e -> Log.e("MainViewModel", "error occurred ${e.localizedMessage}")
                }
                .collect{
                    Log.d("MainViewModel", "data received $it")  // this is the data from api call
                    comment.postValue(it)  // updating live data with new value so that view can observe it and update UI accordingly
                }
        }
    }
}