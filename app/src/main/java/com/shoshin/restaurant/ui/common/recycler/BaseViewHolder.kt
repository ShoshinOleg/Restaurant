package com.shoshin.restaurant.ui.common.recycler

import android.view.View
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<E>(view: View): RecyclerView.ViewHolder(view) {
    protected var item: E? = null

    @CallSuper
    open fun bind(item: E) {
        this.item = item
    }
}