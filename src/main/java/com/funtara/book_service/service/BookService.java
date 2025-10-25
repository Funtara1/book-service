package com.funtara.book_service.service;

import com.funtara.book_service.api.dto.CreateBookRequest;
import com.funtara.book_service.api.dto.UpdateBookRequest;
import com.funtara.book_service.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    Book createBook(CreateBookRequest request);
    List<Book> createBooks(List<CreateBookRequest> requests);
    Page<Book> getAllBooks(Pageable pageable);
    Book getBookById(Long id);
    Book updateBook(Long id, UpdateBookRequest request);
    void deleteBook(Long id);
}
