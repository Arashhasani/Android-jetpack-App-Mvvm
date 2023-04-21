package com.example.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm.data.model.Post
import com.example.mvvm.data.remote.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class PostViewModel:ViewModel() {


    val postlist=MutableLiveData<List<Post>>()
    val postlistError=MutableLiveData<String?>()
    val loading=MutableLiveData<Boolean>()

    fun getAllPostsRequest(){
        loading.value=true
        CoroutineScope(Dispatchers.IO).launch {
            val response=ApiClient.api.getAllPosts()
            withContext(Dispatchers.Main){
                loading.value=false
                if (response.isSuccessful && response.body()!=null){
                    response.body()?.let {allposts->
                        postlist.value=allposts
                        postlistError.value=null
                        loading.value=false

                    }
                }else{
                    postlistError.value=response.message();
                    loading.value=false

                }
            }

        }


    }

}