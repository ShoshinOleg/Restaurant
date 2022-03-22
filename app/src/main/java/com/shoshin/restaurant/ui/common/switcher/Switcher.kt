package com.shoshin.restaurant.ui.common.switcher

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.shoshin.restaurant.R

class Switcher @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {
    var firstClickListener: () -> Unit = {}
    var secondClickListener: () -> Unit = {}

    var selectedTab: Int = 0
        private set
    private val switcherView = TwoButtonsSwitcher(context, attrs, defStyleAttr).apply {
        firstClickListener = {
            selectedTab = 0
            secondChild?.visibility = View.GONE
            firstChild?.visibility = View.VISIBLE
            this@Switcher.firstClickListener()
            invalidate()
        }
        secondClickListener = {
            selectedTab = 1
            firstChild?.visibility = View.GONE
            secondChild?.visibility = View.VISIBLE
            this@Switcher.secondClickListener()
            invalidate()
        }
    }

    init {
        this.addView(switcherView)
        setup(context, attrs)

    }

    private val firstChild: View?
        get() = if(childCount > 1) getChildAt(1) else null
    private val secondChild: View?
        get() = if(childCount > 2) getChildAt(2) else null

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChild1(switcherView, heightMeasureSpec, widthMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val childHeight: Int
        if(selectedTab == 0) {
            firstChild?.let { measureChild1(it, heightMeasureSpec, widthMeasureSpec) }
            firstChild?.visibility = View.VISIBLE
            secondChild?.visibility = View.GONE
            childHeight = firstChild?.measuredHeight ?: 0
        } else {
            secondChild?.let { measureChild1(it, heightMeasureSpec, widthMeasureSpec) }
            secondChild?.visibility = View.VISIBLE
            firstChild?.visibility = View.GONE
            childHeight = secondChild?.measuredHeight ?: 0
        }
        val switcherMeasuredHeight = switcherView.measuredHeight
        setMeasuredDimension(width, switcherMeasuredHeight + childHeight)
    }

    private fun measureChild1(child: View, heightMeasureSpec: Int, widthMeasureSpec: Int) {
        val childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        child.measure(widthMeasureSpec, childHeightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        switcherView.layout(0,0, switcherView.measuredWidth, switcherView.measuredHeight)
        if(selectedTab == 0) {
            firstChild?.layout(
                0,
                switcherView.measuredHeight,
                firstChild?.measuredWidth ?: 0,
                switcherView.measuredHeight + (firstChild?.measuredHeight ?: 0)
            )
        } else {
            secondChild?.layout(
                0,
                switcherView.measuredHeight,
                secondChild?.measuredWidth ?: 0,
                switcherView.measuredHeight + (secondChild?.measuredHeight ?: 0)
            )
        }
    }

    private fun checkChildCount() {
        if(childCount > 2) error("LC should not contain more than 2 children")
    }

    private fun setup(context: Context, attrs: AttributeSet?) {
        if(attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.Switcher)
            switcherView.firstButtonName = ta.getString(R.styleable.Switcher_firstButtonText)
            switcherView.secondButtonName = ta.getString(R.styleable.Switcher_secondButtonText)
            ta.recycle()
        }
    }
}