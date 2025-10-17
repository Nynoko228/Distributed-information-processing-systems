package com.example.demo.Lab2.entities

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * Центральная сущность видеоигры
 */
@Entity
@Table(name = "video_games")
data class VideoGame(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 255)
    val title: String,

    @Column(name = "release_year")
    val releaseYear: Int,

    @Column(nullable = false, precision = 10, scale = 2)
    val price: BigDecimal,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "developer_id", nullable = false)
    val developer: Developer,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "publisher_id", nullable = false)
    val publisher: Publisher,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genre_id", nullable = false)
    val genre: Genre,

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
        if (other !is VideoGame) return false
        return id != null && id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "VideoGame(id=$id, title='$title', releaseYear=$releaseYear, price=$price)"
    }
}
