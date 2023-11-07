package com.aprexi.praxis.myapplication.presentation.adpter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aprexi.praxis.myapplication.databinding.RowListProyectsProfessionalsBinding
import com.aprexi.praxis.myapplication.model.ProfessionalProyectsUser

class ProfessionalProyectsListAdapter: RecyclerView.Adapter<ProfessionalProyectsListAdapter.ProfessionalProyectsListViewHolder>() {

    private var professionalProyectsUserList: List<ProfessionalProyectsUser> = emptyList()

    var onClickListener: (ProfessionalProyectsUser) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ProfessionalProyectsListAdapter.ProfessionalProyectsListViewHolder {
        val binding =
            RowListProyectsProfessionalsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfessionalProyectsListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfessionalProyectsListAdapter.ProfessionalProyectsListViewHolder, position: Int) {
        val item = professionalProyectsUserList[position]

        holder.rootView.setOnClickListener {
            onClickListener.invoke(item)
        }

        holder.nameProyect.text = item.nameProyect
        holder.descriptionProyect.text = item.descriptionProyect

    }
    override fun getItemCount(): Int {
        return professionalProyectsUserList.size
    }

    inner class ProfessionalProyectsListViewHolder(binding: RowListProyectsProfessionalsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val rootView = binding.root
        val nameProyect = binding.tvTitleProyectUserRowListProyectsProf
        val descriptionProyect = binding.tvTitleDescriptionUserRowListProyectsProf
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<ProfessionalProyectsUser>) {
        professionalProyectsUserList = list
        notifyDataSetChanged()
    }

}