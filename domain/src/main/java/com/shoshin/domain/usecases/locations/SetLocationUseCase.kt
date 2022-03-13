package com.shoshin.domain.usecases.locations

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.locations.Location
import com.shoshin.domain_abstract.repositories.ILocationRepository
import com.shoshin.domain_abstract.usecases.locations.ISetLocationUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetLocationUseCase @Inject constructor(
    private val locationsRepository: ILocationRepository
): ISetLocationUseCase {
    override suspend fun execute(location: Location): Flow<Reaction<Location>> =
        locationsRepository.setLocation(location)
}