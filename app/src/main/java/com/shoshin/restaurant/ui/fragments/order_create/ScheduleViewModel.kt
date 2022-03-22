package com.shoshin.restaurant.ui.fragments.order_create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.schedule.WeekSchedule
import com.shoshin.domain_abstract.usecases.schedule.IGetDefaultScheduleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val getDefaultScheduleUseCase: IGetDefaultScheduleUseCase
): ViewModel() {
    private val mutableDefaultSchedule: MutableLiveData<Reaction<WeekSchedule>>
            = MutableLiveData()
    val defaultSchedule = mutableDefaultSchedule as LiveData<Reaction<WeekSchedule>>

    fun getDefaultSchedule() {
        viewModelScope.launch {
            mutableDefaultSchedule.value = Reaction.Progress()
            mutableDefaultSchedule.value = getDefaultScheduleUseCase.execute()
        }
    }
}