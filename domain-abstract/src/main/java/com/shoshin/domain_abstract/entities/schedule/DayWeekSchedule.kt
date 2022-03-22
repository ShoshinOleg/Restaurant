package com.shoshin.domain_abstract.entities.schedule

import java.io.Serializable

data class DayWeekSchedule (
    var dayOfWeek: Int? = null,
    var nameDay: String? = null,
    var startTime: String? = null,
    var endTime: String? = null,
    var isNotWork: Boolean = false
): Serializable