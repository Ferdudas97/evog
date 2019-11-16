package org.agh.eaiib.db.entity.event

import api.generateId
import domain.event.model.Status
import domain.event.model.details.Category
import org.bson.codecs.pojo.annotations.BsonId
import java.time.LocalDateTime


data class ParticipiantEntity(
        @BsonId
        val id: String,
        val firstName: String,
        val lastName: String,
        val fileId: String,
        val age: Int)

data class EventEntity(
        @BsonId
        val id: String,
        val imageName: String,
        val name: String,
        val guests: Set<ParticipiantEntity>,
        val organizers: ParticipiantEntity,
        val status: Status,
        val details: DetailsEntity,
        val discussionEntity: DiscussionEntity = DiscussionEntity()
)

data class DiscussionEntity(@BsonId
                            val id: String = generateId(),
                            val messages: List<MessageEntity> = listOf())
data class MessageEntity(
        @BsonId
        val id: String = generateId(),
        val text: String,
        val participant: ParticipiantEntity,
        val createdAt: LocalDateTime)

data class DetailsEntity(
        val minAllowedAge: Int?,
        val maxAllowedAge: Int?,
        val minNumberOfPeople: Int?,
        val maxNumberOfPeople: Int?,
        val description: String?,
        val startDate: LocalDateTime,
        val endTime: LocalDateTime,
        val category: Category,
        val latitude: Double,
        val longitude: Double
)