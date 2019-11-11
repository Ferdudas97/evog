package org.agh.eaiib.db.dao.images

import com.mongodb.client.gridfs.GridFSBucket
import org.bson.types.ObjectId
import java.util.*


interface ImageDao {
    suspend fun findById(id: String): ByteArray
    suspend fun save(file: ByteArray): String
    suspend fun delete(id: String)
}


class ImageDaoImpl(private val gridFSBucket: GridFSBucket) : ImageDao {

    override suspend fun findById(id: String): ByteArray {
        return gridFSBucket.openDownloadStream(id).readBytes()
    }

    override suspend fun save(file: ByteArray) : String {
        val filename = UUID.randomUUID().toString()
        gridFSBucket.uploadFromStream(filename, file.inputStream())
        return filename
    }

    override suspend fun delete(id: String) {
        gridFSBucket.delete(ObjectId(id))
    }
}