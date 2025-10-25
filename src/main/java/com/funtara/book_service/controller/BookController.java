package com.funtara.book_service.controller;

import com.funtara.book_service.model.Book;
import com.funtara.book_service.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    // Создать книгу
    @PostMapping
    public ResponseEntity<Map<String, Object>> createBook(@RequestBody Book book) {
        Book savedBook = bookService.createBook(book.getBookName(), book.getAuthorName());
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Book successfully created");
        response.put("book", savedBook);
        return new ResponseEntity<>(response, HttpStatus.CREATED); // 201 Created
    }

    // Получить все книги
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK); // 200 OK
    }

    // Получить книгу по ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id); // выбросит BookNotFoundException, если нет
        return new ResponseEntity<>(book, HttpStatus.OK); // 200 OK
    }

    // Обновить книгу
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateBook(@PathVariable Long id,
                                                          @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(id, book.getBookName(), book.getAuthorName());
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Book successfully updated");
        response.put("book", updatedBook);
        return new ResponseEntity<>(response, HttpStatus.OK); // 200 OK
    }

    // Удалить книгу
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id); // выбросит BookNotFoundException, если нет
        Map<String, String> response = new HashMap<>();
        response.put("message", "Book successfully deleted");
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT); // 204 No Content
    }
}
