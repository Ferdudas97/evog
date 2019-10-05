package api.command.notification.dto

import api.command.event.dto.ParticipantDto
import api.command.event.dto.pattern
import com.fasterxml.jackson.annotation.JsonFormat
import domain.notification.State
import java.time.LocalDateTime


data class NotificationDto(
        val id: String,
        val guest: ParticipantDto,
        val eventId: String,
        val content: String,
        @JsonFormat(pattern = pattern)
        val creationTime: LocalDateTime,
        val state: State
)