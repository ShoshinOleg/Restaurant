package com.shoshin.restaurant.ui.common.switcher

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.shoshin.restaurant.R
import com.shoshin.restaurant.databinding.SwitcherBinding

class TwoButtonsSwitcher @JvmOverloads constructor(
context: Context,
attrs: AttributeSet? = null,
defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: SwitcherBinding
    var firstClickListener: () -> Unit = {}
    var secondClickListener: () -> Unit = {}

    var firstButtonName: String? = ""
        set(value) {
            binding.first.text = value
            binding.firstCardText.text = value
            field = value
        }

    var secondButtonName: String? = ""
        set(value) {
            binding.second.text = value
            binding.secondCardText.text = value
            field = value
        }

    init {
        val layoutInflater = LayoutInflater.from(context)
        binding = SwitcherBinding.inflate(layoutInflater, this, false)
        addView(binding.root)
        setup(context, attrs)

        binding.first.setOnClickListener {
            binding.secondCard.visibility = View.INVISIBLE
            binding.firstCard.visibility = View.VISIBLE
            firstClickListener.invoke()
        }
        binding.second.setOnClickListener {
            binding.firstCard.visibility = View.INVISIBLE
            binding.secondCard.visibility = View.VISIBLE
            secondClickListener.invoke()
        }
    }

    private fun setup(context: Context, attrs: AttributeSet?) {
        if(attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.TwoButtonsSwitcher)
            firstButtonName = ta.getString(R.styleable.TwoButtonsSwitcher_firstText)
            secondButtonName = ta.getString(R.styleable.TwoButtonsSwitcher_secondText)
            ta.recycle()
        }
    }
}
