package by.dardem.researchfactorbackend.repository.base

interface CriteriaBuilderResolver<T> {
    suspend fun findList(criteriaFunction: CriteriaFunction<T>): List<T>
    suspend fun find(criteriaFunction: CriteriaFunction<T>): T?
    suspend fun delete(criteriaFunction: CriteriaFunction<T>): Any
    suspend fun exists(criteriaFunction: CriteriaFunction<T>): Boolean
}