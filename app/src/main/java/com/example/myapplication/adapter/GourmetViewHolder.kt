package com.example.myapplication.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemGourmetListBinding
import com.example.myapplication.models.TestGourmet

class GourmetViewHolder(private val binding: ItemGourmetListBinding) :RecyclerView.ViewHolder(binding.root){
    fun bind(gourmetData: TestGourmet) {
        binding.apply {
            nameTextView.text = gourmetData.name
            accessTextView.text = gourmetData.access
        }
    }
}