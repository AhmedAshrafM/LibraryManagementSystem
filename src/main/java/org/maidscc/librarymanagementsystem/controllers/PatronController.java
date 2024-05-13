package org.maidscc.librarymanagementsystem.controllers;

import org.maidscc.librarymanagementsystem.services.PatronService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatronController {
    private final PatronService patronService;

    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }
}
