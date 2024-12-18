package com.sinaglife.shoping_cart.shared.infrastructure.bus.command

import com.sinaglife.shoping_cart.shared.domain.bus.command.Command
import com.sinaglife.shoping_cart.shared.domain.bus.command.CommandBus
import com.sinaglife.shoping_cart.shared.domain.bus.command.CommandHandler
import com.sinaglife.shoping_cart.shared.domain.bus.command.CommandHandlerExecutionError
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service

@Suppress("UNCHECKED_CAST")
@Service
class InMemoryCommandBus(
    private  val information: CommandHandlersInformation,
    private val context: ApplicationContext,
): CommandBus {

    @Throws(CommandHandlerExecutionError::class)
    override fun dispatch(command: Command) {
        try {
            val commandHandlerClass = information.search(command.javaClass)
            val handler: CommandHandler<Command> = context.getBean(commandHandlerClass) as CommandHandler<Command>
            handler.handle(command)
            println("Handler:::" + " " + commandHandlerClass.name)
        } catch (error: Throwable) {
            println("[[DISPATCH_ERROR]]::: $error")
            throw CommandHandlerExecutionError(error)
        }
    }
}