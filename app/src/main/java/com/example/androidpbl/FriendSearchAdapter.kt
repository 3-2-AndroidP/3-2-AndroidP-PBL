package com.example.androidpbl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpbl.databinding.ItemLayoutBinding

class FriendSearchAdapter(private val viewModel: ListViewModel) : RecyclerView.Adapter<FriendSearchAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setContents(pos: Int){
            val item = viewModel.items[pos]
            binding.textView2.text = item.name
//            binding.imageView3.setImageResource(@drawable/buigs)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutBinding.inflate(layoutInflater, parent, false)
        val viewHolder = ViewHolder(binding)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // position의 데이터를 뷰홀더에 넣음
        holder.setContents(position)
    }

    override fun getItemCount() = viewModel.items.size
}