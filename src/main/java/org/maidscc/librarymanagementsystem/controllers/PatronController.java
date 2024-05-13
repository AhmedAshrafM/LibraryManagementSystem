package org.maidscc.librarymanagementsystem.controllers;

import jakarta.validation.Valid;
import org.maidscc.librarymanagementsystem.dtos.PatronDTO;
import org.maidscc.librarymanagementsystem.exceptions.DuplicateFoundException;
import org.maidscc.librarymanagementsystem.exceptions.PatronNotFoundException;
import org.maidscc.librarymanagementsystem.models.Patron;
import org.maidscc.librarymanagementsystem.services.PatronService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/patrons")
public class PatronController {
    private final PatronService patronService;

    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }
    @GetMapping
    public List<Patron> getPatrons() {
        return patronService.getAllPatrons();
    }

    @GetMapping("{patronId}")
    public Patron getPatronById(
            @PathVariable("patronId") long patronId
    ) {
        try {
            return patronService.getPatronById(patronId);
        } catch (PatronNotFoundException e) {
            throw new PatronNotFoundException(e.getMessage());
        }
    }

    @PostMapping
    public Patron addPatron(@RequestBody @Valid PatronDTO patron) {
        try {
            Patron newPatron = patronService.addPatron(patron);
            if (newPatron == null) {
                ResponseEntity.badRequest().build();
            }
            return newPatron;
        } catch (DuplicateFoundException e) {
            throw new DuplicateFoundException(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public Patron updatePatron(@PathVariable Long id, @RequestBody Patron patron) {
        try {
            Patron updatedPatron = patronService.updatePatron(id, patron);

            if (updatedPatron == null) {
                ResponseEntity.badRequest().build();
            }

            return updatedPatron;
        } catch (PatronNotFoundException e) {
            throw new PatronNotFoundException(e.getMessage());
        } catch (DuplicateFoundException e) {
            throw new DuplicateFoundException(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public void deletePatron(@PathVariable Long id) {
        try {
            patronService.deletePatron(id);

        } catch (PatronNotFoundException e) {
            throw new PatronNotFoundException(e.getMessage());
        }
    }
}
