package api.query.notification

import api.query.Query
import api.query.QueryHandler


sealed class NotificationQuery : Query {
    data class FindByUserId(val id: String) : NotificationQuery()
}

interface NotificationQueryHandler<Q : NotificationQuery, R> : QueryHandler<Q, R>