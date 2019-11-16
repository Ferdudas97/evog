package api.command

import arrow.core.Either


open class Command

abstract class CommandHandler<in C : Command, out R : CommandResult, out E> {

    abstract suspend fun handle(command: C): Either<E, R>
}

open class CommandResult



