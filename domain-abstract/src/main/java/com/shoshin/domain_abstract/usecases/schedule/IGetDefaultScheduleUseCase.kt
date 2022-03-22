package com.shoshin.domain_abstract.usecases.schedule

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.schedule.WeekSchedule

interface IGetDefaultScheduleUseCase {
    suspend fun execute(): Reaction<WeekSchedule>
}