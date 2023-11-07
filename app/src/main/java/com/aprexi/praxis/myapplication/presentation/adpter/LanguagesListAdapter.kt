package com.aprexi.praxis.myapplication.presentation.adpter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aprexi.praxis.myapplication.databinding.RowListLanguageBinding
import com.aprexi.praxis.myapplication.model.LanguagesUser

class LanguagesListAdapter: RecyclerView.Adapter<LanguagesListAdapter.LanguageListViewHolder>() {

    private var languagesUserList: List<LanguagesUser> = emptyList()

    var onClickListener: (LanguagesUser) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): LanguagesListAdapter.LanguageListViewHolder {
        val binding =
            RowListLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LanguageListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LanguagesListAdapter.LanguageListViewHolder, position: Int) {
        val item = languagesUserList[position]

        holder.rootView.setOnClickListener {
            onClickListener.invoke(item)
        }

        holder.nameLanguage.text = item.nameLanguages
        holder.levelLanguage.text = item.nameExperience

    }
    override fun getItemCount(): Int {
        return languagesUserList.size
    }

    inner class LanguageListViewHolder(binding: RowListLanguageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val rootView = binding.root
        val nameLanguage = binding.tvNameLanguageRowListStudies
        val levelLanguage = binding.tvLevelLanguageRowListStudies
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<LanguagesUser>) {
        languagesUserList = list
        notifyDataSetChanged()
    }

}