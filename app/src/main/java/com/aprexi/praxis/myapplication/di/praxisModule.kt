package com.aprexi.praxis.myapplication.di

import com.aprexi.praxis.myapplication.data.company.CompanyDataImpl
import com.aprexi.praxis.myapplication.data.company.local.CompanyLocalImpl
import com.aprexi.praxis.myapplication.data.company.remote.CompanyRemoteImpl
import com.aprexi.praxis.myapplication.data.experience.ExperienceJobDataImpl
import com.aprexi.praxis.myapplication.data.experience.local.ExperienceJobLocalImpl
import com.aprexi.praxis.myapplication.data.experience.remote.ExperienceJobRetomeImpl
import com.aprexi.praxis.myapplication.data.followOffer.FollowOfferDataImpl
import com.aprexi.praxis.myapplication.data.followOffer.local.FollowOfferLocalImpl
import com.aprexi.praxis.myapplication.data.followOffer.remote.FollowOfferRemoteImpl
import com.aprexi.praxis.myapplication.data.languages.LanguagesDataImpl
import com.aprexi.praxis.myapplication.data.languages.local.LanguagesLocalImpl
import com.aprexi.praxis.myapplication.data.languages.remote.LanguagesRemoteImpl
import com.aprexi.praxis.myapplication.data.local.MemoryCache
import com.aprexi.praxis.myapplication.data.offer.OfferDataImpl
import com.aprexi.praxis.myapplication.data.login.LoginDataImpl
import com.aprexi.praxis.myapplication.data.token.TokenDataImpl
import com.aprexi.praxis.myapplication.data.offer.local.OfferLocalImpl
import com.aprexi.praxis.myapplication.data.login.local.LoginLocalImpl
import com.aprexi.praxis.myapplication.data.offer.local.TokenLocalImpl
import com.aprexi.praxis.myapplication.data.offer.remote.OfferRemoteImpl
import com.aprexi.praxis.myapplication.data.offer.remote.LoginRemoteImpl
import com.aprexi.praxis.myapplication.data.token.remote.TokenRemoteImpl
import com.aprexi.praxis.myapplication.data.remote.ApiClient
import com.aprexi.praxis.myapplication.data.remote.AprexiPraxisService
import com.aprexi.praxis.myapplication.data.requestOffer.RequestOfferDataImpl
import com.aprexi.praxis.myapplication.data.requestOffer.local.RequestOfferLocalImpl
import com.aprexi.praxis.myapplication.data.requestOffer.remote.RequestOfferRemoteImpl
import com.aprexi.praxis.myapplication.data.professionalProyects.ProfessionalProyectsImpl
import com.aprexi.praxis.myapplication.data.professionalProyects.local.ProfessionalProyectsLocalImpl
import com.aprexi.praxis.myapplication.data.professionalProyects.remote.ProfessionalProyectsRemoteImpl
import com.aprexi.praxis.myapplication.data.studies.StudiesDataImpl
import com.aprexi.praxis.myapplication.data.studies.local.StudiesLocalImpl
import com.aprexi.praxis.myapplication.data.studies.remote.StudiesRemoteImpl
import com.aprexi.praxis.myapplication.data.user.UserDataImpl
import com.aprexi.praxis.myapplication.data.user.local.UserLocalImpl
import com.aprexi.praxis.myapplication.data.user.remote.UserRemoteImpl
import com.aprexi.praxis.myapplication.domain.CompanyRepository
import com.aprexi.praxis.myapplication.domain.ExperienceJobRepository
import com.aprexi.praxis.myapplication.domain.FollowOfferRepository
import com.aprexi.praxis.myapplication.domain.LanguagesRepository
import com.aprexi.praxis.myapplication.domain.LoginRepository
import com.aprexi.praxis.myapplication.domain.OfferRepository
import com.aprexi.praxis.myapplication.domain.ProfessionalProyectsRepository
import com.aprexi.praxis.myapplication.domain.RequestOfferRepository
import com.aprexi.praxis.myapplication.domain.StudiesRepository
import com.aprexi.praxis.myapplication.domain.TokenRepository
import com.aprexi.praxis.myapplication.domain.UserRepository
import com.aprexi.praxis.myapplication.domain.usercase.CleanTokenPreferencesUseCause
import com.aprexi.praxis.myapplication.domain.usercase.DeleteFollowOfferUseCause
import com.aprexi.praxis.myapplication.domain.usercase.DeleteStudiesUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetCheckTokenPreferencesUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetCheckTokenUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetLoginTokenPreferencesUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetLoginUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetOfferUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetOfferListUseCause
import com.aprexi.praxis.myapplication.domain.usercase.RequestOfferUseCause
import com.aprexi.praxis.myapplication.domain.usercase.FollowCompanyOfferUseCause
import com.aprexi.praxis.myapplication.domain.usercase.FollowOfferUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetCompanyUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetDetailRequestOfferListUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetExperienceJobUserListUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetFollowOffersUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetLanguagesListUseCase
import com.aprexi.praxis.myapplication.domain.usercase.GetLanguagesUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetOfferListCompanyUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetProfessionalProyectsListUseCase
import com.aprexi.praxis.myapplication.domain.usercase.GetRequestOfferListUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetStudiesListUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetStudiesUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetUserDataUseCase
import com.aprexi.praxis.myapplication.domain.usercase.SaveTokenPreferencesUseCause
import com.aprexi.praxis.myapplication.presentation.viewmodel.CompanyViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.CurriculumViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.DetailLanguagesViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.DetailRequestOfferViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.DetailStudiesViewModel
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

    factory { CompanyLocalImpl() }
    factory { CompanyRemoteImpl(get()) }
    factory<CompanyRepository> { CompanyDataImpl(get(),get()) }

    factory { UserLocalImpl() }
    factory { UserRemoteImpl(get()) }
    factory<UserRepository> { UserDataImpl(get(),get()) }

    factory { StudiesLocalImpl(get()) }
    factory { StudiesRemoteImpl(get()) }
    factory<StudiesRepository> { StudiesDataImpl(get(),get()) }

    factory { ProfessionalProyectsLocalImpl(get()) }
    factory { ProfessionalProyectsRemoteImpl(get()) }
    factory<ProfessionalProyectsRepository> { ProfessionalProyectsImpl(get(),get()) }

    factory { LanguagesLocalImpl(get()) }
    factory { LanguagesRemoteImpl(get()) }
    factory<LanguagesRepository> { LanguagesDataImpl(get(),get()) }

    factory { ExperienceJobLocalImpl(get()) }
    factory { ExperienceJobRetomeImpl(get()) }
    factory<ExperienceJobRepository> { ExperienceJobDataImpl(get(),get()) }


    factory { GetLanguagesUserUseCause(get()) }
    factory { DeleteStudiesUserUseCause(get())}
    factory { GetStudiesUserUseCause(get())}
    factory { GetProfessionalProyectsListUseCase(get())}
    factory { GetExperienceJobUserListUseCause(get())}
    factory { GetLanguagesListUseCase(get())}
    factory { GetStudiesListUseCause(get())}
    factory { GetUserDataUseCase(get())}
    factory { GetOfferListCompanyUseCause(get())}
    factory { GetCompanyUseCause(get()) }
    factory { GetDetailRequestOfferListUseCause(get()) }
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
    factory { GetOfferListUseCause(get()) }
    factory { GetOfferUseCause(get()) }

    viewModel{OfferViewModel(get(),get(),get(),get(),get(),get(),get())}
    viewModel{CurriculumViewModel(get(),get(),get(),get(),get()) }
    viewModel{TokenViewModel(get(),get(),get(),get(),get())}
    viewModel{LoginViewModel(get())}
    viewModel{OfferRequestViewModel(get())}
    viewModel{OfferFollowViewModel(get()) }
    viewModel{DetailRequestOfferViewModel(get())}
    viewModel{CompanyViewModel(get())}
    viewModel{DetailStudiesViewModel(get(),get()) }
    viewModel{ DetailLanguagesViewModel(get(),get()) }

}