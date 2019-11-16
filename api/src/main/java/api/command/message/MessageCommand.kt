package api.command.message

import api.command.Command
import api.command.message.dto.MessageDto


sealed class MessageCommand : Command()

data class Create(val messageDto: MessageDto) : MessageCommand()