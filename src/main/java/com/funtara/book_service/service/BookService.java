package com.funtara.book_service.service;

import com.funtara.book_service.model.Book;

import java.util.List;

public interface BookService {

    Book createBook(String bookName, String authorName);

    List<Book> getAllBooks();

    Book getBookById(Long id);

    Book updateBook(Long id, String bookName, String authorName);

    void deleteBook(Long id);
}
