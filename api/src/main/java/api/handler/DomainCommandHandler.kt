package api.handler

import api.command.Command
import api.command.CommandHandler
import api.command.CommandResult
import exceptions.DomainError


abstract class DomainCommandHandler<C : Command, R : CommandResult> : CommandHandler<C, R, DomainError>()