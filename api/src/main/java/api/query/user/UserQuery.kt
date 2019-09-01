package api.query.user

import domain.account.model.user.UserId
import query.Query


sealed class UserQuery : Query {
    data class FindById(val id: UserId) : UserQuery()
}