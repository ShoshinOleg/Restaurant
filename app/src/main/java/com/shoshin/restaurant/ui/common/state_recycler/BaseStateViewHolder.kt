package com.shoshin.restaurant.ui.common.state_recycler

import android.view.View
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView

abstract class BaseStateViewHolder<E, T: BaseStateViewHolder.State>(
    view: View
): RecyclerView.ViewHolder(view) {
    interface State

    protected var item: E? = null
//    protected var state: T? = null

    @CallSuper
    open fun bind(item: E, state: T) {
        this.item = item
//        this.state = state
    }
}