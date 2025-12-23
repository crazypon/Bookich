package com.ilnur.bookich.repositories;

import com.ilnur.bookich.entities.Book;
import com.ilnur.bookich.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBooksByOwner(User owner);
}
