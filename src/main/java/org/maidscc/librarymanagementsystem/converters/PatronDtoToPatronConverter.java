package org.maidscc.librarymanagementsystem.converters;

import org.maidscc.librarymanagementsystem.dtos.PatronDTO;
import org.maidscc.librarymanagementsystem.models.Patron;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PatronDtoToPatronConverter implements Converter<PatronDTO, Patron> {
    @Override
    public Patron convert(PatronDTO source) {
        Patron patron = new Patron();
        patron.setName(source.name());
        patron.setEmail(source.email());
        patron.setPhone(source.phone());
        patron.setAddress(source.address());
        return patron;
    }
}
