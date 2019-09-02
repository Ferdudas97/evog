package api.command.account.handler

import api.command.account.AccountCommand
import api.command.account.result.AccountCommandResult
import api.handler.DomainCommandHandler


abstract class AccountCommandHandler<C : AccountCommand, R : AccountCommandResult> : DomainCommandHandler<C, R>()