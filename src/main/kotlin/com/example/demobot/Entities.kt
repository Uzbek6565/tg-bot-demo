package com.example.demobot

import org.hibernate.annotations.ColumnDefault
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseEntity(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
        @CreatedDate @Temporal(TemporalType.TIMESTAMP) var createdDate: Date? = null,
        @LastModifiedDate @Temporal(TemporalType.TIMESTAMP) var modifiedDate: Date? = null,
        @Column(nullable = false) @ColumnDefault(value = "false") var deleted: Boolean = false,
)

@Entity(name = "users")
class User(
        var chatId: Long? = null,
        var fullName: String? = null,
        var phoneNumber: String? = null,
        var isOnline: Boolean = false,
        @Enumerated(EnumType.STRING) var role: Role = Role.CLIENT,
        @Enumerated(EnumType.STRING) var language: MutableList<Language> = mutableListOf(),
        @Enumerated(EnumType.STRING) var state: UserState = UserState.START,
) : BaseEntity()


@Entity(name = "sessions")
class Session(
        @ManyToOne var client: User,
        var isActive: Boolean = true,
        @Enumerated(EnumType.STRING) var language: Language,
        @ManyToOne var operator: User? = null,
        var rate: Short? = null
) : BaseEntity()

@Entity(name = "user_messages")
class UserMessage(
        @Enumerated(EnumType.STRING) var contentType: ContentType? = null,
        @Enumerated(EnumType.STRING) var type: MessageType,
        var text: String? = null,
        var storedId: String? = null,
        @ManyToOne var session: Session,
) : BaseEntity()