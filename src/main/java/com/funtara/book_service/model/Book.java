package com.funtara.book_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "books",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_books_name_author",
                columnNames = {"book_name", "author_name"}))
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_name", nullable = false, length = 200)
    private String bookName;

    @Column(name = "author_name", nullable = false, length = 120)
    private String authorName;

    @Version
    private Long version;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    void prePersist() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    void preUpdate() {
        this.updatedAt = Instant.now();
    }

    @Override
    public String toString() {
        return "Book{id=" + id +
                ", bookName='" + bookName + '\'' +
                ", authorName='" + authorName + '\'' + '}';
    }
}
