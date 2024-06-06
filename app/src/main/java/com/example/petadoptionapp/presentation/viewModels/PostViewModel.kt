package com.example.petadoptionapp.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.domain.model.Post
import com.example.petadoptionapp.domain.model.SavedPost
import com.example.petadoptionapp.domain.usecases.post.GetPostUseCase
import com.example.petadoptionapp.domain.usecases.post.PostCreationUseCase
import com.example.petadoptionapp.domain.usecases.savedPost.GetSavedPostUseCase
import com.example.petadoptionapp.domain.usecases.savedPost.RemoveSavedPostUseCase
import com.example.petadoptionapp.domain.usecases.savedPost.SavePostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postCreationUseCase: PostCreationUseCase,
    private val getPostUseCase: GetPostUseCase,
    private val savePostUseCase: SavePostUseCase,
    private val getSavedPostUseCase: GetSavedPostUseCase,
    private val removeSavedPostUseCase: RemoveSavedPostUseCase,
):ViewModel() {

    private val _post = MutableStateFlow<List<Post>>(emptyList())
    val post = _post.asStateFlow()
    private val _createPostResponse = MutableStateFlow<Response<Boolean>>(Response.Success(false))
    val createPostResponse = _createPostResponse.asStateFlow()
    private val _savePostResponse = MutableStateFlow<Response<Boolean>>(Response.Loading)
    val savePostResponse = _savePostResponse.asStateFlow()
    private val _removeSavedPostResponse = MutableStateFlow<Response<Boolean>>(Response.Loading)
    val removeSavedPostResponse = _removeSavedPostResponse.asStateFlow()

    init {
        getPost()
    }
    fun createPost(post: Post){
        viewModelScope.launch {
          _createPostResponse.value =  postCreationUseCase.invoke(post)
        }
        _createPostResponse.value  = Response.Loading
    }

    fun getPost(){
        viewModelScope.launch(Dispatchers.IO) {
            getPostUseCase.invoke().collect{
                _post.value = it
            }
        }
    }
    fun savePost(savedPost: SavedPost){
        viewModelScope.launch {
          _savePostResponse.value =  savePostUseCase.invoke(savedPost)
        }
    }

    fun getSavedPost(){
        viewModelScope.launch {
getSavedPostUseCase.invoke()
        }
    }

    fun removeSavedPost(authorId:String,savedBy:String,timestamp: String){
        viewModelScope.launch {
          _removeSavedPostResponse.value=  removeSavedPostUseCase.invoke(authorId = authorId, savedBy = savedBy, timeStamp = timestamp)
        }
    }
    fun resetValue(){
        _createPostResponse.value = Response.Success(false)
    }
}