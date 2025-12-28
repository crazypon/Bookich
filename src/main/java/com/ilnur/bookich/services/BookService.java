package com.ilnur.bookich.services;

import com.ilnur.bookich.dtos.BookRegistrationDTO;
import com.ilnur.bookich.entities.Book;
import com.ilnur.bookich.entities.User;
import com.ilnur.bookich.repositories.BookRepository;
import com.ilnur.bookich.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public void register(BookRegistrationDTO dto) {
        User owner = getCurrentUser();

        Book book = new Book();
        book.setOwner(owner);
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setGenre(dto.getGenre());
        book.setDescription(dto.getDescription());
        book.setCondition(dto.getCondition());
        book.setIs18Plus(dto.getIs18Plus());

        bookRepository.save(book);
    }

    public Page<Book> getLibrary(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Book getBook(Long id) {
        // ResponseEntityException will automatically return a response with 404 in this case
        // if it gets raised
        return bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Such book")
        );
    }

    public Page<Book> getUsersBook(Pageable pageable) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findBooksByUsername(user, pageable);
    }

    public void remove(long id) {
        // check if book exists
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Such Book")
        );
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

        // check if client has right to remove this book
        if(!book.getOwner().getUsername().equals(currentUser)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You Cannot Delete This Book");
        }

        bookRepository.delete(book);
    }

    // this method fetches the current user who sent request
    public User getCurrentUser() {
        // SecurityContextHolder, holds all necessary information about the requester
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("No user with such username")
        );
    }


}
