package org.maidscc.librarymanagementsystem.repositories;

import org.maidscc.librarymanagementsystem.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(@Param("title") String title);
}