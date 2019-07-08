package account

import org.joda.time.DateTime
import user.User

data class Login(val value: String)
data class Password(val value: String)
data class CreationDate(val date: DateTime)

// Todo: add it if everything works
enum class State {
    ACTIVE, INACTIVE, DELETED
}
data class Account(val login: Login,
                   val password: Password,
                   val creationDate: CreationDate,
                   val user: User)
