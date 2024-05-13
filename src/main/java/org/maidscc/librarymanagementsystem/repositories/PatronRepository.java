package org.maidscc.librarymanagementsystem.repositories;

import org.maidscc.librarymanagementsystem.models.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PatronRepository extends JpaRepository<Patron, Long> {
    Optional<Patron> findByEmail(@Param("email") String email);
}
