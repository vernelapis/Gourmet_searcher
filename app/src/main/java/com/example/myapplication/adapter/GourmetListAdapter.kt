package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemGourmetListBinding
import com.example.myapplication.models.TestGourmet

private object DiffCallback : DiffUtil.ItemCallback<TestGourmet>() {
    override fun areItemsTheSame(oldItem: TestGourmet, newItem: TestGourmet): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TestGourmet, newItem: TestGourmet): Boolean {
        return oldItem == newItem
    }
}

class GourmetListAdapter: ListAdapter<TestGourmet, GourmetListAdapter.GourmetViewHolder>(DiffCallback) {
    class GourmetViewHolder(private val binding: ItemGourmetListBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(gourmetData: TestGourmet) {
            binding.apply {
                nameTextView.text = gourmetData.name
                accessTextView.text = gourmetData.access
                gourmet = gourmetData
            }
        }
    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GourmetViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemGourmetListBinding.inflate(inflater, parent, false)
            return GourmetViewHolder(binding)
        }

        override fun onBindViewHolder(holder: GourmetViewHolder, position: Int) {
//            holder.bind(getItem(position), viewLifecycleOwner, viewModel)
            holder.bind(getItem(position))
        }

}