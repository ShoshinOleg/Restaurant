package com.shoshin.restaurant.ui.common

import androidx.recyclerview.widget.DiffUtil

abstract class DiffUtilCallback<T>(
    private val oldList: List<T>,
    private val newList: List<T>
): DiffUtil.Callback() {

    abstract fun same(item1: T, item2: T): Boolean

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return same(oldList[oldItemPosition], newList[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        newList[newItemPosition]?.equals(oldList[oldItemPosition]) == true
}