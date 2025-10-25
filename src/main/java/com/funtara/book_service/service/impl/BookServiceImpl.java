package com.funtara.book_service.service.impl;

import com.funtara.book_service.api.dto.CreateBookRequest;
import com.funtara.book_service.api.dto.UpdateBookRequest;
import com.funtara.book_service.api.mapper.BookMapper;
import com.funtara.book_service.exception.BookNotFoundException;
import com.funtara.book_service.model.Book;
import com.funtara.book_service.repository.BookRepository;
import com.funtara.book_service.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper mapper;

    @Override
    @Transactional
    public Book createBook(CreateBookRequest request) {
        Book book = mapper.toEntity(request);
        return bookRepository.save(book);
    }

    @Override
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @Override
    @Transactional
    public Book updateBook(Long id, UpdateBookRequest request) {
        Book existing = getBookById(id);
        mapper.updateEntity(existing, request);
        return bookRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
    }
}
