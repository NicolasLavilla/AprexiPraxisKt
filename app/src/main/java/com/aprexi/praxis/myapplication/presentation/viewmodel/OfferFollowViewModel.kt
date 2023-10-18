package com.aprexi.praxis.myapplication.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aprexi.praxis.myapplication.domain.usercase.GetFollowOffersUseCause
import com.aprexi.praxis.myapplication.model.ListOffersResponse
import com.aprexi.praxis.myapplication.model.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias FollowOfferListState = ResourceState<ListOffersResponse>
class OfferFollowViewModel(
    private val getFollowOfferUseCause: GetFollowOffersUseCause
): ViewModel() {

    private val _followOfferListLiveData = MutableLiveData<FollowOfferListState>()

    fun getFollowOfferListLiveData(): LiveData<OfferListState> {
        return _followOfferListLiveData
    }

    fun fetchFollowOfferList(idUser: Int, token: String) {
        _followOfferListLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getFollowOfferUseCause.execute(idUser = idUser, token = token)

                withContext(Dispatchers.Main) {
                    if (response.success) {
                        _followOfferListLiveData.value = ResourceState.Success(response)
                    } else {
                        _followOfferListLiveData.value = ResourceState.SuccessFaild(response)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _followOfferListLiveData.value = ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

}