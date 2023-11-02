package com.aprexi.praxis.myapplication.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aprexi.praxis.myapplication.databinding.FragmentDescriptionCompanyBinding
import com.aprexi.praxis.myapplication.model.Company

class DescriptionCompanyFragment(private val company: Company): Fragment() {

    private val binding: FragmentDescriptionCompanyBinding by lazy {
        FragmentDescriptionCompanyBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {

        binding.tvSectorCompanyDescriptionCompanyFragment.text = company.nameSectorBusiness
        binding.tvDescriptionDescriptionCompanyFragment.text = company.description
        binding.tvLocationCompanyDescriptionCompanyFragment.text = company.nameMunicipality
        binding.tvNumberWorkersDescriptionCompanyFragment.text = company.numWorkers.toString()
        binding.tvWebDescriptionCompanyFragment.text = company.websites

    }

}