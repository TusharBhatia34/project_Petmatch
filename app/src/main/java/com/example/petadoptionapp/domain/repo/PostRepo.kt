package com.example.petadoptionapp.domain.repo

import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.domain.model.Post
import com.example.petadoptionapp.domain.model.SavedPost
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow

interface PostRepo {
   suspend fun createPost (post: Post): Response<Boolean>
   suspend fun getPost(): Flow<List<Post>>

   suspend fun savePost(savedPost: SavedPost):Response<Boolean>

   suspend fun getSavedPost():Response<Boolean>

   suspend fun removeSavedPost(authorId:String, timeStamp: Timestamp, savedBy:String):Response<Boolean>

   suspend fun getMyPosts():Pair<Response<Boolean>,List<Post>>



}