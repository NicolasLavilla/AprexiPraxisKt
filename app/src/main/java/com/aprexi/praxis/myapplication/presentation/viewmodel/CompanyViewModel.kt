package com.aprexi.praxis.myapplication.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aprexi.praxis.myapplication.domain.usercase.GetCompanyUseCause
import com.aprexi.praxis.myapplication.model.Company
import com.aprexi.praxis.myapplication.model.Offer
import com.aprexi.praxis.myapplication.model.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


typealias CompanyState = ResourceState<Company>
class CompanyViewModel(
    private val companyUseCause: GetCompanyUseCause
): ViewModel() {

    private val _companyLiveData = MutableLiveData<CompanyState>()

    fun getCompanyLiveData(): LiveData<CompanyState> {
        return _companyLiveData
    }

    fun fetchCompany(idUser: Int ,idCompany: Int, token: String) {
        _companyLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val company = companyUseCause.execute(idUser = idUser, idCompany = idCompany, token = token)

                withContext(Dispatchers.Main) {
                    if (company.success){
                        _companyLiveData.value = ResourceState.Success(company)
                    }else{
                        _companyLiveData.value = ResourceState.SuccessFaild(company)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _companyLiveData.value = ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

}