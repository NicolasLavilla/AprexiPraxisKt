package com.aprexi.praxis.myapplication.data.remote

import com.aprexi.praxis.myapplication.model.CheckToken
import com.aprexi.praxis.myapplication.model.Company
import com.aprexi.praxis.myapplication.model.DeleteExperienceJobUser
import com.aprexi.praxis.myapplication.model.DeleteFollowOfferUser
import com.aprexi.praxis.myapplication.model.DeleteLanguagesUser
import com.aprexi.praxis.myapplication.model.DeleteProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.DeleteStudiesUser
import com.aprexi.praxis.myapplication.model.ExperienceJobUser
import com.aprexi.praxis.myapplication.model.FollowOfferUser
import com.aprexi.praxis.myapplication.model.InsertExperienceJobUser
import com.aprexi.praxis.myapplication.model.InsertLanguagesUser
import com.aprexi.praxis.myapplication.model.InsertProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.InsertStudiesUser
import com.aprexi.praxis.myapplication.model.LanguagesUser
import com.aprexi.praxis.myapplication.model.ListBasicCompany
import com.aprexi.praxis.myapplication.model.ListBasicLanguages
import com.aprexi.praxis.myapplication.model.ListCategory
import com.aprexi.praxis.myapplication.model.ListDetailRequestOffer
import com.aprexi.praxis.myapplication.model.ListExperience
import com.aprexi.praxis.myapplication.model.ListExperienceJobUser
import com.aprexi.praxis.myapplication.model.ListLanguagesUser
import com.aprexi.praxis.myapplication.model.ListLevelJob
import com.aprexi.praxis.myapplication.model.ListLicense
import com.aprexi.praxis.myapplication.model.ListOffersResponse
import com.aprexi.praxis.myapplication.model.ListProfessionalFamilies
import com.aprexi.praxis.myapplication.model.ListProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.ListRequestOffer
import com.aprexi.praxis.myapplication.model.ListSchool
import com.aprexi.praxis.myapplication.model.ListStudiesUser
import com.aprexi.praxis.myapplication.model.ListTypeStudies
import com.aprexi.praxis.myapplication.model.Login
import com.aprexi.praxis.myapplication.model.NameStudies
import com.aprexi.praxis.myapplication.model.Offer
import com.aprexi.praxis.myapplication.model.ProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.RequestOfferUser
import com.aprexi.praxis.myapplication.model.StudiesUser
import com.aprexi.praxis.myapplication.model.UpdateExperienceJobUser
import com.aprexi.praxis.myapplication.model.UpdateLanguagesUser
import com.aprexi.praxis.myapplication.model.UpdateProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.UpdateStudiesUser
import com.aprexi.praxis.myapplication.model.UpdateUser
import com.aprexi.praxis.myapplication.model.User
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AprexiPraxisService {

    @GET("ListOfferCompany.php")
    suspend fun getOffersList(
        @Query("idUser") idUser: Int,
        @Query("token") token: String
    ): ListOffersResponse

    @GET("OfferCompany.php")
    suspend fun getOffer(
        @Query("idUser") idUser: Int,
        @Query("idOffer") idOffer: Int,
        @Query("token") token: String
    ): Offer

    @GET("CheckToken.php")
    suspend fun getCheckToken(@Query("token") token: String): CheckToken

    @GET("LoginUser.php")
    suspend fun getLogin(@Query("email") email: String, @Query("password") password: String): Login

    @POST("RequestOfferUser.php")
    suspend fun setRequestOffer(
        @Query("idUser") idUser: Int,
        @Query("idOffer") idOffer: Int,
        @Query("token") token: String
    ): RequestOfferUser

    @POST("FollowOfferUser.php")
    suspend fun setFollowOffer(
        @Query("idUser") idUser: Int,
        @Query("idOffer") idOffer: Int,
        @Query("token") token: String
    ): FollowOfferUser

    @DELETE("DeleteFollowOfferUser.php")
    suspend fun deleteFollowOffer(
        @Query("idUser") idUser: Int,
        @Query("idOffer") idOffer: Int,
        @Query("token") token: String
    ): DeleteFollowOfferUser

    @GET("ListRequestOffer.php")
    suspend fun getRequestOfferList(
        @Query("idUser") idUser: Int,
        @Query("token") token: String
    ): ListRequestOffer

    @GET("ListFollowtOffer.php")
    suspend fun getFollowOfferList(
        @Query("idUser") idUser: Int,
        @Query("token") token: String
    ): ListOffersResponse

    @GET("ListDetailRequestOffer.php")
    suspend fun getDetailRequestOfferList(
        @Query("idUser") idUser: Int,
        @Query("idOffer") idOffer: Int,
        @Query("token") token: String
    ): ListDetailRequestOffer

    @GET("Company.php")
    suspend fun getCompany(
        @Query("idUser") idUser: Int,
        @Query("idCompany") idCompany: Int,
        @Query("token") token: String
    ): Company

    @GET("ListOfferOnlyCompany.php")
    suspend fun getOffersListCompany(
        @Query("idUser") idUser: Int,
        @Query("idCompany") idCompany: Int,
        @Query("token") token: String
    ): ListOffersResponse

    @GET("User.php")
    suspend fun getUserData(@Query("idUser") idUser: Int, @Query("token") token: String): User

    @GET("ListStudiesUser.php")
    suspend fun getStudiesList(
        @Query("idUser") idUser: Int,
        @Query("token") token: String
    ): ListStudiesUser

    @GET("ListLanguagesUser.php")
    suspend fun getLanguagesList(
        @Query("idUser") idUser: Int,
        @Query("token") token: String
    ): ListLanguagesUser

    @GET("ListExperienceJobUser.php")
    suspend fun getExperienceJobList(
        @Query("idUser") idUser: Int,
        @Query("token") token: String
    ): ListExperienceJobUser

    @GET("ListProfessionalProyectsUser.php")
    suspend fun getProfessionalProyectsList(
        @Query("idUser") idUser: Int,
        @Query("token") token: String
    ): ListProfessionalProyectsUser

    @DELETE("DeleteStudiesUser.php")
    suspend fun deleteStudiesUser(
        @Query("idUser") idUser: Int,
        @Query("idStudiesUser") idStudiesUser: Int,
        @Query("token") token: String
    ): DeleteStudiesUser

    @GET("StudiesUser.php")
    suspend fun getStudiesUser(
        @Query("idUser") idUser: Int,
        @Query("idStudiesUser") idStudiesUser: Int,
        @Query("token") token: String
    ): StudiesUser

    @GET("LanguagesUser.php")
    suspend fun getLanguagesUser(
        @Query("idUser") idUser: Int,
        @Query("idLanguages") idLanguages: Int,
        @Query("token") token: String
    ): LanguagesUser

    @DELETE("DeleteLanguagesUser.php")
    suspend fun deleteLanguagesUser(
        @Query("idUser") idUser: Int,
        @Query("idLanguagesUser") idLanguagesUser: Int,
        @Query("token") token: String
    ): DeleteLanguagesUser

    @DELETE("DeleteExperienceJobUser.php")
    suspend fun deleteExperienceJobUser(
        @Query("idUser") idUser: Int,
        @Query("idExperienceJobUser") idExperienceJobUser: Int,
        @Query("token") token: String
    ): DeleteExperienceJobUser

    @GET("ExperienceJobUser.php")
    suspend fun getExperienceJobUser(
        @Query("idUser") idUser: Int,
        @Query("idExperienceJobUser") idExperienceJobUser: Int,
        @Query("token") token: String
    ): ExperienceJobUser

    @GET("ProfessionalProyectsUser.php")
    suspend fun getProfessionalProyectsUser(
        @Query("idUser") idUser: Int,
        @Query("idProfessionalProyectsUser") idProfessionalProyectsUser: Int,
        @Query("token") token: String
    ): ProfessionalProyectsUser

    @DELETE("DeleteProfessionalProyectsUser.php")
    suspend fun deleteProfessionalProyectsJobUser(
        @Query("idUser") idUser: Int,
        @Query("idProfessionalProyectUser") idProfessionalProyectUser: Int,
        @Query("token") token: String
    ): DeleteProfessionalProyectsUser

    @PUT("UpdateUser.php")
    suspend fun updateUser(
        @Query("idUser") idUser: Int,
        @Query("name") name: String,
        @Query("surname1") surname1: String,
        @Query("surname2") surname2: String,
        @Query("gender") gender: Int,
        @Query("mobile") mobile: Int,
        @Query("email") email: String,
        @Query("dni") dni: String,
        @Query("nie") nie: String,
        @Query("passport") passport: String,
        @Query("birthDate") birthDate: String,
        @Query("address") address: String,
        @Query("municipality") municipality: Int,
        @Query("description") description: String,
        @Query("workPermit") workPermit: Int,
        @Query("autonomousDischarge") autonomousDischarge: Int,
        @Query("ownVehicle") ownVehicle: Int,
        @Query("token") token: String
    ): UpdateUser

    @PUT("UpdateExperienceJobUser.php")
    suspend fun updateExperienceJobUser(
        @Query("idUser") idUser: Int,
        @Query("nameJobs") nameJobs: String,
        @Query("level") level: Int,
        @Query("category") category: Int,
        @Query("descriptionJob") descriptionJob: String,
        @Query("idCompany") idCompany: Int,
        @Query("initDate") initDate: String,
        @Query("endDate") endDate: String,
        @Query("idExperienceJobUser") idExperienceJobUser: Int,
        @Query("token") token: String
    ): UpdateExperienceJobUser

    @PUT("UpdateLanguagesUser.php")
    suspend fun updateLanguagesUser(
        @Query("idUser") idUser: Int,
        @Query("idLanguages") idLanguages: Int,
        @Query("idExperience") idExperience: Int,
        @Query("idLanguagesUser") idLanguagesUser: Int,
        @Query("token") token: String
    ): UpdateLanguagesUser

    @PUT("UpdateStudiesUser.php")
    suspend fun updateStudiesUser(
        @Query("idUser") idUser: Int,
        @Query("idNameStudies") idNameStudies: Int,
        @Query("startYear") startYear: String,
        @Query("endYear") endYear: String,
        @Query("idSchool") idSchool: Int,
        @Query("idStudiesUser") idStudiesUser: Int,
        @Query("token") token: String
    ): UpdateStudiesUser

    @PUT("UpdateProfessionalProyectsUser.php")
    suspend fun updateProfessionalProyectsUser(
        @Query("idUser") idUser: Int,
        @Query("nameProyect") nameProyect: String,
        @Query("descriptionProyect") descriptionProyect: String,
        @Query("websites") websites: String,
        @Query("job") job: String,
        @Query("initDate") initDate: String,
        @Query("endDate") endDate: String,
        @Query("idProfessionalProyectUser") idProfessionalProyectUser: Int,
        @Query("token") token: String
    ): UpdateProfessionalProyectsUser

    @POST("InsertStudiesUser.php")
    suspend fun insertStudiesUser(
        @Query("idUser") idUser: Int,
        @Query("idNameStudies") idNameStudies: Int,
        @Query("startYear") startYear: String,
        @Query("endYear") endYear: String,
        @Query("idSchool") idSchool: Int,
        @Query("idStudiesUser") idStudiesUser: Int,
        @Query("token") token: String
    ): InsertStudiesUser

    @POST("InsertLanguagesUser.php")
    suspend fun insertLanguagesUser(
        @Query("idUser") idUser: Int,
        @Query("idLanguages") idLanguages: Int,
        @Query("idExperience") idExperience: Int,
        @Query("idLanguagesUser") idLanguagesUser: Int,
        @Query("token") token: String
    ): InsertLanguagesUser

    @POST("InsertProfessionalProyectsUser.php")
    suspend fun insertProfessionalProyectsUser(
        @Query("idUser") idUser: Int,
        @Query("nameProyect") nameProyect: String,
        @Query("descriptionProyect") descriptionProyect: String,
        @Query("websites") websites: String,
        @Query("job") job: String,
        @Query("initDate") initDate: String,
        @Query("endDate") endDate: String,
        @Query("idProfessionalProyectUser") idProfessionalProyectUser: Int,
        @Query("token") token: String
    ): InsertProfessionalProyectsUser

    @POST("InsertExperienceJobUser.php")
    suspend fun insertExperienceJobUser(
        @Query("idUser") idUser: Int,
        @Query("nameJobs") nameJobs: String,
        @Query("level") level: Int,
        @Query("category") category: Int,
        @Query("descriptionJob") descriptionJob: String,
        @Query("idCompany") idCompany: Int,
        @Query("initDate") initDate: String,
        @Query("endDate") endDate: String,
        @Query("idExperienceJobUser") idExperienceJobUser: Int,
        @Query("token") token: String
    ): InsertExperienceJobUser

    @GET("ListTypeStudies.php")
    suspend fun getListTypeStudies(
        @Query("token") token: String
    ): ListTypeStudies

    @GET("ListProfessionalFamilies.php")
    suspend fun getListProfessionalFamilies(
        @Query("token") token: String
    ): ListProfessionalFamilies

    @GET("ListSchools.php")
    suspend fun getListSchools(
        @Query("token") token: String
    ): ListSchool

    @GET("NameStudies.php")
    suspend fun getNameStudies(
        @Query("idTypeStudies") idTypeStudies: Int,
        @Query("idProfessionalFamilies") idProfessionalFamilies: Int,
        @Query("token") token: String
    ): NameStudies

    @GET("ListCompany.php")
    suspend fun getListCompany(
        @Query("token") token: String
    ): ListBasicCompany

    @GET("ListLevel.php")
    suspend fun getListLevel(
        @Query("token") token: String
    ): ListLevelJob

    @GET("ListCategory.php")
    suspend fun getListCategory(
        @Query("token") token: String
    ): ListCategory

    @GET("ListExperience.php")
    suspend fun getListExperience(
        @Query("token") token: String
    ): ListExperience

    @GET("ListLanguages.php")
    suspend fun getListLanguages(
        @Query("token") token: String
    ): ListBasicLanguages

    @GET("ListLicense.php")
    suspend fun getListLicenses(
        @Query("token") token: String
    ): ListLicense


}