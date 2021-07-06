package com.example.loginapp.daos

import com.example.loginapp.models.Post
import com.example.loginapp.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.awaitAll

class PostDao {
    val db = FirebaseFirestore.getInstance()
    val postCollection = db.collection("posts")
    val auth = Firebase.auth

    fun addPost(text:String){
        GlobalScope.launch {
              val currentUserId = auth.currentUser!!.uid
              val userDao = UserDao()
              val user = userDao.getUserById(currentUserId).Deferred.await().toObject(User::class.java)!!

              val currentTime = System.currentTimeMillis()
              val post = Post(text,user,currentTime)
              postCollection.document().set(post)
        }

    }
}