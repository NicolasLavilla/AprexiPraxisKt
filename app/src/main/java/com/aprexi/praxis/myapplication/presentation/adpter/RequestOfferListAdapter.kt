package com.aprexi.praxis.myapplication.presentation.adpter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aprexi.praxis.myapplication.R
import com.aprexi.praxis.myapplication.databinding.RowOfferRequestListItemBinding
import com.aprexi.praxis.myapplication.model.RequestOffer
import com.aprexi.praxis.myapplication.presentation.utils.Utils
import com.bumptech.glide.Glide

class RequestOfferListAdapter(
    private val myUtils: Utils
): RecyclerView.Adapter<RequestOfferListAdapter.RequestOfferListViewHolder>() {

    private var requestOfferList: List<RequestOffer> = emptyList()


    var onClickListener: (RequestOffer) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): RequestOfferListAdapter.RequestOfferListViewHolder {
        val binding =
            RowOfferRequestListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RequestOfferListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RequestOfferListAdapter.RequestOfferListViewHolder, position: Int) {
        val item = requestOfferList[position]

        holder.rootView.setOnClickListener {
            onClickListener.invoke(item)
        }

        holder.title.text = item.offerTitle
        holder.nameCompany.text = item.nameCompany
        holder.nameState.text = item.nameState
        holder.nameState.setTextColor(myUtils.colorState(item.stateRequest.toInt(), holder.rootView.context))
        holder.time.text = myUtils.calculateElapsedTime(item.datePublication)
        holder.circleState.setColorFilter(myUtils.colorState(item.stateRequest.toInt(), holder.rootView.context))

        if (!item.logoCompany.isNullOrEmpty()) {
            Glide.with(holder.logoImageView)
                .load(item.logoCompany)
                .into(holder.logoImageView)
        }
    }

    override fun getItemCount(): Int {
        return requestOfferList.size
    }

    inner class RequestOfferListViewHolder(binding: RowOfferRequestListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val rootView = binding.root
        val logoImageView = binding.ivOfferListItem
        val title = binding.tvTitleOfferRequestItem
        val nameCompany = binding.tvNameCompanyOfferRequestItem
        val circleState = binding.vCircleStateOfferRequestItem
        val nameState = binding.tvNameStateOfferRequestItem
        val time = binding.tvTimeOfferRequestItem
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<RequestOffer>) {
        requestOfferList = list
        notifyDataSetChanged()
    }
}