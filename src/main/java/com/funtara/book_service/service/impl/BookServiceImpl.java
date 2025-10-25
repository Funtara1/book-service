package com.funtara.book_service.service.impl;

import com.funtara.book_service.exception.BookNotFoundException;
import com.funtara.book_service.model.Book;
import com.funtara.book_service.repository.BookRepository;
import com.funtara.book_service.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Book createBook(String bookName, String authorName) {
        Book book = Book.builder()
                .bookName(bookName)
                .authorName(authorName)
                .build();
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @Override
    public Book updateBook(Long id, String bookName, String authorName) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        book.setBookName(bookName);
        book.setAuthorName(authorName);
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
    }
}
