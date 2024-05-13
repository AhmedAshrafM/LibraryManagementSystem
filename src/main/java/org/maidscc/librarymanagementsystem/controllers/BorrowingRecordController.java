package org.maidscc.librarymanagementsystem.controllers;

import org.maidscc.librarymanagementsystem.services.BorrowingRecordService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BorrowingRecordController {
    private final BorrowingRecordService borrowingRecordService;

    public BorrowingRecordController(BorrowingRecordService borrowingRecordService) {
        this.borrowingRecordService = borrowingRecordService;
    }
}
