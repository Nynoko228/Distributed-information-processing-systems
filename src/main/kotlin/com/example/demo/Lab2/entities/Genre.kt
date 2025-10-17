package com.example.demo.Lab2.entities

import jakarta.persistence.*
import java.time.LocalDateTime

/**
 * Сущность жанра видеоигр
 */
@Entity
@Table(name = "genres")
data class Genre(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true, length = 100)
    val name: String,

    @Column(columnDefinition = "TEXT")
    val description: String? = null,

    @OneToMany(mappedBy = "genre", cascade = [CascadeType.PERSIST], fetch = FetchType.LAZY)
    val games: MutableList<VideoGame> = mutableListOf(),

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    @PreUpdate
    fun onUpdate() {
        updatedAt = LocalDateTime.now()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Genre) return false
        return id != null && id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Genre(id=$id, name='$name', description=$description)"
    }
}
