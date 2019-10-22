//package org.agh.eaiib.db.dao.images
//
//import com.mongodb.client.gridfs.GridFSBucket
//import org.agh.eaiib.db.dao.GenericDao
//import java.io.File
//import java.nio.ByteBuffer
//import java.util.*
//
//
//interface ImageDao : GenericDao<File, String> {
//}
//
//
//class ImageDaoImpl(private val gridFSBucket: GridFSBucket) : ImageDao {
//
//    override suspend fun findById(id: String): File? {
//    gridFSBucket.openDownloadStream(id).gridFSFile.
//    }
//
//    override suspend fun save(entity: File) {
//        gridFSBucket.uploadFromStream(entity.name, entity.inputStream())
//    }
//
//    override suspend fun delete(id: String) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override suspend fun update(entity: File) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//}