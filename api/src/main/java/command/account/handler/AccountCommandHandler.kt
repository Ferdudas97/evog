package command.account.handler

import command.account.AccountCommand
import command.account.result.AccountCommandResult
import command.handler.DomainCommandHandler


abstract class AccountCommandHandler<C : AccountCommand, R : AccountCommandResult> : DomainCommandHandler<C, R>()
