package api.query.user

import api.query.QueryHandler


interface UserQueryHandler<Q : UserQuery, R> : QueryHandler<Q, R>