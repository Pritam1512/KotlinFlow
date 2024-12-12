package com.example.flowconcept.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flowconcept.model.CommentData
import com.example.flowconcept.service.APIService
import com.example.flowconcept.viewmodel.HandleProgress
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class CommentRepository(private val apiService: APIService, handleProgress: HandleProgress) {

    private val TAG = "CommentRepository"
    private lateinit var progress: HandleProgress

    init {
        Log.i(TAG,"init")
        progress = handleProgress
    }
    suspend fun getComment(id: Int) : Flow<CommentData>{

        val currentTime = System.currentTimeMillis()
        Log.i(TAG, "getComment")
        return flow {
            withContext(Dispatchers.Main){
                progress.showProgress()
            }
            val result = apiService.getComment(id)
            if(result.body != null){
                Log.i(TAG,"result :: ${result.body}")
            }else{
                Log.i(TAG, "result is null")
            }
            emit(result)
            val dataReceivedTime = System.currentTimeMillis()
            Log.i(TAG, "time taken to fetch data :: ${dataReceivedTime - currentTime}")
            withContext(Dispatchers.Main){
                progress.hideProgress()
            }
        }.flowOn(Dispatchers.IO)

    }
}