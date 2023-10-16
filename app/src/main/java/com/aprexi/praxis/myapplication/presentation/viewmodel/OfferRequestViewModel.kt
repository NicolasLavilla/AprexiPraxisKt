package com.aprexi.praxis.myapplication.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aprexi.praxis.myapplication.domain.usercase.GetRequestOfferListUseCause
import com.aprexi.praxis.myapplication.model.ListRequestOffer
import com.aprexi.praxis.myapplication.model.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias RequestOfferListState = ResourceState<ListRequestOffer>
class OfferRequestViewModel(
    private val getRequestOfferListUseCause: GetRequestOfferListUseCause
): ViewModel() {
    private val _requestOfferListLiveData = MutableLiveData<RequestOfferListState>()

    fun getRequestOfferListLiveData(): LiveData<RequestOfferListState> {
        return _requestOfferListLiveData
    }

    fun fetchRequestOfferList(idUser: Int, token: String) {
        _requestOfferListLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val requestOfferList = getRequestOfferListUseCause.execute(idUser= idUser,token= token)

                withContext(Dispatchers.Main) {
                    if (requestOfferList.success){
                        _requestOfferListLiveData.value = ResourceState.Success(requestOfferList)
                    }else{
                        _requestOfferListLiveData.value = ResourceState.SuccessFaild(requestOfferList)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _requestOfferListLiveData.value = ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }
}
