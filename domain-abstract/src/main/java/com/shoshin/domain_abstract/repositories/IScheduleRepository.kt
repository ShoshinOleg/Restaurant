package com.shoshin.domain_abstract.repositories

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.schedule.WeekSchedule

interface IScheduleRepository {
    suspend fun getDefaultSchedule(): Reaction<WeekSchedule>
}