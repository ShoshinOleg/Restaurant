package com.shoshin.restaurant.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<E, T : BaseViewHolder<E>> :
    RecyclerView.Adapter<T>() {

    private fun diffUtilCallback(list: ArrayList<E>, data: List<E>): DiffUtilCallback<E> =
        object: DiffUtilCallback<E>(list, data) {
            override fun same(item1: E, item2: E): Boolean = this@BaseAdapter.same(item1, item2)
        }

    abstract fun same(item1: E, item2: E): Boolean

    protected var items = ArrayList<E>()
    fun getItems(): List<E> = items

    fun setupItems(data: List<E>) {
        val diffCallback = diffUtilCallback(items, data)
        val result = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(data)
        result.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: T, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    protected fun Int.makeView(parent: ViewGroup) =
        LayoutInflater.from(parent.context).inflate(this, parent, false)
}