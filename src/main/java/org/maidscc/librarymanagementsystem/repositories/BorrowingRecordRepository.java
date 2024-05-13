package org.maidscc.librarymanagementsystem.repositories;

import org.maidscc.librarymanagementsystem.models.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord,Long> {
}
