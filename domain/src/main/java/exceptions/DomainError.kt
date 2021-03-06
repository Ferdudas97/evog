package exceptions


abstract class DomainError
data class ValidationError(val msg: String) : DomainError()
data class SavingError(val msg: String) : DomainError()
data class UpdateError(val msg: String) : DomainError()
data class DeleteError(val msg: String) : DomainError()
data class ItemNotFoundError(val msg: String) : DomainError()
data class UserIsAlreadyAssigned(val msg: String) : DomainError()
data class CannotRemoveOrganizer(val msg: String): DomainError()
data class UserNotAuthorized(val msg: String) : DomainError()