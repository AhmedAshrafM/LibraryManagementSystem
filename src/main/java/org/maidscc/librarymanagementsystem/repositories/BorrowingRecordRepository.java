package org.maidscc.librarymanagementsystem.repositories;

import org.maidscc.librarymanagementsystem.models.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    @Query("SELECT b FROM BorrowingRecord b WHERE b.book.id = ?1 AND b.patron.id = ?2")
    List<BorrowingRecord> findByBookIdAndPatronId(Long bookId, Long patronId);
}
