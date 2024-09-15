package com.example.petadoptionapp.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.domain.model.Post
import com.example.petadoptionapp.domain.usecases.post.DeletePostUseCase
import com.example.petadoptionapp.domain.usecases.post.EditPostUseCase
import com.example.petadoptionapp.domain.usecases.post.GetMyPostsUseCase
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPostViewModel @Inject constructor(
    private val getMyPostsUseCase: GetMyPostsUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    private val editPostUseCase: EditPostUseCase
) : ViewModel(){


    private var _getMyPostsResponse = MutableStateFlow<Response<Boolean>>(Response.Loading)
    var getMyPostsResponse = _getMyPostsResponse.asStateFlow()
    private var _getMyPostsList = MutableStateFlow(emptyList<Post>())
    var getMyPostsList = _getMyPostsList.asStateFlow()

    private var _deletePostResponse = MutableStateFlow<Response<Boolean>>(Response.Loading)
    var deletePostResponse = _deletePostResponse.asStateFlow()

    private var _editPostResponse = MutableStateFlow<Response<Boolean>>(Response.Loading)
    var editPostResponse = _editPostResponse.asStateFlow()

    suspend  fun getMyPosts(){
        _getMyPostsResponse.value = Response.Loading
        viewModelScope.launch {
            val result =getMyPostsUseCase.invoke()
            _getMyPostsList.value = result.second
            _getMyPostsResponse.value = result.first
        }

    }
 fun editPost(post:Post,newImages:Boolean,numOfImagesBefore: Int){
    viewModelScope.launch {
        _editPostResponse.value = editPostUseCase.invoke(post, newImages,numOfImagesBefore)
    }

}
     fun deletePost(authorId:String,timestamp:Timestamp,photosSize:Int){
        viewModelScope.launch {
            _deletePostResponse.value = deletePostUseCase.invoke(authorId, timestamp,photosSize)
        }
    }

    fun resetValue(){
        _deletePostResponse.value = Response.Loading
        _editPostResponse.value = Response.Loading
    }


}