package common


interface ImageRepository {
    suspend fun findById(id: String): ByteArray
    suspend fun save(file: ByteArray): String
    suspend fun delete(id: String)
}