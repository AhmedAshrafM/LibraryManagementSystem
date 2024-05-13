package org.maidscc.librarymanagementsystem.controllers;

import jakarta.validation.Valid;
import org.maidscc.librarymanagementsystem.dtos.PatronDTO;
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
    public ResponseEntity<Patron> getPatronById(
            @PathVariable("patronId") long patronId
    ) {
        Patron patron = patronService.getPatronById(patronId);
        return ResponseEntity.ok(patron);
    }

    @PostMapping
    public ResponseEntity<Patron> addPatron(@RequestBody @Valid PatronDTO patron) {
        Patron newPatron = patronService.addPatron(patron);
        if (newPatron == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(newPatron);
    }

    @PutMapping("{id}")
    public ResponseEntity<Patron> updatePatron(@PathVariable Long id, @RequestBody Patron patron) {
        Patron updatedPatron = patronService.updatePatron(id, patron);

        if (updatedPatron == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(updatedPatron);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable Long id) {

            patronService.deletePatron(id);
            return ResponseEntity.noContent().build();
    }
}
