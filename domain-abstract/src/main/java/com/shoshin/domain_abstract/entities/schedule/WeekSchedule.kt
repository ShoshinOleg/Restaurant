package com.shoshin.domain_abstract.entities.schedule

import java.io.Serializable

data class WeekSchedule (
    val days: List<DayWeekSchedule> = listOf()
): Serializable