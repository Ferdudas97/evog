package api

import arrow.core.Either
import arrow.core.Right
import arrow.core.left


fun <T, R> R?.toEither(f: () -> T) = when (this) {
    null -> f().left()
    else -> Right(this)
}

fun <A, B> Either<A, B>.peek(f: (B) -> Unit): Either<A, B> = when (this) {

    is Either.Left -> this
    is Either.Right -> {
        f(this.b)
        this
    }
}