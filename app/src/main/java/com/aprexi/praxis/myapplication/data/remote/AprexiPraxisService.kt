package com.aprexi.praxis.myapplication.data.remote

import com.aprexi.praxis.myapplication.model.CheckToken
import com.aprexi.praxis.myapplication.model.DeleteFollowOfferUser
import com.aprexi.praxis.myapplication.model.FollowOfferUser
import com.aprexi.praxis.myapplication.model.ListDetailRequestOffer
import com.aprexi.praxis.myapplication.model.ListOffersResponse
import com.aprexi.praxis.myapplication.model.ListRequestOffer
import com.aprexi.praxis.myapplication.model.Login
import com.aprexi.praxis.myapplication.model.Offer
import com.aprexi.praxis.myapplication.model.RequestOfferUser
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AprexiPraxisService {

    @GET("ListOfferCompany.php")
    suspend fun getOffersList(@Query("idUser") idUser: Int, @Query("token") token: String): ListOffersResponse

    @GET("OfferCompany.php")
    suspend fun getOffer(@Query("idUser") idUser: Int, @Query("idOffer") idOffer: Int, @Query("token") token: String): Offer

    @GET("CheckToken.php")
    suspend fun getCheckToken(@Query("token") token: String): CheckToken

    @GET("LoginUser.php")
    suspend fun getLogin(@Query("email") email: String, @Query("password") password: String): Login

    @POST("RequestOfferUser.php")
    suspend fun setRequestOffer(@Query("idUser") idUser: Int, @Query("idOffer") idOffer: Int, @Query("token") token: String): RequestOfferUser

    @POST("FollowOfferUser.php")
    suspend fun setFollowOffer(@Query("idUser") idUser: Int, @Query("idOffer") idOffer: Int, @Query("token") token: String): FollowOfferUser

    @DELETE("DeleteFollowOfferUser.php")
    suspend fun deleteFollowOffer(@Query("idUser") idUser: Int, @Query("idOffer") idOffer: Int, @Query("token") token: String): DeleteFollowOfferUser

    @GET("ListRequestOffer.php")
    suspend fun getRequestOfferList(@Query("idUser") idUser: Int, @Query("token") token: String): ListRequestOffer

    @GET("ListFollowtOffer.php")
    suspend fun getFollowOfferList(@Query("idUser") idUser: Int, @Query("token") token: String): ListOffersResponse

    @GET("ListDetailRequestOffer.php")
    suspend fun getDetailRequestOfferList(@Query("idUser") idUser: Int,@Query("idOffer") idOffer: Int, @Query("token") token: String): ListDetailRequestOffer

}