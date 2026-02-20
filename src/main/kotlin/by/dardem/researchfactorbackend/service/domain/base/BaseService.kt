package by.dardem.researchfactorbackend.service.domain.base

import by.dardem.researchfactorbackend.repository.base.ReactiveCrudDao

abstract class BaseService<T, ID>(
    private val dao: ReactiveCrudDao<T, ID>
) {
    suspend fun findById(id: ID): T? =
        dao.findById(id)

    suspend fun findAll(): List<T> =
        dao.findAll()

    suspend fun save(entity: T): T =
        dao.save(entity)

    suspend fun saveOrUpdate(entity: T): T =
        dao.saveOrUpdate(entity)

    suspend fun saveAll(entities: Iterable<T>) {
        dao.saveAll(entities)
    }
}