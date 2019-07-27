package api.command.command.account.user.handler

import api.command.command.account.user.UserCommand
import api.command.command.account.user.UserCommandResult
import api.handler.DomainCommandHandler


abstract class UserCommandHandler<C : UserCommand, R : UserCommandResult> : DomainCommandHandler<C, R>()