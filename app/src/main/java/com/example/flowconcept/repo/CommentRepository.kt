package com.example.flowconcept.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flowconcept.model.CommentData
import com.example.flowconcept.service.APIService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CommentRepository(private val apiService: APIService) {

    private val TAG = "CommentRepository"
    suspend fun getComment(id: Int) :Flow<CommentData>{

        Log.i(TAG, "getComment")
        return flow{
            val result = apiService.getComment(id)
            if(result.body != null){
                Log.i(TAG,"result :: ${result.body}")
            }else{
                Log.i(TAG, "result is null")
            }
            emit(result)
        }.flowOn(Dispatchers.IO)

    }
}