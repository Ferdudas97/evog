package event.model.participiant

import account.user.info.FirstName
import account.user.info.LastName


sealed class Participant(open val id: ParticipantId, open val firstName: FirstName, open val lastName: LastName, open val age: Age)

data class Age(val int: Int)

data class ParticipantId(val id: String)

data class Organizator(override val id: ParticipantId,
                       override val firstName: FirstName,
                       override val lastName: LastName,
                       override val age: Age) : Participant(id, firstName, lastName, age)


data class Guest(override val id: ParticipantId,
                 override val firstName: FirstName,
                 override val lastName: LastName,
                 override val age: Age) : Participant(id, firstName, lastName, age)