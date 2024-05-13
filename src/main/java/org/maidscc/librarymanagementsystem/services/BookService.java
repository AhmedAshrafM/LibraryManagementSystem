package org.maidscc.librarymanagementsystem.services;

import org.maidscc.librarymanagementsystem.daos.BookDao;
import org.maidscc.librarymanagementsystem.dtos.BookDTO;
import org.maidscc.librarymanagementsystem.converters.BookDtoToBookConverter;
import org.maidscc.librarymanagementsystem.exceptions.BookNotFoundException;
import org.maidscc.librarymanagementsystem.exceptions.DuplicateFoundException;
import org.maidscc.librarymanagementsystem.models.Book;
import org.maidscc.librarymanagementsystem.repositories.BookRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService implements BookDao {

    private final BookRepository bookRepository;
    private final BookDtoToBookConverter bookDtoToBookConverter;

    public BookService(BookRepository bookRepository, BookDtoToBookConverter bookDtoToBookConverter) {
        this.bookRepository = bookRepository;
        this.bookDtoToBookConverter = bookDtoToBookConverter;
    }

    @Cacheable(value = "books")
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Cacheable(value = "books", key = "#id")
    @Override
    public Book getBookById(long id) {
        return bookRepository.findById(id).orElseThrow(() ->
                new BookNotFoundException("book with id [%s] not found".formatted(id)));
    }

    @Override
    public Book getByTitle(String title){
        return this.bookRepository.findByTitle(title)
                .orElseThrow(() -> new BookNotFoundException("book with title [%s] not found".formatted(title)));
    }

    @Transactional
    @Override
    public Book addBook(BookDTO book) {
        if(this.bookRepository.findByTitle(book.title()).isEmpty()){
            Book newBook = this.bookDtoToBookConverter.convert(book);
            return bookRepository.save(newBook);
        }
        throw new DuplicateFoundException("book with same title already exists");
    }

    @Transactional
    @Override
    public Book updateBook(long id, Book bookData){
        Book book = this.bookRepository.findById(id).
                orElseThrow(() -> new BookNotFoundException("book with id [%s] not found".formatted(id)));

        if(bookData.getTitle() != null){
            if(this.bookRepository.findByTitle(bookData.getTitle()).isEmpty()){
                book.setTitle(bookData.getTitle());
            }else{
                throw new DuplicateFoundException("book with same title already exists");
            }
        }
        if(bookData.getAuthor() != null){
            book.setAuthor(bookData.getAuthor());
        }
        if(bookData.getPublicationYear() != null){
            book.setPublicationYear(bookData.getPublicationYear());
        }
        if(bookData.getIsbn() != null){
            book.setIsbn(bookData.getIsbn());
        }
        if(bookData.getPublisher() != null){
            book.setPublisher(bookData.getPublisher());
        }
        if(bookData.getGenre() != null){
            book.setGenre(bookData.getGenre());
        }
        return this.bookRepository.save(book);
    }

    @Transactional
    @Override
    public void deleteBook(long id){
        this.bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("book with id [%s] not found".formatted(id)));
        this.bookRepository.deleteById(id);
    }
}
