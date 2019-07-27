package api.command.command.account.handler

import api.command.command.account.AccountCommand
import api.command.command.account.result.AccountCommandResult
import api.handler.DomainCommandHandler


abstract class AccountCommandHandler<C : AccountCommand, R : AccountCommandResult> : DomainCommandHandler<C, R>()
