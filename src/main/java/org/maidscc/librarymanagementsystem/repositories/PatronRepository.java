package org.maidscc.librarymanagementsystem.repositories;

import org.maidscc.librarymanagementsystem.models.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PatronRepository extends JpaRepository<Patron, Long> {
    @Query
    boolean existsPatronByEmail(String email);
}
