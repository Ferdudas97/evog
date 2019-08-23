package api.command.event

import api.command.event.result.EventResult
import api.handler.DomainCommandHandler


abstract class EventCommandHandler<C : EventCommand, R : EventResult> : DomainCommandHandler<C, R>()