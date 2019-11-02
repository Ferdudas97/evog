package api.command.message

import api.command.message.dto.MessageDto
import command.Command


sealed class MessageCommand : Command()

data class Create(val messageDto: MessageDto) : MessageCommand()