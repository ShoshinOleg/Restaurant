package com.shoshin.restaurant.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseMutableAdapter<E, T : BaseViewHolder<E>> :
    RecyclerView.Adapter<T>() {

    private var items: MutableList<E> = mutableListOf()

    fun setupItems(data: MutableList<E>) {
        items = data
        notifyItemRangeInserted(0, data.size)
    }

    fun setItem(item: E) {
        val index = items.indexOfFirst { same(it, item) }
        if(index != -1) {
            items[index] = item
            notifyItemChanged(index)
        } else {
            items.add(item)
            notifyItemInserted(items.size -1)
        }
    }

    fun addItem(item: E) {
        items.add(item)
        notifyItemInserted(items.size-1)
    }

    fun removeItem(item: E) {
        val index = items.indexOfFirst { same(it, item) }
        if(index != -1) {
            items.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    abstract fun same(it1: E, it2: E): Boolean

    override fun onBindViewHolder(holder: T, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    protected fun Int.makeView(parent: ViewGroup) =
        LayoutInflater.from(parent.context).inflate(this, parent, false)
}