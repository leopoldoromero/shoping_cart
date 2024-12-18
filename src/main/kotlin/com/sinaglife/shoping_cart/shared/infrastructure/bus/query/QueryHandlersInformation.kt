package com.sinaglife.shoping_cart.shared.infrastructure.bus.query

import com.sinaglife.shoping_cart.shared.domain.bus.query.Query
import com.sinaglife.shoping_cart.shared.domain.bus.query.QueryHandler
import com.sinaglife.shoping_cart.shared.domain.bus.query.QueryNotRegisteredError
import org.reflections.Reflections
import org.springframework.stereotype.Service
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass


@Suppress("UNCHECKED_CAST")
@Service
@SuppressWarnings
class QueryHandlersInformation(
    private final var indexedQueryHandlers: HashMap<Class<out Query?>, Class<out QueryHandler<*, *>>>,
) {
    init {
        val reflections = Reflections("com.sinaglife")
        val classes = reflections.getSubTypesOf(
            QueryHandler::class.java
        )

        indexedQueryHandlers = formatHandlers(classes)
    }

    private fun formatHandlers(
        queryHandlers: Set<Class<out QueryHandler<*, *>>>
    ): HashMap<Class<out Query?>, Class<out QueryHandler<*, *>>> {
        val handlers = HashMap<Class<out Query?>, Class<out QueryHandler<*, *>>>()

        for (handler in queryHandlers) {
            val paramType: ParameterizedType = handler.genericInterfaces[0] as ParameterizedType
            val queryClass =
                paramType.actualTypeArguments[0] as Class<out Query?>

            handlers[queryClass] = handler
        }

        return handlers
    }

    @Throws(QueryNotRegisteredError::class)
    fun search(queryClass: Class<out Query?>): Class<out QueryHandler<*, *>> {
        val queryHandlerClass = indexedQueryHandlers[queryClass] ?: throw QueryNotRegisteredError(queryClass as KClass<out Query>)

        return queryHandlerClass
    }
}
