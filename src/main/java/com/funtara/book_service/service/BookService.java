package com.funtara.book_service.service;

import com.funtara.book_service.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Book createBook(String bookName, String authorName);
    Page<Book> getAllBooks(Pageable pageable);
    Book getBookById(Long id);
    Book updateBook(Long id, String bookName, String authorName);
    void deleteBook(Long id);
}
