package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemGourmetListBinding
import com.example.myapplication.models.Shop

private object DiffCallback : DiffUtil.ItemCallback<Shop>() {
    override fun areItemsTheSame(oldItem: Shop, newItem: Shop): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Shop, newItem: Shop): Boolean {
        return oldItem == newItem
    }
}

class GourmetListAdapter(
    private val onClickListener: OnClickListener
    ): ListAdapter<Shop, GourmetListAdapter.GourmetViewHolder>(DiffCallback) {
    class GourmetViewHolder(private val binding: ItemGourmetListBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(shopData: Shop) {
            binding.apply {
                nameTextView.text = shopData.name
                accessTextView.text = shopData.access
                gourmet = shopData
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GourmetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGourmetListBinding.inflate(inflater, parent, false)
        return GourmetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GourmetViewHolder, position: Int) {
        val shopData = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(shopData)
        }
        holder.bind(shopData)
    }

    class OnClickListener(val clickListener: (shopData: Shop) -> Unit) {
        fun onClick(shopData: Shop) = clickListener(shopData)
    }


}