package com.example.androidpbl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpbl.databinding.ItemLayout2Binding
import com.example.androidpbl.databinding.ItemLayoutBinding

class FriendListAdapter(private val viewModel: ListViewModel) : RecyclerView.Adapter<FriendListAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemLayout2Binding) : RecyclerView.ViewHolder(binding.root) {
        fun setContents(pos: Int){
            val item = viewModel.items[pos]
            binding.textView2.text = item.name
//            binding.imageView3.setImageResource(@drawable/buigs)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLayout2Binding.inflate(layoutInflater, parent, false)
        val viewHolder = ViewHolder(binding)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // position의 데이터를 뷰홀더에 넣음
        holder.setContents(position)
    }

    override fun getItemCount() = viewModel.items.size
}