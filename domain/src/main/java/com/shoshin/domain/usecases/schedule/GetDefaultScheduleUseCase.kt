package com.shoshin.domain.usecases.schedule

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.schedule.WeekSchedule
import com.shoshin.domain_abstract.repositories.IScheduleRepository
import com.shoshin.domain_abstract.usecases.schedule.IGetDefaultScheduleUseCase
import javax.inject.Inject

class GetDefaultScheduleUseCase @Inject constructor(
    private val scheduleRepository: IScheduleRepository
): IGetDefaultScheduleUseCase {
    override suspend fun execute(): Reaction<WeekSchedule> = scheduleRepository.getDefaultSchedule()
}