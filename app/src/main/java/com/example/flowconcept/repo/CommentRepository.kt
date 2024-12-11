package com.example.flowconcept.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flowconcept.model.CommentData
import com.example.flowconcept.service.APIService

class CommentRepository(private val apiService: APIService) {

    private val TAG = "CommentRepository"
    private val commentLiveData = MutableLiveData<CommentData>()

    val _commentLiveData: LiveData<CommentData>
        get() = commentLiveData

    suspend fun getComment(id: Int){
        val result = apiService.getComment(id)
        Log.i(TAG,result.toString())
        if(result.body() !=null){
            commentLiveData.postValue(result.body())
            Log.i(TAG,"result :: ${result.body()}")
        }else{
            Log.i(TAG, "result is null")
        }
    }
}