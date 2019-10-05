package api.query.notification

import query.Query
import query.QueryHandler


sealed class NotificationQuery : Query {
    data class FindByUserId(val id: String) : NotificationQuery()
}

interface NotificationQueryHandler<Q : NotificationQuery, R> : QueryHandler<Q, R>