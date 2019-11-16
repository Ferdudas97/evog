package api.query.user

import api.query.Query


sealed class UserQuery : Query {
    data class FindById(val id: String) : UserQuery()
}