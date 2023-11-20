package com.aprexi.praxis.myapplication.presentation.adpter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aprexi.praxis.myapplication.databinding.RowListLicenseBinding
import com.aprexi.praxis.myapplication.model.LicenseUser

class LicenseListAdapter: RecyclerView.Adapter<LicenseListAdapter.LicenseListViewHolder>() {

    private var licenseUserList: List<LicenseUser> = emptyList()

    var onClickListener: (LicenseUser) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): LicenseListAdapter.LicenseListViewHolder {
        val binding =
            RowListLicenseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LicenseListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LicenseListAdapter.LicenseListViewHolder, position: Int) {
        val item = licenseUserList[position]

        holder.rootView.setOnClickListener {
            onClickListener.invoke(item)
        }

        holder.nameLicense.text = item.nameLicense

    }
    override fun getItemCount(): Int {
        return licenseUserList.size
    }

    inner class LicenseListViewHolder(binding: RowListLicenseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val rootView = binding.root
        val nameLicense = binding.tvNameLicenseRowListLicense
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<LicenseUser>) {
        licenseUserList = list
        notifyDataSetChanged()
    }

}