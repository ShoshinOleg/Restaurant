package com.shoshin.domain.usecases.locations

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.locations.Location
import com.shoshin.domain_abstract.repositories.ILocationRepository
import com.shoshin.domain_abstract.usecases.locations.IRemoveLocationUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoveLocationUseCase @Inject constructor(
    private val repository: ILocationRepository
): IRemoveLocationUseCase {
    override suspend fun execute(location: Location): Flow<Reaction<Location>> =
        repository.removeLocation(location)
}