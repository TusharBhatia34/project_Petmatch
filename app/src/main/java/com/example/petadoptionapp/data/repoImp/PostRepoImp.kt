package com.example.petadoptionapp.data.repoImp

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
            for((index, uri) in post.photos.withIndex()){
               val result =  uploadFile(post.authorId,post.timestamp.toString(),uri,index)
                if(result.first){

                    imageUrls.add(result.second)
                }
                else {
                    return Response.Failure(Exception("Something went wrong."))
                }
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


    override suspend fun editPost(post:Post,newImages:Boolean,numOfImagesBefore:Int): Response<Boolean> {

        try {
            var imageUrls = post.photos.toMutableList()
            if(newImages){
                if(post.photos.size<numOfImagesBefore){
                    for (index in numOfImagesBefore -1 downTo post.photos.size){
                        deleteFile(post.authorId,post.timestamp.toString(),index)
                    }
                }
                imageUrls = emptyList<String>().toMutableList()
                for((index,uri) in post.photos.withIndex()){

                    val result =  uploadFile(post.authorId,post.timestamp.toString(),uri,index)
                    if(result.first){
                        imageUrls.add(result.second)
                    }
                    else {
                        return Response.Failure(Exception("Something went wrong."))
                    }
                }
            }
            db.collection(Collections.POSTS)
                .document("${post.authorId}_${post.timestamp.toDate()}_${post.timestamp.toDate().time}")
                .set(post.copy(photos = imageUrls))
                .await()
            return Response.Success(true).also {
                editSavedPostFromEveryUser(post.copy(photos = imageUrls))
            }
        }
        catch (e:Exception){
            return Response.Failure(e)
        }

    }

    override suspend fun getSelectedCountryPosts(country: String): Flow<Pair<Response<Boolean>, List<Post>>> = callbackFlow {

        try{
            val allPost = mutableListOf<Post>()
            db.collection(Collections.POSTS)
                .whereEqualTo("country",country)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { snapshot->
                    for (document in snapshot){

                        val post = document.toObject<Post>()
                        if(post.authorId!=SharedComponents.currentUser!!.uid){

                            allPost.add(post)
                        }
                    }
                    trySend(Pair(Response.Success(true),allPost))

                }
                .addOnFailureListener{
                    throw Exception(it.message)
                }

        }
        catch (e:Exception){

            trySend(Pair(Response.Failure(e),emptyList()))
        }
        awaitClose {  }
    }


    override suspend fun deletePost(authorId: String, timeStamp: Timestamp,photosSize:Int):Response<Boolean> {
       try {
           db.collection(Collections.POSTS)
               .document("${authorId}_${timeStamp.toDate()}_${timeStamp.toDate().time}")
               .delete()
               .await()

           for (index in 0 until photosSize){
               deleteFile(authorId,timeStamp.toString(),index)
           }

       return Response.Success(true).also {
deleteSavedPostFromEveryUser(authorId,timeStamp)
       }
        }
        catch (e:Exception){
         return  Response.Failure(e)
        }
    }

    override suspend fun getPost(country:String): Flow<Pair<Response<Boolean>,List<Post>>> = callbackFlow {

        try{

            val allPost = mutableListOf<Post>()
    db.collection(Collections.POSTS)
    .whereEqualTo("country",country)
    .orderBy("timestamp", Query.Direction.DESCENDING)
    .get()
    .addOnSuccessListener { snapshot->
        for (document in snapshot){

            val post = document.toObject<Post>()
            if(post.authorId!=SharedComponents.currentUser!!.uid){
                allPost.add(post)
            }

        }
        db.collection(Collections.POSTS)
            .whereNotEqualTo("country",country)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener{
                for (document in it){

                    val post = document.toObject<Post>()
                    if(post.authorId!=SharedComponents.currentUser!!.uid){
                        allPost.add(post)
                    }

                }
                trySend(Pair(Response.Success(true),allPost))
            }
    }
    .addOnFailureListener{
        throw Exception(it.message)
    }

        }
        catch (e:Exception){

trySend(Pair(Response.Failure(e),emptyList()))
        }
        awaitClose {  }
    }

    override suspend fun savePost(savedPost: SavedPost): Response<Boolean> {

        try{
            db.collection(Collections.SAVED_POST)
                .document("${savedPost.savedBy}_${savedPost.authorId}_${savedPost.postTimestamp}")
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
        timeStamp: Timestamp,
        savedBy: String,
    ): Response<Boolean> {
        try {
         db.collection(Collections.SAVED_POST)
              .document("${savedBy}_${authorId}_${timeStamp}")
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
    override suspend fun getFilteredPosts(
        type: String,
        gender: String,
        breed: String,
        city:String,
        state:String,
        country:String
    ):Flow<Pair<Response<Boolean>,List<Post>>> = callbackFlow {
        try {
            val result = mutableListOf<Post>()
            var query:Query = db.collection(Collections.POSTS).whereNotEqualTo("authorId",SharedComponents.currentUser!!.uid)
            if(city!=""){
                query = query.whereEqualTo("city",city)
            }
          else if(type!=""){
                query = query.whereEqualTo("type",type)
            }
         else if(breed!=""){
                query = query.whereEqualTo("breed",breed)
            }
           else if(gender!=""){
                query = query.whereEqualTo("gender",gender)
            }



            query.orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener {
                if(it.isSuccessful){
                    for (doc in it.result.documents){
                        val post = doc.toObject<Post>()
                         if((type!=""&&type!=post!!.type)||(breed != "" && breed.lowercase() != post!!.breed.lowercase())||(gender != "" && gender != post!!.gender)){
                            continue
                        }
                        result.add(post!!)
                    }
                    if(state!=""){
                        val query2:Query = db.collection(Collections.POSTS).whereEqualTo("state",state).whereNotEqualTo("city",city)

                       query2.orderBy("timestamp", Query.Direction.DESCENDING)
                            .get()
                            .addOnCompleteListener {q2 ->

                            if(q2.isSuccessful){
                                for (doc in q2.result.documents){

                                    val post = doc.toObject<Post>()
                                    if((post?.authorId == SharedComponents.currentUser!!.uid)|| (type!=""&&type!=post!!.type)||(breed != "" && breed.lowercase() != post!!.breed.lowercase())||(gender != "" && gender != post!!.gender)){
                                        continue
                                    }
                                    result.add(post!!)
                                }
                                if(country!=""){

                                    val query3:Query = db.collection(Collections.POSTS).whereNotEqualTo("state",state).whereEqualTo("country",country)

                                    query3.orderBy("timestamp", Query.Direction.DESCENDING).get().addOnCompleteListener {q3 ->
                                        if(q3.isSuccessful){

                                            for (doc in q3.result.documents){

                                                val post = doc.toObject<Post>()
                                                if((post?.authorId == SharedComponents.currentUser!!.uid)||(type!=""&&type!=post!!.type)||(breed != "" && breed.lowercase() != post!!.breed.lowercase())||(gender != "" && gender != post!!.gender)){
                                                    continue
                                                }
                                                result.add(post!!)
                                            }

                                            trySend(Pair(Response.Success(true),result))
                                        }
                                        else{
                                            trySend(Pair(Response.Success(true),result))
                                        }
                                    }
                                }
                                else{
                                    trySend(Pair(Response.Success(true),result))
                                }
                            }
                            else{
                                trySend(Pair(Response.Success(true),result))
                            }
                        }
                    }
                    else{
                        trySend(Pair(Response.Success(true),result))
                    }
                }
                else{
                    trySend(Pair(Response.Failure(it.exception?:Exception("something is wrong.")), emptyList()))
                }
            }
        }
        catch (e:Exception){
            trySend(Pair(Response.Failure(e), emptyList()))
        }
        awaitClose{}
    }


    private suspend fun uploadFile(authorId:String,timeStamp:String,fileUrl:String,index:Int):Pair<Boolean,String>{
        try {
            val ref = storage.reference.child("images/${authorId}/[$index]_${authorId}_${timeStamp}")
            ref.putFile(fileUrl.toUri()).await()
            return Pair(true,ref.downloadUrl.await().toString())
        }
        catch (e:Exception){
            return Pair(false,"")
        }

    }

    private fun deleteFile(authorId: String,timeStamp:String,index: Int){
        try{
            val ref = storage.reference.child("images/${authorId}/[$index]_${authorId}_${timeStamp}")
            ref.delete()

        }
        catch (_:Exception){}

    }

 private fun deleteSavedPostFromEveryUser(authorId: String,postTimestamp: Timestamp){
db.collection(Collections.SAVED_POST)
    .whereEqualTo("authorId",authorId)
    .whereEqualTo("postTimestamp",postTimestamp)
    .get()
    .addOnCompleteListener {
        if(it.isSuccessful){
           for(doc in it.result.documents) {
               db.collection(Collections.SAVED_POST)
                   .document(doc.id)
                   .delete()

           }
        }
    }
    }

 private fun editSavedPostFromEveryUser(post:Post){
db.collection(Collections.SAVED_POST)
    .whereEqualTo("authorId",post.authorId)
    .whereEqualTo("postTimestamp",post.timestamp)
    .get()
    .addOnCompleteListener {
        if(it.isSuccessful){
           for(doc in it.result.documents) {
               db.collection(Collections.SAVED_POST)
                   .document(doc.id)
                   .update(mapOf(
                       "age" to post.bornOn,
                       "breed" to post.breed,
                       "description" to post.description,
                       "gender" to post.gender,
                       "healthInformation" to post.healthInformation,
                       "state" to post.state,
                       "photos" to post.photos,
                       "type" to post.type,
                       "name" to post.name,
                       "country" to post.country,
                       "city" to post.city
                   ))

           }
        }
    }
    }

}


