package com.aprexi.praxis.myapplication.di

import com.aprexi.praxis.myapplication.data.followOffer.FollowOfferDataImpl
import com.aprexi.praxis.myapplication.data.followOffer.local.FollowOfferLocalImpl
import com.aprexi.praxis.myapplication.data.followOffer.remote.FollowOfferRemoteImpl
import com.aprexi.praxis.myapplication.data.local.MemoryCache
import com.aprexi.praxis.myapplication.data.offer.OfferDataImpl
import com.aprexi.praxis.myapplication.data.login.LoginDataImpl
import com.aprexi.praxis.myapplication.data.token.TokenDataImpl
import com.aprexi.praxis.myapplication.data.offer.local.OfferLocalImpl
import com.aprexi.praxis.myapplication.data.login.local.LoginLocalImpl
import com.aprexi.praxis.myapplication.data.offer.local.TokenLocalImpl
import com.aprexi.praxis.myapplication.data.offer.remote.OfferRemoteImpl
import com.aprexi.praxis.myapplication.data.offer.remote.LoginRemoteImpl
import com.aprexi.praxis.myapplication.data.offer.remote.TokenRemoteImpl
import com.aprexi.praxis.myapplication.data.remote.ApiClient
import com.aprexi.praxis.myapplication.data.remote.AprexiPraxisService
import com.aprexi.praxis.myapplication.data.requestOffer.RequestOfferDataImpl
import com.aprexi.praxis.myapplication.data.requestOffer.local.RequestOfferLocalImpl
import com.aprexi.praxis.myapplication.data.requestOffer.remote.RequestOfferRemoteImpl
import com.aprexi.praxis.myapplication.domain.FollowOfferRepository
import com.aprexi.praxis.myapplication.domain.LoginRepository
import com.aprexi.praxis.myapplication.domain.OfferRepository
import com.aprexi.praxis.myapplication.domain.RequestOfferRepository
import com.aprexi.praxis.myapplication.domain.TokenRepository
import com.aprexi.praxis.myapplication.domain.usercase.CleanTokenPreferencesUseCause
import com.aprexi.praxis.myapplication.domain.usercase.DeleteFollowOfferUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetCheckTokenPreferencesUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetCheckTokenUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetLoginTokenPreferencesUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetLoginUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetOfferUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetOffersUseCause
import com.aprexi.praxis.myapplication.domain.usercase.RequestOfferUseCause
import com.aprexi.praxis.myapplication.domain.usercase.FollowCompanyOfferUseCause
import com.aprexi.praxis.myapplication.domain.usercase.FollowOfferUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetFollowOffersUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetRequestOfferListUseCause
import com.aprexi.praxis.myapplication.domain.usercase.SaveTokenPreferencesUseCause
import com.aprexi.praxis.myapplication.presentation.viewmodel.LoginViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.OfferFollowViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.OfferRequestViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.OfferViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val baseModule = module {
    single { MemoryCache() }
    single<AprexiPraxisService> { ApiClient.retrofit.create(AprexiPraxisService::class.java) }
}

val praxisModule = module {

    factory { LoginLocalImpl(get(),get()) }
    factory { LoginRemoteImpl(get()) }
    factory<LoginRepository> { LoginDataImpl(get(),get()) }

    factory { TokenLocalImpl(get(),get()) }
    factory { TokenRemoteImpl(get()) }
    factory<TokenRepository> { TokenDataImpl(get(),get()) }

    factory { OfferLocalImpl(get()) }
    factory { OfferRemoteImpl(get()) }
    factory<OfferRepository> { OfferDataImpl(get(),get()) }

    factory { RequestOfferLocalImpl(get()) }
    factory { RequestOfferRemoteImpl(get()) }
    factory<RequestOfferRepository> { RequestOfferDataImpl(get(),get()) }

    factory { FollowOfferLocalImpl(get()) }
    factory { FollowOfferRemoteImpl(get()) }
    factory<FollowOfferRepository> { FollowOfferDataImpl(get(),get()) }


    factory { GetFollowOffersUseCause(get()) }
    factory { GetRequestOfferListUseCause(get()) }
    factory { DeleteFollowOfferUseCause(get()) }
    factory { GetLoginTokenPreferencesUseCause(get()) }
    factory { GetCheckTokenPreferencesUseCause(get()) }
    factory { SaveTokenPreferencesUseCause(get()) }
    factory { CleanTokenPreferencesUseCause(get()) }
    factory { GetLoginUseCause(get()) }
    factory { GetCheckTokenUseCause(get()) }
    factory { RequestOfferUseCause(get()) }
    factory { FollowOfferUseCause(get()) }
    factory { FollowCompanyOfferUseCause(get()) }
    factory { GetOffersUseCause(get()) }
    factory { GetOfferUseCause(get()) }

    viewModel{OfferViewModel(get(),get(),get(),get(),get(),get())}
    viewModel{TokenViewModel(get(),get(),get(),get(),get())}
    viewModel{LoginViewModel(get())}
    viewModel{OfferRequestViewModel(get())}
    viewModel{ OfferFollowViewModel(get()) }
}