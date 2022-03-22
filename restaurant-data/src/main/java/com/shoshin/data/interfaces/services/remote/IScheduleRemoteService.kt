package com.shoshin.data.interfaces.services.remote

import com.shoshin.data.remote.main.Constants
import com.shoshin.domain_abstract.entities.schedule.WeekSchedule
import retrofit2.http.GET

interface IScheduleRemoteService {
    @GET("${Constants.SCHEDULES_URL}/default")
    suspend fun getDefaultSchedule(): WeekSchedule
}