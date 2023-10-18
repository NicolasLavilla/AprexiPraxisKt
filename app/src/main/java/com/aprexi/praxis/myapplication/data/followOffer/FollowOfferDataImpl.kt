package com.aprexi.praxis.myapplication.data.followOffer

import com.aprexi.praxis.myapplication.data.followOffer.local.FollowOfferLocalImpl
import com.aprexi.praxis.myapplication.data.followOffer.remote.FollowOfferRemoteImpl
import com.aprexi.praxis.myapplication.domain.FollowOfferRepository
import com.aprexi.praxis.myapplication.model.ListOffersResponse
import com.aprexi.praxis.myapplication.model.Offer

class FollowOfferDataImpl(
    private val requestOfferLocalImpl: FollowOfferLocalImpl,
    private val requestOfferRemoteImpl: FollowOfferRemoteImpl
): FollowOfferRepository {

    override suspend fun getListFollowOffer( idUser: Int, forceRemote: Boolean, token: String): ListOffersResponse {
        val cachedOffersList = requestOfferLocalImpl.getFollowOffers()

        if (cachedOffersList != null && forceRemote) {
            return cachedOffersList
        } else {
            val remoteOfferList = requestOfferRemoteImpl.getListFollowOffer(idUser = idUser, token = token)
            saveFollowOffers(remoteOfferList)
            return remoteOfferList
        }
    }

    override fun saveFollowOffers(followOffers: ListOffersResponse) {
        requestOfferLocalImpl.saveFollowOffer(followOffers)
    }


}