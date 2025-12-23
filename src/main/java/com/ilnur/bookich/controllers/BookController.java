package com.ilnur.bookich.controllers;

import com.ilnur.bookich.dtos.BookRegistrationDTO;
import com.ilnur.bookich.entities.Book;
import com.ilnur.bookich.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/register")
    public ResponseEntity<String> registerBook(@Valid @RequestBody BookRegistrationDTO book) {
        bookService.register(book);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Book registered successfully");
    }

    @GetMapping
    public ResponseEntity<Set<Book>> getAllBooks() {
        Set<Book> books = bookService.getLibrary();
        return ResponseEntity
                .ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable long id) {
        Book book = bookService.getBook(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/my-books")
    public ResponseEntity<List<Book>> getCurrentUsersBook() {
        List<Book> books = bookService.getUsersBook();
        return ResponseEntity.ok(books);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable long id) {
        bookService.remove(id);
        return ResponseEntity.ok("Book Removed Successfully");
    }

}
