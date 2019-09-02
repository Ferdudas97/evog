package api.query.user

import query.Query


sealed class UserQuery : Query {
    data class FindById(val id: String) : UserQuery()
}