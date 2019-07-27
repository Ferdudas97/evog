package api.command.command.account

import api.command.command.account.result.AccountCommandResult
import api.handler.DomainCommandHandler


abstract class AccountCommandHandler<C : AccountCommand, R : AccountCommandResult> : DomainCommandHandler<C, R>()
