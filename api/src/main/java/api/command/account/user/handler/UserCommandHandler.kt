package api.command.account.user.handler

import api.command.account.user.UserCommand
import api.command.account.user.UserCommandResult
import api.handler.DomainCommandHandler


abstract class UserCommandHandler<C : UserCommand, R : UserCommandResult> : DomainCommandHandler<C, R>()