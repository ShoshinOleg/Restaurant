package com.shoshin.domain_abstract.usecases.locations

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.locations.Location
import kotlinx.coroutines.flow.Flow

interface IGetLocationsUseCase {
    suspend fun execute(needRemoteDownload: Boolean = false): Flow<Reaction<List<Location>>>
}