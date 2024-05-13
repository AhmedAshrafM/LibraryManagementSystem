package org.maidscc.librarymanagementsystem.services;

import org.maidscc.librarymanagementsystem.converters.PatronDtoToPatronConverter;
import org.maidscc.librarymanagementsystem.daos.PatronDao;
import org.maidscc.librarymanagementsystem.dtos.PatronDTO;
import org.maidscc.librarymanagementsystem.exceptions.DuplicateFoundException;
import org.maidscc.librarymanagementsystem.exceptions.PatronNotFoundException;
import org.maidscc.librarymanagementsystem.models.Patron;
import org.maidscc.librarymanagementsystem.repositories.PatronRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatronService implements PatronDao {

    private final PatronRepository patronRepository;
    private final PatronDtoToPatronConverter patronDtoToPatronConverter;

    public PatronService(PatronRepository patronRepository, PatronDtoToPatronConverter patronDtoToPatronConverter) {
        this.patronRepository = patronRepository;
        this.patronDtoToPatronConverter = patronDtoToPatronConverter;
    }

    @Cacheable(value = "patrons")
    @Override
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    @Cacheable(value = "patrons", key= "#id")
    @Override
    public Patron getPatronById(long id) {
        return patronRepository.findById(id)
                .orElseThrow(() -> new PatronNotFoundException("patron with id [%s} not found".formatted(id)));
    }

    @Override
    public Patron getByEmail(String email) {
        return this.patronRepository.findByEmail(email)
                .orElseThrow(() -> new PatronNotFoundException("patron with email [%s} not found".formatted(email)));
    }

    @Transactional
    @Override
    public Patron addPatron(PatronDTO patron) {
        if(this.patronRepository.findByEmail(patron.email()).isEmpty()){
            Patron newPatron = this.patronDtoToPatronConverter.convert(patron);
            return patronRepository.save(newPatron);
        }
        throw new DuplicateFoundException("patron with same email already exists");
    }

    @Transactional
    @Override
    public Patron updatePatron(long id, Patron patronData) {
        Patron patron = this.patronRepository.findById(id).
                orElseThrow(() -> new PatronNotFoundException("patron with id [%s} not found".formatted(id)));
        if(patronData.getName() != null){
            patron.setName(patronData.getName());
        }
        if(patronData.getAddress() != null){
            patron.setAddress(patronData.getAddress());
        }
        if(patronData.getPhone() != null){
            patron.setPhone(patronData.getPhone());
        }
        if(patronData.getEmail() != null){
            if(this.patronRepository.findByEmail(patronData.getEmail()).isEmpty()) {
                patron.setEmail(patronData.getEmail());
            }else{
                throw new DuplicateFoundException("patron with same name already exists");
            }
        }

        return this.patronRepository.save(patron);
    }

    @Transactional
    @Override
    public void deletePatron(long id) {
        this.patronRepository.findById(id)
                .orElseThrow(() -> new PatronNotFoundException("patron with id [%s} not found".formatted(id)));
        this.patronRepository.deleteById(id);
    }
}
