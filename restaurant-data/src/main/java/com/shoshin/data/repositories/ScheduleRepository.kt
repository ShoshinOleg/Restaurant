package com.shoshin.data.repositories

import com.shoshin.data.interfaces.sources.remote.IScheduleRemoteSource
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.schedule.WeekSchedule
import com.shoshin.domain_abstract.repositories.IScheduleRepository
import javax.inject.Inject

class ScheduleRepository @Inject constructor(
    private val scheduleRemoteSource: IScheduleRemoteSource
): IScheduleRepository {
    override suspend fun getDefaultSchedule(): Reaction<WeekSchedule> =
        scheduleRemoteSource.getDefaultSchedule()
}