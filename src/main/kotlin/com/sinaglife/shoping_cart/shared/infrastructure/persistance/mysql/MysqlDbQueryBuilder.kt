package com.sinaglife.shoping_cart.shared.infrastructure.persistance.mysql

import com.sinaglife.shoping_cart.shared.domain.critreria.Criteria
import com.sinaglife.shoping_cart.shared.domain.critreria.CriteriaOperators
import jakarta.persistence.EntityManager
import jakarta.persistence.criteria.*
import org.springframework.stereotype.Service


@Service
class MysqlDbQueryBuilder(private val em: EntityManager) {

    fun <T> buildQuery(entityClass: Class<T>, criteria: Criteria?): CriteriaQuery<T> {
        val cb: CriteriaBuilder = em.criteriaBuilder
        val cq: CriteriaQuery<T> = cb.createQuery(entityClass)
        val root: Root<T> = cq.from(entityClass)

        criteria?.filters?.forEach { filter ->
            val fieldPath = filter.field.split(".")
            val filterByField = fieldPath.last()
            val value = filter.value

            if (fieldPath.size > 1) {
                val joinFieldPaths = fieldPath.dropLast(1)
                var joinField: Join<Any, Any>? = null
                for (joinFieldPath in joinFieldPaths) {
                    joinField = root.join(joinFieldPath, JoinType.LEFT)
                }
                val formattedField = joinField?.get<String>(filterByField) as Path<*>
                if (value is List<*>) {
                    val inPredicate = cb.`in`(formattedField)
                    value.forEach { inPredicate.value(it) }
                    cq.where(inPredicate)
                } else {
                    val predicate = sqlOperatorMapping(filter.operator, cb)(formattedField, value)
                    cq.where(predicate)
                }
            } else {
                val field = root.get<Any>(filterByField)
                if (value is List<*>) {
                    val inPredicate = cb.`in`(field)
                    value.forEach { inPredicate.value(it) }
                    cq.where(inPredicate)
                } else {
                    val predicate = sqlOperatorMapping(filter.operator, cb)(field, value)
                    cq.where(predicate)

                }
            }
        }
        return cq
    }

    private fun sqlOperatorMapping(operator: CriteriaOperators, cb: CriteriaBuilder): (Path<*>, Any) -> Predicate {
        return when (operator) {
            CriteriaOperators.EQUAL -> { path, value -> cb.equal(path, value) }
            CriteriaOperators.NOT_EQUAL -> { path, value -> cb.notEqual(path, value) }
            CriteriaOperators.GT -> { path, value -> cb.greaterThan(path as Path<Comparable<Any>>, value as Comparable<Any>) }
            CriteriaOperators.LT -> { path, value -> cb.lessThan(path as Path<Comparable<Any>>, value as Comparable<Any>) }
            CriteriaOperators.GT_EQUAL -> { path, value -> cb.greaterThanOrEqualTo(path as Path<Comparable<Any>>, value as Comparable<Any>) }
            CriteriaOperators.LT_EQUAL -> { path, value -> cb.lessThanOrEqualTo(path as Path<Comparable<Any>>, value as Comparable<Any>) }
            CriteriaOperators.CONTAINS -> { path, value -> cb.like(path as Path<String>, "%$value%") }
            CriteriaOperators.NOT_CONTAINS -> { path, value -> cb.notLike(path as Path<String>, "%$value%") }
            CriteriaOperators.LIKE -> { path, value -> cb.like(path as Path<String>, value as String) }
        }
    }
}
