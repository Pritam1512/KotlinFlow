package com.example.flowconcept

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.flowconcept.databinding.ActivityMainBinding
import com.example.flowconcept.repo.CommentRepository
import com.example.flowconcept.service.APIService
import com.example.flowconcept.service.RetrofitHelper
import com.example.flowconcept.viewmodel.MainViewModel
import com.example.flowconcept.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    lateinit var mainViewBinding: ActivityMainBinding

    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainViewBinding.root)


        Log.i(TAG,"OnCreate")
        val commentService = RetrofitHelper.getInstance().create(APIService::class.java)
        val repository = CommentRepository(commentService)

        mainViewModel = ViewModelProvider(this,MainViewModelFactory(repository)).get(MainViewModel::class.java)

        setUpObserver()
        setUpListeners()

    }

    private fun setUpListeners() {
        mainViewBinding.button.setOnClickListener{
            if(mainViewBinding.editTextText.text.isNullOrEmpty()){
                Toast.makeText(this, "Query Can't be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener;
            }
            val page = mainViewBinding.editTextText.text
            val pageNo = Integer.parseInt(page.toString());

            Log.i(TAG,"page :: $pageNo")
            mainViewModel.makeApiCall(pageNo)
        }
    }

    private fun setUpObserver() {
        mainViewModel.comment.observe(this){

            val name = mainViewModel.comment.value?.name
            val email = mainViewModel.comment.value?.email
            val body = mainViewModel.comment.value?.body


            mainViewBinding.textViewName.text = name
            mainViewBinding.textViewEmail.text = email
            mainViewBinding.textViewBody.text = body
        }
    }
}