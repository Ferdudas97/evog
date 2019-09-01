package api.query.user

import query.QueryHandler


interface UserQueryHandler<Q : UserQuery, R> : QueryHandler<Q, R>