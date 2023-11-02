package com.aprexi.praxis.myapplication.data.offer

import com.aprexi.praxis.myapplication.data.offer.local.OfferLocalImpl
import com.aprexi.praxis.myapplication.data.offer.remote.OfferRemoteImpl
import com.aprexi.praxis.myapplication.domain.OfferRepository
import com.aprexi.praxis.myapplication.model.DeleteFollowOfferUser
import com.aprexi.praxis.myapplication.model.FollowOfferUser
import com.aprexi.praxis.myapplication.model.ListOffersResponse
import com.aprexi.praxis.myapplication.model.Offer
import com.aprexi.praxis.myapplication.model.RequestOfferUser

class OfferDataImpl (
    private val offerLocalImpl: OfferLocalImpl,
    private val offerRemoteImpl: OfferRemoteImpl
) : OfferRepository {

    override suspend fun getOfferList(idUser: Int, forceRemote: Boolean, token: String): ListOffersResponse {
        val cachedOffersList = offerLocalImpl.getOffers()

        if (cachedOffersList != null && forceRemote) {
            return cachedOffersList
        } else {
            val remoteOfferList = offerRemoteImpl.getOfferList(idUser = idUser, token = token)
            saveOffers(remoteOfferList)
            return remoteOfferList
        }
    }

    override suspend fun getOffer(idUser: Int, idOffer: Int, token: String): Offer {
        return offerRemoteImpl.getOffer(idUser = idUser,idOffer = idOffer, token = token )
    }

    override fun saveOffers(offers: ListOffersResponse) {
        offerLocalImpl.saveOffer(offers)
    }

    override suspend fun setRequestOffer(idUser: Int, idOffer: Int, token: String): RequestOfferUser {
        return offerRemoteImpl.setRequestOffer(idUser = idUser,idOffer = idOffer, token = token )
    }

    override suspend fun followOffer(idUser: Int, idOffer: Int, token: String): FollowOfferUser {
        return offerRemoteImpl.setFollowOffer(idUser = idUser,idOffer = idOffer, token = token )
    }

    override suspend fun deleteFollowOffer(idUser: Int, idOffer: Int, token: String): DeleteFollowOfferUser {
        return offerRemoteImpl.deleteFollowOffer(idUser = idUser,idOffer = idOffer, token = token )
    }

    override fun followCompanyOffer(idCompanyOffer: Int) {
        //TO-DO
        //NO ESTÁ IMPLEMENTADO LA SOOLICUTUD
    }

    override suspend fun getOfferListCompany(idUser: Int, idCompany: Int, token: String): ListOffersResponse {
       return offerRemoteImpl.getOfferListCompany(idUser = idUser, idCompany = idCompany, token = token)
    }
}