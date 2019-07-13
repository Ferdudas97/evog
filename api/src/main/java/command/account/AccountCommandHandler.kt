package command.account

import command.account.result.AccountCommandResult
import command.handler.DomainCommandHandler


abstract class AccountCommandHandler<C : AccountCommand, R : AccountCommandResult> : DomainCommandHandler<C, R>()
