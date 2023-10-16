package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.RequestOfferRepository
import com.aprexi.praxis.myapplication.model.ListRequestOffer

class GetRequestOfferListUseCause(
    private val requestOfferRepository: RequestOfferRepository
) {

    suspend fun execute(idUser: Int ,forceRemote: Boolean = false, token: String): ListRequestOffer{
        return  requestOfferRepository.getRequestOfferList(idUser , forceRemote,token)
    }
}