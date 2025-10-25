package com.funtara.book_service.repository;

import com.funtara.book_service.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Поиск по части названия (без учёта регистра)
    List<Book> findByBookNameContainingIgnoreCase(String bookName);

    // Поиск по части имени автора (без учёта регистра)
    List<Book> findByAuthorNameContainingIgnoreCase(String authorName);

    // Поиск по названию И автору
    List<Book> findByBookNameContainingIgnoreCaseAndAuthorNameContainingIgnoreCase(String bookName, String authorName);
}
