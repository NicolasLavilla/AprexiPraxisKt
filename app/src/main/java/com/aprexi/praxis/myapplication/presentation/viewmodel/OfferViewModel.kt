package com.aprexi.praxis.myapplication.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aprexi.praxis.myapplication.domain.usercase.DeleteFollowOfferUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetOfferUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetOffersUseCause
import com.aprexi.praxis.myapplication.domain.usercase.RequestOfferUseCause
import com.aprexi.praxis.myapplication.domain.usercase.FollowCompanyOfferUseCause
import com.aprexi.praxis.myapplication.domain.usercase.FollowOfferUseCause
import com.aprexi.praxis.myapplication.model.DeleteFollowOfferUser
import com.aprexi.praxis.myapplication.model.FollowOfferUser
import com.aprexi.praxis.myapplication.model.ListOffersResponse
import com.aprexi.praxis.myapplication.model.Offer
import com.aprexi.praxis.myapplication.model.RequestOfferUser
import com.aprexi.praxis.myapplication.model.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias OfferListState = ResourceState<ListOffersResponse>
typealias OfferDetailState = ResourceState<Offer>
typealias RequestOfferState = ResourceState<RequestOfferUser>
typealias FollowCompanyState = ResourceState<Void?>
typealias FollowOfferState = ResourceState<FollowOfferUser>
typealias DeleteFollowOfferState = ResourceState<DeleteFollowOfferUser>

class OfferViewModel(
    private val getOfferUseCause: GetOfferUseCause,
    private val getOffersUseCause: GetOffersUseCause,
    private val requestOfferUseCause: RequestOfferUseCause,
    private val followCompanyUseCause: FollowCompanyOfferUseCause,
    private val followOfferUseCause: FollowOfferUseCause,
    private val deleteFollowOfferUseCase: DeleteFollowOfferUseCause
): ViewModel() {

    private val _offerListLiveData = MutableLiveData<OfferListState>()

    private val _offerDetailLiveData = MutableLiveData<OfferDetailState>()

    private val _requestOfferLiveData = MutableLiveData<RequestOfferState>()

    private val _followCompanyLiveData = MutableLiveData<FollowCompanyState>()

    private val _followOfferLiveData = MutableLiveData<FollowOfferState>()

    private val _deleteFollowOfferLiveData = MutableLiveData<DeleteFollowOfferState>()

    fun getOfferListLiveData(): LiveData<OfferListState> {
        return _offerListLiveData
    }

    fun getOfferDetailLiveData(): LiveData<OfferDetailState> {
        return _offerDetailLiveData
    }

    fun setRequestOfferLiveData(): LiveData<RequestOfferState> {
        return _requestOfferLiveData
    }

    fun followCompanyLiveData(): LiveData<FollowCompanyState> {
        return _followCompanyLiveData
    }

    fun deleteFollowOfferLiveData(): LiveData<DeleteFollowOfferState> {
        return _deleteFollowOfferLiveData
    }

    fun followOfferLiveData(): LiveData<FollowOfferState> {
        return _followOfferLiveData
    }

    fun fetchOfferList(idUser: Int, token: String) {
        _offerListLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getOffersUseCause.execute(idUser = idUser, token = token)

                withContext(Dispatchers.Main) {
                    if (response.success){
                        _offerListLiveData.value = ResourceState.Success(response)
                    }else{
                        _offerListLiveData.value = ResourceState.SuccessFaild(response)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _offerListLiveData.value = ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun fetchOffer(idUser: Int ,idOffer: Int, token: String) {
        _offerDetailLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val offer = getOfferUseCause.execute(idUser = idUser, idOffer = idOffer, token = token)

                withContext(Dispatchers.Main) {
                    if (offer.success){
                        _offerDetailLiveData.value = ResourceState.Success(offer)
                    }else{
                        _offerDetailLiveData.value = ResourceState.SuccessFaild(offer)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _offerDetailLiveData.value = ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun setRequestOffer(idUser: Int ,idOffer: Int, token: String) {
        _requestOfferLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val requestOffer = requestOfferUseCause.execute(idUser,idOffer,token)

                withContext(Dispatchers.Main) {
                    if (requestOffer.success){
                        _requestOfferLiveData.value = ResourceState.Success(requestOffer)
                    }else{
                        _requestOfferLiveData.value = ResourceState.SuccessFaild(requestOffer)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _requestOfferLiveData.value = ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun followCompany(idCompanyOffer: Int) {
        _followCompanyLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                followCompanyUseCause.execute(idCompanyOffer)

                withContext(Dispatchers.Main) {
                    _followCompanyLiveData.value = ResourceState.Success(null)
                    //_saveCompanyLiveData.value = ResourceState.Offer()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _followCompanyLiveData.value = ResourceState.Error(e.localizedMessage.orEmpty())
                    //_saveCompanyLiveData.value = ResourceState.Offer()
                }
            }
        }
    }

    fun followOffer(idUser: Int,idOffer: Int,token: String) {
        _followOfferLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val saveOffer = followOfferUseCause.execute(idUser,idOffer,token)

                withContext(Dispatchers.Main) {
                    if (saveOffer.success){
                        _followOfferLiveData.value = ResourceState.Success(saveOffer)
                    }else{
                        _followOfferLiveData.value = ResourceState.SuccessFaild(saveOffer)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _followOfferLiveData.value = ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun deleteFollowOffer(idUser: Int,idOffer: Int,token: String) {
        _deleteFollowOfferLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val saveOffer = deleteFollowOfferUseCase.execute(idUser,idOffer,token)

                withContext(Dispatchers.Main) {
                    if (saveOffer.success){
                        _deleteFollowOfferLiveData.value = ResourceState.Success(saveOffer)
                    }else{
                        _deleteFollowOfferLiveData.value = ResourceState.SuccessFaild(saveOffer)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _deleteFollowOfferLiveData.value = ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

}