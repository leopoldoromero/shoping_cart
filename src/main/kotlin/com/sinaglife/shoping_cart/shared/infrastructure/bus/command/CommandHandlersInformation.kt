package com.sinaglife.shoping_cart.shared.infrastructure.bus.command

import com.sinaglife.shoping_cart.shared.domain.bus.command.Command
import com.sinaglife.shoping_cart.shared.domain.bus.command.CommandHandler
import com.sinaglife.shoping_cart.shared.domain.bus.command.CommandNotRegisteredError
import org.reflections.Reflections
import org.springframework.stereotype.Service
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
@Service
@SuppressWarnings
class CommandHandlersInformation(
    private final var indexedCommandHandlers: HashMap<Class<out Command?>, Class<out CommandHandler<*>>>,
    ) {
    init {
        val reflections = Reflections("com.sinaglife")
        val classes = reflections.getSubTypesOf(
            CommandHandler::class.java
        )

        indexedCommandHandlers = formatHandlers(classes)
    }

    @Throws(CommandNotRegisteredError::class)
    fun search(commandClass: Class<out Command?>): Class<out CommandHandler<*>> {
        val commandHandlerClass = indexedCommandHandlers[commandClass] ?: throw CommandNotRegisteredError(commandClass as KClass<out Command>)

        return commandHandlerClass
    }

    private fun formatHandlers(
        queryHandlers: Set<Class<out CommandHandler<*>>>
    ): HashMap<Class<out Command?>, Class<out CommandHandler<*>>> {
        val handlers = HashMap<Class<out Command?>, Class<out CommandHandler<*>>>()

        for (handler in queryHandlers) {
            val paramType: ParameterizedType = handler.genericInterfaces[0] as ParameterizedType
            val queryClass =
                paramType.actualTypeArguments[0] as Class<out Command?>

            handlers[queryClass] = handler
        }

        return handlers
    }
}
