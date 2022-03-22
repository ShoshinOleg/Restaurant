package com.shoshin.data.remote.sources

import com.shoshin.data.interfaces.services.remote.IScheduleRemoteService
import com.shoshin.data.interfaces.sources.remote.IScheduleRemoteSource
import com.shoshin.data.remote.main.NetworkHelper
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.schedule.WeekSchedule
import javax.inject.Inject

class ScheduleRemoteSource @Inject constructor(
    private val scheduleRemoteService: IScheduleRemoteService
): IScheduleRemoteSource {
    override suspend fun getDefaultSchedule(): Reaction<WeekSchedule> =
        NetworkHelper.safeApiCall {
            scheduleRemoteService.getDefaultSchedule()
        }
}