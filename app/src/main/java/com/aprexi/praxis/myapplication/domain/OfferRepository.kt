package com.aprexi.praxis.myapplication.domain

import com.aprexi.praxis.myapplication.model.DeleteFollowOfferUser
import com.aprexi.praxis.myapplication.model.FollowOfferUser
import com.aprexi.praxis.myapplication.model.ListOffersResponse
import com.aprexi.praxis.myapplication.model.Offer
import com.aprexi.praxis.myapplication.model.RequestOfferUser

interface OfferRepository {

    suspend fun getOffers(idUser: Int, forceRemote: Boolean, token: String): ListOffersResponse

    suspend fun getOffer(idUser: Int, idOffer: Int, token: String): Offer

    fun saveOffers(offers: ListOffersResponse)             //GUARDAR EN LOCAL LISTADO DE OFERTAS

    suspend fun setRequestOffer(idUser: Int,idOffer: Int, token:String): RequestOfferUser               //INSCRIBIRSE EN OFERTA

    suspend fun followOffer(idUser: Int, idOffer: Int, token: String): FollowOfferUser                   //GUARDAR/SEGUIR OFERTA

    suspend fun deleteFollowOffer(idUser: Int, idOffer: Int, token: String): DeleteFollowOfferUser

    fun followCompanyOffer(idCompanyOffer: Int)     //GUARDAR/SEGUIR EMPRESA

}