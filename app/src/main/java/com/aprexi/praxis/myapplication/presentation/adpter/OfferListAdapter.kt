package com.aprexi.praxis.myapplication.presentation.adpter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aprexi.praxis.myapplication.databinding.RowOfferListItemBinding
import com.aprexi.praxis.myapplication.model.Offer
import com.aprexi.praxis.myapplication.presentation.utils.Utils
import com.bumptech.glide.Glide

class OfferListAdapter(
    private val myUtils: Utils
) : RecyclerView.Adapter<OfferListAdapter.OfferListViewHolder>() {

    private var offerList: List<Offer> = emptyList()

    var onClickListener: (Offer) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferListViewHolder {
        val binding =
            RowOfferListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OfferListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OfferListViewHolder, position: Int) {
        val item = offerList[position]

        holder.rootView.setOnClickListener {
            onClickListener.invoke(item)
        }

        holder.titleTextView.text = item.offerTitle
        holder.companyTextView.text = item.nameCompany
        holder.ubicationTextView.text = item.nameMunicipality
        holder.vacantTextView.text = item.numVacancies.toString()
        holder.modalityTextView.text = item.nameModality
        holder.timeTextView.text = myUtils.calculateElapsedTime(item.datePublication)
        holder.salaryTextView.text = item.salary.toString()
        holder.inscriptionTextView.visibility =
            if (item.requestOffer.toInt() == 1) View.VISIBLE else View.GONE

        if (!item.logoCompany.isNullOrEmpty()) {
            Glide.with(holder.logoImageView)
                .load(item.logoCompany)
                .into(holder.logoImageView)
        }
    }

    override fun getItemCount(): Int {
        return offerList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Offer>) {
        offerList = list
        notifyDataSetChanged()
    }

    inner class OfferListViewHolder(binding: RowOfferListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val rootView = binding.root
        val logoImageView = binding.ivOfferListItem
        val titleTextView = binding.tvTitleOfferItem
        val companyTextView = binding.tvNameCompanyOfferItem
        val ubicationTextView = binding.tvLocationOfferListItem
        val vacantTextView = binding.tvVacantOfferListItem
        val modalityTextView = binding.tvModalityOfferListItem
        val timeTextView = binding.tvTimeOfferListItem
        val salaryTextView = binding.tvSalaryOfferListItem
        val inscriptionTextView = binding.tvInscriptionOfferListItem
    }
}