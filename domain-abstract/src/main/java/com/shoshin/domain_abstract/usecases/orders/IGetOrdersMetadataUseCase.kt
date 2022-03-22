package com.shoshin.domain_abstract.usecases.orders

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.order.OrderMetadata

interface IGetOrdersMetadataUseCase {
    suspend fun execute(): Reaction<List<OrderMetadata>>
}