package com.shoshin.data.interfaces.sources.remote

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.schedule.WeekSchedule

interface IScheduleRemoteSource {
    suspend fun getDefaultSchedule(): Reaction<WeekSchedule>
}