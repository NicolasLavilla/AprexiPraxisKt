package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.OfferRepository

class FollowCompanyOfferUseCause (
    private val offerRepository: OfferRepository
) {

    fun execute(idCompanyOffer: Int) {
        offerRepository.followCompanyOffer(idCompanyOffer)
    }
}