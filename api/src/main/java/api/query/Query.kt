package api.query

interface Query

interface QueryHandler<in Q : Query, R> {

    suspend fun exevute(query: Q): R
}
