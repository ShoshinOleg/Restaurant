package com.shoshin.restaurant.ui.fragments.order_create

import com.shoshin.domain_abstract.entities.schedule.DayWeekSchedule
import com.shoshin.domain_abstract.entities.schedule.WeekSchedule
import java.io.Serializable
import java.util.*

class AvailableTimeRange: Serializable {
    private var dayWeekSchedules: List<DayWeekSchedule>? = null
    var startWorkCalendar: Calendar = Calendar.getInstance()
    var endWorkCalendar: Calendar = Calendar.getInstance()
    var canDoOrder = true

    companion object {
        fun getAvailableTimeRange(
            weekSchedule: WeekSchedule,
            targetDate: Date,
            delay: Int = 0,
            delayTimeUnit: Int = Calendar.MINUTE
        ): AvailableTimeRange {
            val range = AvailableTimeRange()
            range.dayWeekSchedules = weekSchedule.days
            val calendar = Calendar.getInstance()
            calendar.time = targetDate
            calendar.add(delayTimeUnit, delay)
            val dayOfWeek = getDayOfWeek(calendar)
            if(weekSchedule.days[dayOfWeek].isNotWork) {
                range.canDoOrder = false
            }

            if(range.initStartAndEndTime(calendar)) {
                if(isCurrentDay(calendar)) {
                    range.setupStartTimeForCurrentDay(calendar)
                }
            }
            return range
        }

        private fun isCurrentDay(calendar: Calendar): Boolean {
            return calendar.get(Calendar.DATE) ==
                    Calendar.getInstance().get(Calendar.DATE)
        }

        private fun getDayOfWeek(calendar: Calendar): Int {
            var day = calendar.get(Calendar.DAY_OF_WEEK)
            day = (day-2 + 7) % 7
            return day
        }
    }


    //API methods
    fun getTimeList(): List<Date>? {
        if(!canDoOrder) {
            return null
        }
        val list = mutableListOf<Date>()
        val dayCalendar = Calendar.getInstance()
        dayCalendar.time = startWorkCalendar.time
        while(
            dayCalendar < endWorkCalendar
        ) {
            list.add(dayCalendar.time)
            dayCalendar.add(Calendar.MINUTE, 15)
        }
        return list
    }

    fun firstAvailableDate(): Date? {
        if(!canDoOrder) {
            return null
        } else {
            return startWorkCalendar.time
        }
    }

    //!API methods


    //internalMethods
    private fun initStartAndEndTime(calendar: Calendar): Boolean {
        startWorkCalendar.time = calendar.time
        endWorkCalendar.time = calendar.time
        val dayOfWeek = getDayOfWeek(startWorkCalendar)
        val daySchedule = dayWeekSchedules!![dayOfWeek]
        if(daySchedule.startTime == null) {
            canDoOrder = false
            return false
        } else {
            val hour_minute = daySchedule.startTime!!.split(':')
            startWorkCalendar.set(Calendar.HOUR_OF_DAY, hour_minute[0].toInt())
            startWorkCalendar.set(Calendar.MINUTE, hour_minute[1].toInt())
            startWorkCalendar.set(Calendar.SECOND, 0)
        }
        if(daySchedule.endTime == null) {
            canDoOrder = false
            return false
        } else {
            val hour_minute = daySchedule.endTime!!.split(':')
            endWorkCalendar.set(Calendar.HOUR_OF_DAY, hour_minute[0].toInt())
            endWorkCalendar.set(Calendar.MINUTE, hour_minute[1].toInt())
            endWorkCalendar.set(Calendar.SECOND, 0)
        }
        if(endWorkCalendar < startWorkCalendar) {
            canDoOrder = false
            return false
        }
        return true
    }

    private fun setupStartTimeForCurrentDay(calendar: Calendar): Boolean {
        val minutes = calendar.get(Calendar.MINUTE)
        val k = minutes / 15
        if(k==3) {
            calendar.add(Calendar.HOUR_OF_DAY, 1)
            calendar.set(Calendar.MINUTE, 0)
        } else {
            calendar.set(Calendar.MINUTE, 15*k)
        }
        if(startWorkCalendar < calendar && calendar < endWorkCalendar) {
            startWorkCalendar.time = calendar.time
        } else if(calendar > endWorkCalendar) {
            canDoOrder = false
            return false
        }
        return true
    }
    //!internalMethods
}