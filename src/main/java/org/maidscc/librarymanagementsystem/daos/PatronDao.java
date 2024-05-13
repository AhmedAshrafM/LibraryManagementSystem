package org.maidscc.librarymanagementsystem.daos;

import org.maidscc.librarymanagementsystem.dtos.PatronDTO;
import org.maidscc.librarymanagementsystem.models.Patron;

import java.util.List;

public interface PatronDao {
    List<Patron> getAllPatrons();
    Patron getPatronById(long id);
    Patron getByEmail(String email);
    Patron addPatron(PatronDTO patron);
    Patron updatePatron(long id, Patron patronData);
    void deletePatron(long id);
}
