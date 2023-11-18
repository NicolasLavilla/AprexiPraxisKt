package com.aprexi.praxis.myapplication.presentation.adpter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aprexi.praxis.myapplication.databinding.RowListExperienceBinding
import com.aprexi.praxis.myapplication.model.ExperienceJobUser
import com.aprexi.praxis.myapplication.presentation.utils.Utils

class ExperienceJobListAdapter(
    private val myUtils: Utils
): RecyclerView.Adapter<ExperienceJobListAdapter.ExperienceJobListViewHolder>() {

    private var experienceJobUserList: List<ExperienceJobUser> = emptyList()

    var onClickListener: (ExperienceJobUser) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ExperienceJobListAdapter.ExperienceJobListViewHolder {
        val binding =
            RowListExperienceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExperienceJobListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExperienceJobListAdapter.ExperienceJobListViewHolder, position: Int) {
        val item = experienceJobUserList[position]

        holder.rootView.setOnClickListener {
            onClickListener.invoke(item)
        }

        holder.nameExperienceJob.text = item.nameJobs
        holder.company.text = item.nameCompany

        val fullDate = buildString {
            if (item.initDate.isNotEmpty()) {
                append(myUtils.transformDateFormatMMYYYY(item.initDate))
            }
            if (item.endDate.isNotEmpty()) {
                append(" - ")
                append(myUtils.transformDateFormatMMYYYY(item.endDate))
            }
        }
        holder.dateExperienceJob.text = fullDate

    }
    override fun getItemCount(): Int {
        return experienceJobUserList.size
    }

    inner class ExperienceJobListViewHolder(binding: RowListExperienceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val rootView = binding.root
        val nameExperienceJob = binding.tvTitleExperienceRowListStudies
        val company = binding.tvNameCompanyRowListStudies
        val dateExperienceJob = binding.tvDatesExperienceRowListStudies
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<ExperienceJobUser>) {
        experienceJobUserList = list
        notifyDataSetChanged()
    }

}