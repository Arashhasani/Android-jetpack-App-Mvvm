package com.example.mvvm

import android.annotation.SuppressLint
import android.database.Observable
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.data.model.Post
import com.example.mvvm.ui.theme.BgColor
import com.example.mvvm.ui.theme.MvvmTheme
import com.example.mvvm.viewmodel.PostViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MvvmTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    Greeting("Android")
//                }


                Scaffold(
                    topBar = {
                             TopAppBar(modifier = Modifier
                                 .fillMaxWidth()
                                 .background(Color.White), backgroundColor = Color.White) {
                                 Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {

                                     IconButton(onClick = { /*TODO*/ }) {
                                         Icon(Icons.Filled.Menu, contentDescription = "", tint = Color.Black)

                                     }
                                     Text(text = "Title", color = Color.Black)
                                     IconButton(onClick = { /*TODO*/ }) {
                                         Icon(Icons.Filled.ArrowForward, contentDescription = "", tint = Color.Black)

                                     }
                                 }

                             }
                    },
                    backgroundColor = BgColor,
                    content = {
                        observePostsViewModel()
                    },
                )
            }
        }
    }


    @Composable
    private fun observePostsViewModel() {

        var postList by remember { mutableStateOf(emptyList<Post>()) }
        var loadingg by remember { mutableStateOf(true) }



        LaunchedEffect(key1 = Unit){
            val viewModel = ViewModelProvider(this@MainActivity).get(PostViewModel::class.java)
            viewModel.getAllPostsRequest()
            viewModel.postlist.observe(this@MainActivity) { posts ->
                postList = posts
            }


            viewModel.postlistError.observe(this@MainActivity) { isError ->
                isError?.let {
                    Log.e("3636", isError.toString())
                }

            }

            viewModel.loading.observe(this@MainActivity) { loading ->
                loadingg=loading
                Log.e("3636", loading.toString())
            }
        }
        if (loadingg){
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
            }
        }else{
            PostView(postList = postList)
        }



    }

    @Composable
    fun PostView(postList: List<Post>) {
        LazyColumn() {
            items(postList) { post ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .padding(8.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .clickable { },backgroundColor = Color.White, elevation = 14.dp) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        Text(text = post.title, color = Color.Black)
                        Text(text = post.body, fontSize = 10.sp)
                    }
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MvvmTheme {
        Greeting("Android")
    }
}