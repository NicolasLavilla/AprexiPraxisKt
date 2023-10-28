package com.aprexi.praxis.myapplication.data.requestOffer

import com.aprexi.praxis.myapplication.data.requestOffer.local.RequestOfferLocalImpl
import com.aprexi.praxis.myapplication.data.requestOffer.remote.RequestOfferRemoteImpl
import com.aprexi.praxis.myapplication.domain.OfferRepository
import com.aprexi.praxis.myapplication.domain.RequestOfferRepository
import com.aprexi.praxis.myapplication.model.ListDetailRequestOffer
import com.aprexi.praxis.myapplication.model.ListOffersResponse
import com.aprexi.praxis.myapplication.model.ListRequestOffer

class RequestOfferDataImpl(
    private val requestOfferLocalImpl: RequestOfferLocalImpl,
    private val requestOfferRemoteImpl: RequestOfferRemoteImpl
): RequestOfferRepository {

    override suspend fun getRequestOfferList(idUser: Int, forceRemote: Boolean, token: String): ListRequestOffer {
        val cachedRequestOffersList = requestOfferLocalImpl.getRequestOffers()

        if (cachedRequestOffersList != null && forceRemote) {
            return cachedRequestOffersList
        } else {
            val remoteRequestOfferList = requestOfferRemoteImpl.getRequestOfferList(idUser = idUser, token = token)
            saveRequestOffers(remoteRequestOfferList)
            return remoteRequestOfferList
        }
    }

    override fun saveRequestOffers(requestOffers: ListRequestOffer) {
        requestOfferLocalImpl.saveRequestOffer(requestOffers)
    }

    override suspend fun getDetailRequestOfferList(
        idUser: Int,
        idOffer: Int,
        forceRemote: Boolean,
        token: String
    ): ListDetailRequestOffer {
        return requestOfferRemoteImpl.getDetailRequestOfferList(idUser,idOffer,token)
    }
}