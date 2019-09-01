package api.query.event

import query.Query
import query.QueryHandler


interface EventQuery : Query

interface EventQueryHandler<Q : EventQuery, R> : QueryHandler<Q, R>