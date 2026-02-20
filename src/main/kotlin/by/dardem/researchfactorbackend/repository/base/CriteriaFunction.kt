package by.dardem.researchfactorbackend.repository.base

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root

@FunctionalInterface
fun interface CriteriaFunction<T> {
    fun apply(root: Root<T>, builder: CriteriaBuilder): MutableList<Predicate>
}
