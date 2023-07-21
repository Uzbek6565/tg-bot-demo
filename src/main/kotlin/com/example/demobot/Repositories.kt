package com.example.demobot

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface BaseRepository<T : BaseEntity> : JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
    fun findByIdAndDeletedFalse(id: Long): T?

    fun trash(id: Long): T?

    fun trashList(ids: List<Long>): List<T?>
    fun findAllNotDeleted(): List<T>
    fun findAllNotDeleted(pageable: Pageable): Page<T>
}

class BaseRepositoryImpl<T : BaseEntity>(
        entityInformation: JpaEntityInformation<T, Long>, entityManager: EntityManager,
) : SimpleJpaRepository<T, Long>(entityInformation, entityManager), BaseRepository<T> {

    val isNotDeletedSpecification = Specification<T> { root, _, cb -> cb.equal(root.get<Boolean>("deleted"), false) }

    override fun findByIdAndDeletedFalse(id: Long) = findByIdOrNull(id)?.run { if (deleted) null else this }

    @Transactional
    override fun trash(id: Long): T? = findByIdOrNull(id)?.run {
        deleted = true
        save(this)
    }

    override fun findAllNotDeleted(): List<T> = findAll(isNotDeletedSpecification)
    override fun findAllNotDeleted(pageable: Pageable): Page<T> = findAll(isNotDeletedSpecification, pageable)

    override fun trashList(ids: List<Long>): List<T?> = ids.map { trash(it) }
}

interface UserRepository : BaseRepository<User> {
    fun findByChatId(chatId: Long): User?
    fun findByChatIdAndDeletedFalse(chatId: Long): User
    fun existsByPhoneNumber(phoneNumber: String):Boolean
    fun findByPhoneNumber(phoneNumber: String):User
    fun findAlByRoleAndDeletedFalse(role: Role) :MutableList<User>
}

interface SessionRepository : BaseRepository<Session> {
    fun findByClientIdAndIsActiveTrue(clientId: Long): Session?
    fun findByOperatorIdAndIsActiveTrue(operatorId: Long):  Session?
    @Query("select s from sessions s where s.operator_id = ?1",  nativeQuery = true)
    fun averageRatingByOperatorId(operatorId: Long): Double

    @Query("SELECT s FROM sessions s WHERE s.language IN :language AND s.isActive = true ORDER BY s.createdDate ASC LIMIT 1")
    fun findSuitableSessions( @Param("language") language: List<Language>): Session?
//    fun findFirstByLanguageInAndActiveOrderByCreatedDate(language: MutableCollection<Language>, active: Boolean):Session
}

interface UserMessageRepository: BaseRepository<UserMessage>{

    @Query("select * from user_messages um where um.session_id = ?1 and um.type = 'REQUEST' ORDER BY created_date DESC",
            nativeQuery = true)
    fun findAllBySessionIdAndTypeRequestOderOrderByCreatedDate(sessionId: Long): List<UserMessage>
}