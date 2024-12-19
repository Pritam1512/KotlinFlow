package com.example.flowconcept

import android.util.Log
import com.example.flowconcept.model.CommentData
import javax.inject.Inject

class DatabaseManager @Inject constructor() {

    fun saveCommentToDatabase(commentData: CommentData){
        Log.i(TAG,"Saving comment to database ")
        Log.i(TAG,"Data :: $commentData")
    }

    companion object{
        private const val TAG = "DatabaseManager"
    }
}