package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.OfferRepository
import com.aprexi.praxis.myapplication.model.ListOffersResponse

class GetOfferListUseCause(
    private val offerRepository: OfferRepository
) {

    suspend fun execute(idUser: Int ,forceRemote: Boolean = false, token: String): ListOffersResponse {
        return offerRepository.getOfferList(idUser , forceRemote,token)
    }
}