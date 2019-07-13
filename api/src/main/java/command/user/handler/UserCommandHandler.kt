package command.user.handler

import command.handler.DomainCommandHandler
import command.user.UserCommand
import command.user.UserCommandResult


abstract class UserCommandHandler<C : UserCommand, R : UserCommandResult> : DomainCommandHandler<C, R>()