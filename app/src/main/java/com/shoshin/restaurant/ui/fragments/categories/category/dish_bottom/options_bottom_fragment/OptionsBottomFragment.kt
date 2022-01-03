package com.shoshin.restaurant.ui.fragments.categories.category.dish_bottom.options_bottom_fragment

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shoshin.domain_abstract.entities.dish.DishOption
import com.shoshin.restaurant.R
import com.shoshin.restaurant.common.argument
import com.shoshin.restaurant.databinding.OptionsBottomFragmentBinding


class OptionsBottomFragment: BottomSheetDialogFragment() {
    private lateinit var binding: OptionsBottomFragmentBinding
    private var adapter: OptionAdapterVP? = null
    private var behavior: BottomSheetBehavior<View>? =null
    private var listOptions: MutableList<DishOption>? = null

    private var options: HashMap<String, DishOption> by argument()
    private var currentOption: DishOption by argument()

    companion object {
        val TAG = OptionsBottomFragment::class.java.simpleName
        private val REQUEST_KEY_DEFAULT = "$TAG:REQUEST_KEY_DEFAULT"
        private val RESPONSE_IS_ADD_KEY = "$TAG:RESPONSE_IS_ADD_KEY"

        fun setupListener(
            manager: FragmentManager,
            lifecycleOwner: LifecycleOwner,
            listener: (isAdd: Boolean) -> Unit
        ) {
            manager.setFragmentResultListener(
                REQUEST_KEY_DEFAULT,
                lifecycleOwner,
                { key, bundle ->
                    val isAdd = bundle.get(RESPONSE_IS_ADD_KEY) as Boolean
                    listener.invoke(isAdd)
                }
            )
        }

        fun show(
            manager: FragmentManager,
            options: HashMap<String, DishOption>,
            option: DishOption
        ) {
            OptionsBottomFragment().apply {
                this.options = options
                this.currentOption = option
            }.show(manager, TAG)
        }
    }

    private fun initViewPager() {
        adapter = OptionAdapterVP(
            childFragmentManager,
            FragmentStatePagerAdapter.POSITION_UNCHANGED,
            requireContext()
        )
        binding.viewPager.adapter = adapter
        listOptions = mutableListOf<DishOption>()
        for (optionKey in options.keys.sorted()) {
            listOptions?.add(options[optionKey]!!)
        }
        adapter?.setupOptions(listOptions!!)
    }

    private fun initTabLayout() {
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        for(index in listOptions!!.indices) {
            binding.tabLayout.getTabAt(index)?.text = listOptions!![index].name
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }


    private fun setupResult() {
        parentFragmentManager.setFragmentResult(
            REQUEST_KEY_DEFAULT, bundleOf(
            RESPONSE_IS_ADD_KEY to true
        ))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dial =  super.onCreateDialog(savedInstanceState)
        val view = View.inflate(context, R.layout.options_bottom_fragment, null)
        binding = OptionsBottomFragmentBinding.bind(view)

        val frameLayout = view.findViewById<ConstraintLayout>(R.id.mainConstraint)
        val params = frameLayout.layoutParams
        params.height = Resources.getSystem().displayMetrics.heightPixels
        frameLayout.layoutParams = params

        dial.setContentView(view)
        behavior = BottomSheetBehavior.from(view.parent as View)
        binding.complete.setOnClickListener {
            setupResult()
            dismiss()
        }
        initViewPager()
        initTabLayout()
        setCurrentOption()
        binding.close.setOnClickListener {
            dismiss()
        }

        return dial
    }

    private fun setCurrentOption() {
        val currentOptionIndex = listOptions?.indexOfFirst { it.id == currentOption.id }
        currentOptionIndex?.let {
            if(currentOptionIndex != -1) {
                binding.viewPager.currentItem = currentOptionIndex
            }
        }
    }

    override fun onStart() {
        super.onStart()
        behavior?.state = BottomSheetBehavior.STATE_EXPANDED
        behavior?.skipCollapsed = true
    }
}