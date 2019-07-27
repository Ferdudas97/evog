package api.handler

import command.Command
import command.CommandHandler
import command.CommandResult
import exceptions.DomainError


abstract class DomainCommandHandler<C : Command, R : CommandResult> : CommandHandler<C, R, DomainError>()