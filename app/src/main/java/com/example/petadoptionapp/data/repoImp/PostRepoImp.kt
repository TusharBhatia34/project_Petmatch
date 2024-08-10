package com.example.petadoptionapp.data.repoImp

import android.util.Log
import androidx.core.net.toUri
import com.example.petadoptionapp.data.common.Collections
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.data.common.SharedComponents
import com.example.petadoptionapp.domain.model.Post
import com.example.petadoptionapp.domain.model.SavedPost
import com.example.petadoptionapp.domain.repo.PostRepo
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.storage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class PostRepoImp: PostRepo {
  private  val db = Firebase.firestore
    private val storage = Firebase.storage
    override suspend fun createPost(post: Post): Response<Boolean> {
        try{
            val imageUrls = mutableListOf<String>()

            for(uri in post.photos){
                imageUrls.add(uploadFile(uri))
            }
            db.collection(Collections.POSTS)
                .document("${post.authorId}_${post.timestamp.toDate()}_${post.timestamp.toDate().time}")
                .set(post.copy(photos = imageUrls))
                .await()
            return Response.Success(true)
        }
        catch(e:Exception){
            return Response.Failure(e)
        }

    }

    override suspend fun getPost(): Flow<List<Post>> = callbackFlow {

        try{
            val allPost = mutableListOf<Post>()
db.collection(Collections.POSTS)
    .orderBy("timestamp",Query.Direction.DESCENDING)
    .get()
    .addOnSuccessListener { snapshot->
        for (document in snapshot){
            val post = document.toObject<Post>()
            allPost.add(post)
        }


        trySend(allPost)
    }

            trySend(emptyList())
        }
        catch (e:Exception){
            Log.d("",e.toString())
        }
        awaitClose {  }
    }

    override suspend fun savePost(savedPost: SavedPost): Response<Boolean> {

        try{
            db.collection(Collections.SAVED_POST)
                .document("${savedPost.authorId}_${savedPost.savedBy}_${savedPost.postTimestamp}")
                .set(savedPost)
                .await()
            return Response.Success(true)
        }
        catch (e:Exception){
           return Response.Failure(e)
        }
    }

    override suspend fun getSavedPost():Response<Boolean> {
        try {
            val savedList  = mutableListOf<SavedPost>()
            val result =  db.collection(Collections.SAVED_POST)
                .whereEqualTo("savedBy",SharedComponents.currentUser!!.uid)
                .get()
                .await()
                for (document in result){
                    val savedPost = document.toObject<SavedPost>()
                    savedList.add(savedPost)
                }
                SharedComponents.savedList = savedList


            return Response.Success(true)
        }
        catch(e:Exception){
         return Response.Failure(e)
        }
    }

    override suspend fun removeSavedPost(
        authorId: String,
        postTimestamp: Timestamp,
        savedBy: String,
    ): Response<Boolean> {
        try {



         db.collection(Collections.SAVED_POST)
              .document("${authorId}_${savedBy}_${postTimestamp}")
              .delete()
              .await()


            return Response.Success(true)
        }
        catch (e:Exception){
            return Response.Failure(e)
        }
    }

    override suspend fun getMyPosts(): Pair<Response<Boolean>, List<Post>> {

        try {

            val myPostList = mutableListOf<Post>()
            val result = db.collection(Collections.POSTS)
                .whereEqualTo("authorId",SharedComponents.currentUser!!.uid)
                .get()
                .await()
            for(document in result){
                val post = document.toObject<Post>()
                myPostList.add(post)
            }
            return Pair(Response.Success(true),myPostList)
        }
        catch (e:Exception){
            return Pair(Response.Failure(e), emptyList())
        }
    }


    private suspend fun uploadFile(fileUrl:String):String{
        val ref = storage.reference.child("posts/${fileUrl}")
        ref.putFile(fileUrl.toUri()).await()
        return ref.downloadUrl.await().toString()
    }

}


