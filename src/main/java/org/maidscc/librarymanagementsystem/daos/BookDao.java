package org.maidscc.librarymanagementsystem.daos;

import org.maidscc.librarymanagementsystem.dtos.BookDTO;
import org.maidscc.librarymanagementsystem.models.Book;

import java.util.List;

public interface BookDao {
    List<Book> getAllBooks();
    Book getBookById(long id);
    Book getByTitle(String title);
    Book addBook(BookDTO book);
    Book updateBook(long id, Book bookData);
    void deleteBook(long id);
}
