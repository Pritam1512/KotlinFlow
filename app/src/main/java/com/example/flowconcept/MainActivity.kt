package com.example.flowconcept

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.flowconcept.databinding.ActivityMainBinding
import com.example.flowconcept.di.DaggerNetworkComponent
import com.example.flowconcept.di.NetworkComponent
import com.example.flowconcept.repo.CommentRepository
import com.example.flowconcept.service.APIService
import com.example.flowconcept.service.RetrofitHelper
import com.example.flowconcept.viewmodel.HandleProgress
import com.example.flowconcept.viewmodel.MainViewModel
import com.example.flowconcept.viewmodel.MainViewModelFactory
import retrofit2.Retrofit
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HandleProgress {
    lateinit var mainViewModel: MainViewModel
    lateinit var mainViewBinding: ActivityMainBinding

    private val TAG = "MainActivity"

    @Inject
    lateinit var retrofit : Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainViewBinding.root)

        DaggerNetworkComponent.builder().build().inject(this)
        Log.i(TAG,"OnCreate")

        //val retrofit = RetrofitHelper.getInstance()
        val commentService = retrofit.create(APIService::class.java)
        Log.i(TAG,"$retrofit")
        val repository = CommentRepository(commentService,this)

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
        mainViewModel._comment.observe(this){

            val name = mainViewModel._comment.value?.name
            val email = mainViewModel._comment.value?.email
            val body = mainViewModel._comment.value?.body


            mainViewBinding.textViewName.text = name
            mainViewBinding.textViewEmail.text = email
            mainViewBinding.textViewBody.text = body
        }
    }
    override fun showProgress() {
        mainViewBinding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        mainViewBinding.progressBar.visibility = View.INVISIBLE
    }
}