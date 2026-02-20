package by.dardem.researchfactorbackend.repository.base

interface ReactiveCrudDao<T, ID>: CriteriaBuilderResolver<T> {
    suspend fun findById(id: ID): T?
    suspend fun findAll(): List<T>
    suspend fun save(entity: T): T
    suspend fun saveOrUpdate(entity: T): T
    suspend fun saveAll(entities: Iterable<T>)
}