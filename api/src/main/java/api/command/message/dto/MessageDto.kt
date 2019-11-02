package api.command.message.dto

import api.command.event.dto.ParticipantDto

data class ConversationDto(val conversationId: String, val participantsId: List<String>, val messages: List<MessageDto>)
data class MessageDto(val content: String, val participantDto: ParticipantDto)
