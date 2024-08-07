package com.example.petadoptionapp.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.domain.model.Post
import com.example.petadoptionapp.domain.usecases.post.GetMyPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPostViewModel @Inject constructor(
    private val getMyPostsUseCase: GetMyPostsUseCase
) : ViewModel(){


    private var _getMyPostsResponse = MutableStateFlow<Response<Boolean>>(Response.Loading)
    var getMyPostsResponse = _getMyPostsResponse.asStateFlow()
    private var _getMyPostsList = MutableStateFlow(emptyList<Post>())
    var getMyPostsList = _getMyPostsList.asStateFlow()


    suspend  fun getMyPosts(){
        _getMyPostsResponse.value = Response.Loading
        viewModelScope.launch {
            val result =getMyPostsUseCase.invoke()
            _getMyPostsList.value = result.second
            _getMyPostsResponse.value = result.first
        }

    }


}