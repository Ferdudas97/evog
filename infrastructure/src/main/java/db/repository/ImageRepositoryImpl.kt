package org.agh.eaiib.db.repository

import common.ImageRepository
import org.agh.eaiib.db.dao.images.ImageDao


class ImageRepositoryImpl (private val imageDao: ImageDao ) : ImageRepository {

    override suspend fun findById(id: String): ByteArray {
        return  imageDao.findById(id)
    }

    override suspend fun save(file: ByteArray): String {
        return  imageDao.save(file)
    }

    override suspend fun delete(id: String) {
        imageDao.delete(id)
    }
}