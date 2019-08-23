package org.agh.eaiib.db.dao


interface GenericDao<Entity, Id> {
    suspend fun findById(id: Id): Entity?
    suspend fun save(entity: Entity)
    suspend fun delete(id: Id)
    suspend fun update(entity: Entity)
}