package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemGourmetListBinding
import com.example.myapplication.models.TestGourmet

class GourmetListAdapter(private val gourmetList: List<TestGourmet>) : RecyclerView.Adapter<GourmetViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GourmetViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemGourmetListBinding.inflate(inflater, parent, false)
            return GourmetViewHolder(binding)
        }

        override fun onBindViewHolder(holder: GourmetViewHolder, position: Int) {
            val gourmet = gourmetList[position]
            holder.bind(gourmet)
        }

        override fun getItemCount(): Int = gourmetList.size


}