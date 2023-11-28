package com.aprexi.praxis.myapplication.presentation.adpter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aprexi.praxis.myapplication.R
import com.aprexi.praxis.myapplication.databinding.RowOfferDetailRequestListItemBinding
import com.aprexi.praxis.myapplication.model.DetailRequestOffer
import com.aprexi.praxis.myapplication.presentation.utils.Utils

class DetailRequestOfferListAdapter(
    private val myUtils: Utils
): RecyclerView.Adapter<DetailRequestOfferListAdapter.DetailRequestOfferListViewHolder>() {

    private var detailRequestOfferList: List<DetailRequestOffer> = emptyList()

    var onClickListener: (DetailRequestOffer) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailRequestOfferListAdapter.DetailRequestOfferListViewHolder {
        val binding =
            RowOfferDetailRequestListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailRequestOfferListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailRequestOfferListAdapter.DetailRequestOfferListViewHolder, position: Int) {
        val item = detailRequestOfferList[position]

        holder.rootView.setOnClickListener {
            onClickListener.invoke(item)
        }

        holder.circleState.setColorFilter(myUtils.colorState(item.stateRequest.toInt(), holder.rootView.context))
        holder.titleState.text = item.nameState
        holder.titleState.setTextColor(myUtils.colorState(item.stateRequest.toInt(), holder.rootView.context))
        holder.descriptionState.text = item.descriptionActionRequest
        holder.time.text = myUtils.calculateElapsedTime(item.dateRequest)
    }

    override fun getItemCount(): Int {
        return detailRequestOfferList.size
    }

    inner class DetailRequestOfferListViewHolder(binding: RowOfferDetailRequestListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val rootView = binding.root
        val circleState = binding.vStateCircle
        val titleState = binding.tvStateDetailRequestOffer
        val descriptionState = binding.tvTextDetailRequestOffer
        val time = binding.tvTimeDetailRequestOffer
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<DetailRequestOffer>) {
        detailRequestOfferList = list
        notifyDataSetChanged()
    }
}