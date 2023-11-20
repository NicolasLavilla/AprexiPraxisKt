package com.aprexi.praxis.myapplication.di

import com.aprexi.praxis.myapplication.data.category.CategoryDataImpl
import com.aprexi.praxis.myapplication.data.category.local.CategoryLocalImpl
import com.aprexi.praxis.myapplication.data.category.remote.CategoryRemoteImpl
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
import com.aprexi.praxis.myapplication.data.level.LevelDataImpl
import com.aprexi.praxis.myapplication.data.level.local.LevelLocalImpl
import com.aprexi.praxis.myapplication.data.level.remote.LevelRemoteImpl
import com.aprexi.praxis.myapplication.data.license.LicenseDataImpl
import com.aprexi.praxis.myapplication.data.license.local.LicenseLocalImpl
import com.aprexi.praxis.myapplication.data.license.remote.LicenseRemoteImpl
import com.aprexi.praxis.myapplication.data.local.MemoryCache
import com.aprexi.praxis.myapplication.data.login.LoginDataImpl
import com.aprexi.praxis.myapplication.data.login.local.LoginLocalImpl
import com.aprexi.praxis.myapplication.data.municipality.MunicipalityDataImpl
import com.aprexi.praxis.myapplication.data.municipality.local.MunicipalityLocalImpl
import com.aprexi.praxis.myapplication.data.municipality.remote.MunicipalityRemoteImpl
import com.aprexi.praxis.myapplication.data.offer.OfferDataImpl
import com.aprexi.praxis.myapplication.data.offer.local.OfferLocalImpl
import com.aprexi.praxis.myapplication.data.offer.local.TokenLocalImpl
import com.aprexi.praxis.myapplication.data.offer.remote.LoginRemoteImpl
import com.aprexi.praxis.myapplication.data.offer.remote.OfferRemoteImpl
import com.aprexi.praxis.myapplication.data.professionalFamilies.ProfessionalFamiliesDataImpl
import com.aprexi.praxis.myapplication.data.professionalFamilies.local.ProfessionalFamiliesLocalImpl
import com.aprexi.praxis.myapplication.data.professionalFamilies.remote.ProfessionalFamiliesRemoteImpl
import com.aprexi.praxis.myapplication.data.professionalProyects.ProfessionalProyectsImpl
import com.aprexi.praxis.myapplication.data.professionalProyects.local.ProfessionalProyectsLocalImpl
import com.aprexi.praxis.myapplication.data.professionalProyects.remote.ProfessionalProyectsRemoteImpl
import com.aprexi.praxis.myapplication.data.remote.ApiClient
import com.aprexi.praxis.myapplication.data.remote.AprexiPraxisService
import com.aprexi.praxis.myapplication.data.requestOffer.RequestOfferDataImpl
import com.aprexi.praxis.myapplication.data.requestOffer.local.RequestOfferLocalImpl
import com.aprexi.praxis.myapplication.data.requestOffer.remote.RequestOfferRemoteImpl
import com.aprexi.praxis.myapplication.data.school.SchoolDataImpl
import com.aprexi.praxis.myapplication.data.school.local.SchoolLocalImpl
import com.aprexi.praxis.myapplication.data.school.remote.SchoolRemoteImpl
import com.aprexi.praxis.myapplication.data.studies.StudiesDataImpl
import com.aprexi.praxis.myapplication.data.studies.local.StudiesLocalImpl
import com.aprexi.praxis.myapplication.data.studies.remote.StudiesRemoteImpl
import com.aprexi.praxis.myapplication.data.token.TokenDataImpl
import com.aprexi.praxis.myapplication.data.token.remote.TokenRemoteImpl
import com.aprexi.praxis.myapplication.data.user.UserDataImpl
import com.aprexi.praxis.myapplication.data.user.local.UserLocalImpl
import com.aprexi.praxis.myapplication.data.user.remote.UserRemoteImpl
import com.aprexi.praxis.myapplication.domain.CategoryRepository
import com.aprexi.praxis.myapplication.domain.CompanyRepository
import com.aprexi.praxis.myapplication.domain.ExperienceJobRepository
import com.aprexi.praxis.myapplication.domain.FollowOfferRepository
import com.aprexi.praxis.myapplication.domain.LanguagesRepository
import com.aprexi.praxis.myapplication.domain.LevelRepository
import com.aprexi.praxis.myapplication.domain.LicenseRepository
import com.aprexi.praxis.myapplication.domain.LoginRepository
import com.aprexi.praxis.myapplication.domain.MunicipalityRepository
import com.aprexi.praxis.myapplication.domain.OfferRepository
import com.aprexi.praxis.myapplication.domain.ProfessionalFamiliesRepository
import com.aprexi.praxis.myapplication.domain.ProfessionalProyectsRepository
import com.aprexi.praxis.myapplication.domain.RequestOfferRepository
import com.aprexi.praxis.myapplication.domain.SchoolRepository
import com.aprexi.praxis.myapplication.domain.StudiesRepository
import com.aprexi.praxis.myapplication.domain.TokenRepository
import com.aprexi.praxis.myapplication.domain.UserRepository
import com.aprexi.praxis.myapplication.domain.usercase.CleanTokenPreferencesUseCause
import com.aprexi.praxis.myapplication.domain.usercase.DeleteExperienceJobUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.DeleteFollowOfferUseCause
import com.aprexi.praxis.myapplication.domain.usercase.DeleteLanguagesUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.DeleteLicenseUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.DeleteProfessionalProyectUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.DeleteStudiesUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.FollowCompanyOfferUseCause
import com.aprexi.praxis.myapplication.domain.usercase.FollowOfferUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetCheckEmailUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetCheckTokenPreferencesUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetCheckTokenUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetCompanyUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetDetailRequestOfferListUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetExperienceJobUserListUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetExperienceJobUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetFollowOffersUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetLanguagesListUseCase
import com.aprexi.praxis.myapplication.domain.usercase.GetLanguagesUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetLicenseListUseCase
import com.aprexi.praxis.myapplication.domain.usercase.GetLicenseUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetListBasicMunicipalityUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetListCategoryUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetListCompanyUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetListExperienceUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetListBasicLanguagesUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetListLevelUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetListBasicLicenseUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetListProfessionalFamiliesUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetListSchoolUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetListTypeStudiesUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetLoginTokenPreferencesUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetLoginUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetNameStudiesUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetOfferListCompanyUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetOfferListUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetOfferUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetProfessionalProyectsListUseCase
import com.aprexi.praxis.myapplication.domain.usercase.GetProfessionalProyectsUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetRequestOfferListUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetStudiesListUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetStudiesUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetUserDataUseCase
import com.aprexi.praxis.myapplication.domain.usercase.InsertExperienceJobUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.InsertLanguagesUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.InsertLicenseUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.InsertProfessionalProyectUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.InsertStudiesUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.InsertUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.RequestOfferUseCause
import com.aprexi.praxis.myapplication.domain.usercase.SaveTokenPreferencesUseCause
import com.aprexi.praxis.myapplication.domain.usercase.UpdateExperienceJobUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.UpdateLanguagesUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.UpdateLicenseUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.UpdateProfessionalProyectUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.UpdateStudiesUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.UpdateUserUseCause
import com.aprexi.praxis.myapplication.presentation.adpter.DetailRequestOfferListAdapter
import com.aprexi.praxis.myapplication.presentation.adpter.ExperienceJobListAdapter
import com.aprexi.praxis.myapplication.presentation.adpter.FollowOfferListAdapter
import com.aprexi.praxis.myapplication.presentation.adpter.LicenseListAdapter
import com.aprexi.praxis.myapplication.presentation.adpter.OfferListAdapter
import com.aprexi.praxis.myapplication.presentation.adpter.RequestOfferListAdapter
import com.aprexi.praxis.myapplication.presentation.adpter.StudiesListAdapter
import com.aprexi.praxis.myapplication.presentation.utils.Utils
import com.aprexi.praxis.myapplication.presentation.viewmodel.CompanyViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.CurriculumViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.DetailExperienceJobViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.DetailLanguagesViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.DetailLicenseViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.DetailProfessionalProyectsViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.DetailRequestOfferViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.DetailStudiesViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.DetailUserViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.LoginViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.OfferFollowViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.OfferRequestViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.OfferViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.RegisterViewModel
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

    factory { CompanyLocalImpl(get()) }
    factory { CompanyRemoteImpl(get()) }
    factory<CompanyRepository> { CompanyDataImpl(get(),get()) }

    factory { UserLocalImpl(get()) }
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

    factory { CategoryLocalImpl(get()) }
    factory { CategoryRemoteImpl(get()) }
    factory<CategoryRepository> { CategoryDataImpl(get(),get()) }

    factory { LevelLocalImpl(get()) }
    factory { LevelRemoteImpl(get()) }
    factory<LevelRepository> { LevelDataImpl(get(),get()) }

    factory { LicenseLocalImpl(get()) }
    factory { LicenseRemoteImpl(get()) }
    factory<LicenseRepository> { LicenseDataImpl(get(),get()) }

    factory { ProfessionalFamiliesLocalImpl(get()) }
    factory { ProfessionalFamiliesRemoteImpl(get()) }
    factory<ProfessionalFamiliesRepository> { ProfessionalFamiliesDataImpl(get(),get()) }

    factory { SchoolLocalImpl(get()) }
    factory { SchoolRemoteImpl(get()) }
    factory<SchoolRepository> { SchoolDataImpl(get(),get()) }

    factory { MunicipalityLocalImpl(get()) }
    factory { MunicipalityRemoteImpl(get()) }
    factory<MunicipalityRepository> { MunicipalityDataImpl(get(),get()) }

    factory { RequestOfferListAdapter(get()) }
    factory { LicenseListAdapter() }
    factory { OfferListAdapter(get()) }
    factory { FollowOfferListAdapter(get()) }
    factory { DetailRequestOfferListAdapter(get()) }
    factory { StudiesListAdapter(get()) }
    factory { ExperienceJobListAdapter(get()) }

    factory { Utils() }

    factory { InsertLicenseUserUseCause(get())}
    factory { UpdateLicenseUserUseCause(get())}
    factory { DeleteLicenseUserUseCause(get())}
    factory { GetLicenseListUseCase(get())}
    factory { GetLicenseUserUseCause(get())}
    factory { GetCheckEmailUseCause(get())}
    factory { GetListBasicMunicipalityUseCause(get())}
    factory { GetListCategoryUseCause(get())}
    factory { GetListCompanyUseCause(get())}
    factory { GetListExperienceUseCause(get())}
    factory { GetListBasicLanguagesUseCause(get())}
    factory { GetListLevelUseCause(get())}
    factory { GetListBasicLicenseUseCause(get())}
    factory { GetListProfessionalFamiliesUseCause(get())}
    factory { GetListSchoolUseCause(get())}
    factory { GetListTypeStudiesUseCause(get())}
    factory { GetNameStudiesUseCause(get())}
    factory { InsertProfessionalProyectUserUseCause(get())}
    factory { InsertExperienceJobUserUseCause(get())}
    factory { InsertLanguagesUserUseCause(get())}
    factory { InsertStudiesUserUseCause(get()) }
    factory { UpdateExperienceJobUserUseCause(get())}
    factory { UpdateLanguagesUserUseCause(get())}
    factory { UpdateProfessionalProyectUserUseCause(get())}
    factory { UpdateStudiesUserUseCause(get())}
    factory { UpdateUserUseCause(get())}
    factory { DeleteExperienceJobUserUseCause(get()) }
    factory { DeleteProfessionalProyectUserUseCause(get()) }
    factory { DeleteLanguagesUserUseCause(get()) }
    factory { GetExperienceJobUserUseCause(get()) }
    factory { GetProfessionalProyectsUserUseCause(get()) }
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
    factory { InsertUserUseCause(get())}

    viewModel{OfferViewModel(get(),get(),get(),get(),get(),get(),get())}
    viewModel{CurriculumViewModel(get(),get(),get(),get(),get(),get()) }
    viewModel{TokenViewModel(get(),get(),get(),get(),get())}
    viewModel{LoginViewModel(get())}
    viewModel{OfferRequestViewModel(get())}
    viewModel{OfferFollowViewModel(get()) }
    viewModel{DetailRequestOfferViewModel(get())}
    viewModel{CompanyViewModel(get())}
    viewModel{DetailStudiesViewModel(get(),get(),get(),get(),get(),get(),get(),get()) }
    viewModel{DetailLanguagesViewModel(get(),get(),get(),get(),get(),get()) }
    viewModel{DetailProfessionalProyectsViewModel(get(),get(),get(),get()) }
    viewModel{DetailExperienceJobViewModel(get(),get(),get() ,get() ,get() ,get(),get()) }
    viewModel{DetailUserViewModel(get(),get(),get()) }
    viewModel{RegisterViewModel(get(),get()) }
    viewModel{DetailLicenseViewModel(get(),get(),get(),get(),get()) }

}