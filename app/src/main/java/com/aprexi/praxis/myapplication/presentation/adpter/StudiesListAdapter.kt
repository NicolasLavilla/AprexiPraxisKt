package com.aprexi.praxis.myapplication.presentation.adpter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aprexi.praxis.myapplication.databinding.RowListStudiesBinding
import com.aprexi.praxis.myapplication.model.StudiesUser
import com.aprexi.praxis.myapplication.presentation.utils.Utils

class StudiesListAdapter(
    private val myUtils: Utils
): RecyclerView.Adapter<StudiesListAdapter.StudiesListViewHolder>() {

    private var studiesUserList: List<StudiesUser> = emptyList()

    var onClickListener: (StudiesUser) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): StudiesListAdapter.StudiesListViewHolder {
        val binding =
            RowListStudiesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudiesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudiesListAdapter.StudiesListViewHolder, position: Int) {
        val item = studiesUserList[position]

        holder.rootView.setOnClickListener {
            onClickListener.invoke(item)
        }

        holder.nameStudies.text = item.nameStudies
        holder.schoolStudies.text = item.nameSchool

        val fullDate = buildString {
            if (item.startYear.isNotEmpty()) {
                append(myUtils.transformDateFormatMMYYYY(item.startYear))
            }
            if (item.endYear.isNotEmpty()) {
                append(" - ")
                append(myUtils.transformDateFormatMMYYYY(item.endYear))
            }
        }
        holder.dateStudies.text = fullDate

    }
    override fun getItemCount(): Int {
        return studiesUserList.size
    }

    inner class StudiesListViewHolder(binding: RowListStudiesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val rootView = binding.root
        val nameStudies = binding.tvTitleStudiesUserRowListStudies
        val schoolStudies = binding.tvNameSchoolRowListStudies
        val dateStudies = binding.tvDatesCourseRowListStudies
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<StudiesUser>) {
        studiesUserList = list
        notifyDataSetChanged()
    }

}