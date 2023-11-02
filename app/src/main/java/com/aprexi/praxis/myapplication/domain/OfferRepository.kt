package com.aprexi.praxis.myapplication.domain

import com.aprexi.praxis.myapplication.model.DeleteFollowOfferUser
import com.aprexi.praxis.myapplication.model.FollowOfferUser
import com.aprexi.praxis.myapplication.model.ListOffersResponse
import com.aprexi.praxis.myapplication.model.Offer
import com.aprexi.praxis.myapplication.model.RequestOfferUser

interface OfferRepository {

    suspend fun getOfferList(idUser: Int, forceRemote: Boolean, token: String): ListOffersResponse

    suspend fun getOffer(idUser: Int, idOffer: Int, token: String): Offer

    fun saveOffers(offers: ListOffersResponse)

    suspend fun setRequestOffer(idUser: Int,idOffer: Int, token:String): RequestOfferUser

    suspend fun followOffer(idUser: Int, idOffer: Int, token: String): FollowOfferUser

    suspend fun deleteFollowOffer(idUser: Int, idOffer: Int, token: String): DeleteFollowOfferUser

    fun followCompanyOffer(idCompanyOffer: Int)

    suspend fun getOfferListCompany(idUser: Int, idCompany: Int, token: String): ListOffersResponse

}