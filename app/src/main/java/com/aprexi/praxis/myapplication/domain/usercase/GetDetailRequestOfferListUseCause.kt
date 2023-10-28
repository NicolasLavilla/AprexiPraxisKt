package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.RequestOfferRepository
import com.aprexi.praxis.myapplication.model.ListDetailRequestOffer

class GetDetailRequestOfferListUseCause (
    private val requestOfferRepository: RequestOfferRepository
) {
    suspend fun execute(idUser: Int,idOffer: Int ,forceRemote: Boolean = false, token: String): ListDetailRequestOffer {
        return  requestOfferRepository.getDetailRequestOfferList(idUser, idOffer , forceRemote,token)
    }
}