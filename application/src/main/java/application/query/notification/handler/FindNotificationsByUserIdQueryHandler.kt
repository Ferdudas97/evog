package application.query.notification.handler

import api.command.notification.dto.NotificationDto
import api.query.notification.NotificationQuery
import api.query.notification.NotificationQueryHandler
import application.mapper.toDto
import domain.account.model.user.User
import domain.account.model.user.UserId
import domain.account.repository.UserRepository
import domain.event.repository.EventRepository
import domain.notification.repository.NotificationRepository


class FindNotificationsByUserIdQueryHandler(private val notificationRepository: NotificationRepository,
                                            private val userRepository: UserRepository) : NotificationQueryHandler<NotificationQuery.FindByUserId, List<NotificationDto>> {
    override suspend fun exevute(query: NotificationQuery.FindByUserId): List<NotificationDto> {
        return notificationRepository.findAllByUserId(UserId(query.id)).map { it.toDto() }
    }
}