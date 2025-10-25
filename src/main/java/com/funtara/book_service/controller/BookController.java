package com.funtara.book_service.controller;

import com.funtara.book_service.api.dto.*;
import com.funtara.book_service.api.mapper.BookMapper;
import com.funtara.book_service.model.Book;
import com.funtara.book_service.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;
    private final BookMapper mapper;

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody CreateBookRequest req) {
        Book savedBook = bookService.createBook(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(savedBook));
    }

    @GetMapping
    public ResponseEntity<Page<BookResponse>> getAllBooks(Pageable pageable) {
        Page<Book> page = bookService.getAllBooks(pageable);
        return ResponseEntity.ok(page.map(mapper::toResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(bookService.getBookById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id,
                                                   @Valid @RequestBody UpdateBookRequest req) {
        Book updated = bookService.updateBook(id, req);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
