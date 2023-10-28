package com.aprexi.praxis.myapplication.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aprexi.praxis.myapplication.domain.usercase.GetDetailRequestOfferListUseCause
import com.aprexi.praxis.myapplication.model.ListDetailRequestOffer
import com.aprexi.praxis.myapplication.model.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


typealias DetailRequestOfferListState = ResourceState<ListDetailRequestOffer>
class DetailRequestOfferViewModel(
    private val getDetailRequestOfferListUseCause: GetDetailRequestOfferListUseCause
): ViewModel() {
    private val _detailRequestOfferListLiveData = MutableLiveData<DetailRequestOfferListState>()

    fun getDetailRequestOfferListLiveData(): LiveData<DetailRequestOfferListState> {
        return _detailRequestOfferListLiveData
    }

    fun fetchDetailRequestOfferList(idUser: Int,idOffer:Int, token: String) {
        _detailRequestOfferListLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val detailRequestOfferList = getDetailRequestOfferListUseCause.execute(idUser= idUser, idOffer = idOffer, token= token)

                withContext(Dispatchers.Main) {
                    if (detailRequestOfferList.success){
                        _detailRequestOfferListLiveData.value = ResourceState.Success(detailRequestOfferList)
                    }else{
                        _detailRequestOfferListLiveData.value = ResourceState.SuccessFaild(detailRequestOfferList)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _detailRequestOfferListLiveData.value = ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }
}