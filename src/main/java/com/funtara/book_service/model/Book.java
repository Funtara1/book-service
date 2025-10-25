package com.funtara.book_service.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_name", nullable = false)
    private String bookName;

    @Column(name = "author_name", nullable = false)
    private String authorName;

    @Override
    public String toString() {
        return "Book{id=" + id + ", bookName='" + bookName + '\'' +
                ", authorName='" + authorName + '\'' + '}';
    }
}
