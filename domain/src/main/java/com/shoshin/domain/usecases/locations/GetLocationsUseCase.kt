package com.shoshin.domain.usecases.locations

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.locations.Location
import com.shoshin.domain_abstract.repositories.ILocationRepository
import com.shoshin.domain_abstract.usecases.locations.IGetLocationsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(
    private val repository: ILocationRepository
): IGetLocationsUseCase {
    override suspend fun execute(needRemoteDownload: Boolean): Flow<Reaction<List<Location>>> =
        repository.getLocations(needRemoteDownload)
}