package org.maidscc.librarymanagementsystem.converters;

import org.maidscc.librarymanagementsystem.dtos.PatronDTO;
import org.maidscc.librarymanagementsystem.models.Patron;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PatronToPatronDtoConverter implements Converter<Patron, PatronDTO> {
    @Override
    public PatronDTO convert(Patron source) {
        PatronDTO patronDTO = new PatronDTO(source.getName(), source.getEmail(),
                source.getPhone(), source.getAddress());
        return patronDTO;
    }
}
