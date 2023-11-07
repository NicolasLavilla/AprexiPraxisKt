package com.aprexi.praxis.myapplication.data.local

import com.aprexi.praxis.myapplication.model.ListExperienceJobUser
import com.aprexi.praxis.myapplication.model.ListLanguagesUser
import com.aprexi.praxis.myapplication.model.ListOffersResponse
import com.aprexi.praxis.myapplication.model.ListProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.ListRequestOffer
import com.aprexi.praxis.myapplication.model.ListStudiesUser

class MemoryCache {

    var offerList: ListOffersResponse? = null
    var followOfferList: ListOffersResponse? = null
    var requestOfferList: ListRequestOffer? = null
    var studiesUserList: ListStudiesUser? = null
    var languagesUserList: ListLanguagesUser? = null
    var experienceJobUserList: ListExperienceJobUser? = null
    var professionalProyectsList: ListProfessionalProyectsUser? = null

    fun clearAll() {
        offerList = null
        requestOfferList = null
        followOfferList= null
        studiesUserList = null
        languagesUserList = null
        experienceJobUserList = null
        professionalProyectsList = null
    }

}