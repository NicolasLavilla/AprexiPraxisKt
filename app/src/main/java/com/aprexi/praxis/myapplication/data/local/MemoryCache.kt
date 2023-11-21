package com.aprexi.praxis.myapplication.data.local

import com.aprexi.praxis.myapplication.model.ListBasicCompany
import com.aprexi.praxis.myapplication.model.ListBasicLanguages
import com.aprexi.praxis.myapplication.model.ListBasicMunicipality
import com.aprexi.praxis.myapplication.model.ListCategory
import com.aprexi.praxis.myapplication.model.ListExperience
import com.aprexi.praxis.myapplication.model.ListExperienceJobUser
import com.aprexi.praxis.myapplication.model.ListLanguagesUser
import com.aprexi.praxis.myapplication.model.ListLevelJob
import com.aprexi.praxis.myapplication.model.ListBasicLicense
import com.aprexi.praxis.myapplication.model.ListOffersResponse
import com.aprexi.praxis.myapplication.model.ListProfessionalFamilies
import com.aprexi.praxis.myapplication.model.ListProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.ListRequestOffer
import com.aprexi.praxis.myapplication.model.ListSchool
import com.aprexi.praxis.myapplication.model.ListStudiesUser
import com.aprexi.praxis.myapplication.model.ListTypeStudies
import com.aprexi.praxis.myapplication.model.User

class MemoryCache {

    var offerList: ListOffersResponse? = null
    var followOfferList: ListOffersResponse? = null
    var requestOfferList: ListRequestOffer? = null
    var studiesUserList: ListStudiesUser? = null
    var languagesUserList: ListLanguagesUser? = null
    var experienceJobUserList: ListExperienceJobUser? = null
    var professionalProyectsList: ListProfessionalProyectsUser? = null
    var typeStudies: ListTypeStudies? = null
    var professionalFamilies: ListProfessionalFamilies? = null
    var school: ListSchool? = null
    var listCompany: ListBasicCompany? = null
    var level: ListLevelJob? = null
    var category: ListCategory? = null
    var experience: ListExperience? = null
    var languagesBasic: ListBasicLanguages? = null
    var listBasicLicense: ListBasicLicense? = null
    var listMunicipality: ListBasicMunicipality? = null
    var userData: User? = null

    //Datos completos de la aplicación
    fun clearAll() {
        offerList = null
        requestOfferList = null
        followOfferList= null
        studiesUserList = null
        languagesUserList = null
        experienceJobUserList = null
        professionalProyectsList = null
        typeStudies = null
        professionalFamilies = null
        school = null
        listCompany = null
        level = null
        category = null
        experience = null
        languagesBasic = null
        listBasicLicense = null
        listMunicipality = null
        userData = null
    }

    //Datos de Usuario
    fun cleanUser(){
        userData = null
        offerList = null
        requestOfferList = null
        followOfferList= null
        studiesUserList = null
        languagesUserList = null
        experienceJobUserList = null
        professionalProyectsList = null
    }

}