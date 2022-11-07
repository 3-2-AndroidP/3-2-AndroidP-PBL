package com.example.androidpbl

import androidx.lifecycle.ViewModel


data class Item(val name: String)


class ListViewModel : ViewModel() {
    val items  = ArrayList<Item>()

    init{
        addItem(Item("친구 1호"))
        addItem(Item("친구 2호"))
    }

    fun addItem(item: Item){
        items.add(item)
    }
    fun updateItem(item: Item, pos: Int){
        items[pos] = item
    }
    fun deleteItem(pos: Int){
        items.removeAt(pos)
    }
}