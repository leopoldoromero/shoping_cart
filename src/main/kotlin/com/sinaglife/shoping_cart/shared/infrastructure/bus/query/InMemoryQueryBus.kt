package com.sinaglife.shoping_cart.shared.infrastructure.bus.query

import com.sinaglife.shoping_cart.shared.domain.bus.query.Query
import com.sinaglife.shoping_cart.shared.domain.bus.query.QueryBus
import com.sinaglife.shoping_cart.shared.domain.bus.query.QueryHandler
import com.sinaglife.shoping_cart.shared.domain.bus.query.QueryResponse
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service

@Service
@Suppress("UNCHECKED_CAST")
class InMemoryQueryBus(
    private  val information: QueryHandlersInformation,
    private val context: ApplicationContext,
): QueryBus {
    override fun ask(query: Query): QueryResponse {
        val queryHandlerClass = information.search(query.javaClass)
        val handler: QueryHandler<Query, QueryResponse> = context.getBean(queryHandlerClass) as QueryHandler<Query, QueryResponse>
        return handler.handle(query)
    }
}