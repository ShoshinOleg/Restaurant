package com.shoshin.restaurant.ui.common.state_recycler.mutable_state_recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoshin.restaurant.ui.common.state_recycler.BaseStateViewHolder

abstract class BaseMutableStateAdapter<
        E,
        S: BaseStateViewHolder.State,
        T : BaseStateViewHolder<E, S>,
        >
    : RecyclerView.Adapter<T>() {
    protected var items: MutableList<E> = ArrayList()
    protected var states: MutableList<S> = ArrayList()
    override fun getItemCount(): Int = items.size

    abstract fun same(it1: E, it2: E): Boolean
    abstract fun getInitialState(): S

    protected fun Int.makeView(parent: ViewGroup) =
        LayoutInflater.from(parent.context).inflate(this, parent, false)

    override fun onBindViewHolder(holder: T, position: Int) =
        holder.bind(items[position], states[position])

    open fun setupItems(data: MutableList<E>, initialState: S = getInitialState()) {
        items = data
        states = generateSequence { initialState }.take(data.size).toMutableList()
        notifyItemRangeChanged(0, data.size)
    }

    fun setItem(item: E): Int {
        val index = items.indexOfFirst { same(it, item) }
        return if(index != -1) {
            items[index] = item
            notifyItemChanged(index)
            index
        } else
            addItem(item)
    }

    fun addItem(item: E): Int {
        items.add(item)
        states.add(getInitialState())
        notifyItemInserted(items.size-1)
        return items.size-1
    }

    open fun removeItem(item: E): Int {
        val index = items.indexOfFirst { same(it, item) }
        if(index != -1) {
            items.removeAt(index)
            states.removeAt(index)
            notifyItemRemoved(index)
        }
        return index
    }

    fun setState(item: E, state: S) {
        val index = items.indexOfFirst { same(it, item) }
        if(index != -1) {
            states[index] = state
            notifyItemChanged(index)
        }
    }

}