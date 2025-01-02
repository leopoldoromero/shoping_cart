package com.sinaglife.shoping_cart.cart.infrastructure.persistance.mysql

import com.sinaglife.shoping_cart.cart.domain.Cart
import com.sinaglife.shoping_cart.cart.domain.CartId
import com.sinaglife.shoping_cart.cart.domain.CartPrimitives
import com.sinaglife.shoping_cart.cart.domain.CartRepository
import com.sinaglife.shoping_cart.cart.domain.cart_discount.CartDiscountPrimitives
import com.sinaglife.shoping_cart.cart.domain.cart_item.CartItemPrimitives
import com.sinaglife.shoping_cart.read_model.domain.CartReadModel
import com.sinaglife.shoping_cart.shared.domain.critreria.Criteria
import com.sinaglife.shoping_cart.shared.infrastructure.persistance.mysql.MysqlDbQueryBuilder
import jakarta.persistence.EntityManager
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.JoinType
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository(value = "CartMysqlRepository")
class CartMysqlRepository(
    private val client: CartSpringJpaClientRepository,
    private val em: EntityManager,
): CartRepository {
    private val queryBuilder: MysqlDbQueryBuilder = MysqlDbQueryBuilder(em)
    override fun save(cart: Cart) {
        val cartEntity = CartDbEntity.fromDomainEntity(cart)
        client.save(cartEntity)
    }

    override fun find(id: CartId): Cart? {
        val cb: CriteriaBuilder = em.criteriaBuilder
        val cq: CriteriaQuery<CartDbEntity> = cb.createQuery(CartDbEntity::class.java)

        val root: Root<CartDbEntity> = cq.from(CartDbEntity::class.java)

        val itemsJoin = root.join<CartDbEntity, CartItemDbEntity>("items", JoinType.LEFT)
        val discountJoin = root.join<CartDbEntity, CartDiscountDbEntity>("discount", JoinType.LEFT)

        cq.where(cb.equal(root.get<String>("id"), id.value.toString()))

        val query = em.createQuery(cq)
        val dbEntity = query.singleResult
        val cartOrNull = CartPrimitives(
            id = dbEntity.id,
            items = dbEntity.items.map { item -> CartItemPrimitives(
                id = item.id,
                quantity = item.quantity,
                price = item.price,
            ) }.toMutableList(),
            discount = dbEntity.discount?.let { it -> CartDiscountPrimitives(
                id = it.id,
                type = it.type,
                code = it.code,
                amount = it.amount,
                individualUse = it.individualUse,
            ) },
            createdAt = dbEntity.createdAt,
            updatedAt = dbEntity.updatedAt,
            customerId = dbEntity.customerId
        )
        return Cart.fromPrimitives(cartOrNull)
    }

    override fun findByCriteria(criteria: Criteria?): MutableList<Cart> {
        val criteriaQuery = if (criteria != null) {
            queryBuilder.buildQuery(CartDbEntity::class.java, criteria)
        } else {
            val cb: CriteriaBuilder = em.criteriaBuilder
            val cq: CriteriaQuery<CartDbEntity> = cb.createQuery(CartDbEntity::class.java)
            val root: Root<CartDbEntity> = cq.from(CartDbEntity::class.java)
            cq.select(root)
        }
        val page = criteria?.page
        val limit = criteria?.limit

        val query = if (page != null && limit != null) {
            val firstResult = (page - 1) * limit
            em.createQuery(criteriaQuery).setFirstResult(firstResult).setMaxResults(limit)
        } else {
            em.createQuery(criteriaQuery)
        }

        val result = query.resultList
        val carts: MutableList<Cart> = result?.let { resultList ->
            if (resultList.isNotEmpty()) {
                resultList.map { entity ->
                    val items: MutableList<CartItemPrimitives> = entity.items.let { entityItems ->
                        if (entityItems.isNotEmpty()) {
                            entityItems.map { item -> CartItemPrimitives(
                                id = item.id,
                                quantity = item.quantity,
                                price = item.price,
                            ) }.toMutableList()
                        } else {
                            mutableListOf()
                        }
                    }
                    Cart.fromPrimitives(
                        CartPrimitives(
                            id = entity.id,
                            items = items,
                            discount = null,
                            createdAt = entity.createdAt,
                            updatedAt = entity.updatedAt,
                            customerId = entity.customerId,
                        )
                    )
                }.toMutableList()
            } else {
                mutableListOf()
            }
        } ?: mutableListOf()
        return carts
    }

    override fun updateOne(cart: Cart) {
        client.save(CartDbEntity.fromDomainEntity(cart))
    }

    override fun deleteCart(cartId: CartId) {
        client.deleteById(cartId.value.toString())
    }
}