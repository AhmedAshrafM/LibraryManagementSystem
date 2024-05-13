package org.maidscc.librarymanagementsystem.dtos;

public record PatronDTO(
        String name,
        String email,
        String phone,
        String address
) {
}
