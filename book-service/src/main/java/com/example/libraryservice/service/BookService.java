package com.example.libraryservice.service;

import com.example.libraryservice.entity.Book;
import com.example.libraryservice.exceptions.IsbnNotFoundException;
import com.example.libraryservice.repository.BookRepository;
import com.example.libraryservice.repository.specification.BookSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
@Transactional
public class BookService {

  private final BookRepository bookRepository;

  @Autowired
  public BookService(final BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  public Book getBookByIsbn(String isbn) throws IsbnNotFoundException {
    return bookRepository.findById(isbn).orElseThrow(() -> new IsbnNotFoundException());
  }

  public Book create(Book book) {
    return bookRepository.save(book);
  }

  public void update(String isbn, Book book) throws IsbnNotFoundException {
      Book toUpdateBook = getBookByIsbn(isbn);
      toUpdateBook.setIsbn(book.getIsbn());
      toUpdateBook.setAuthor(book.getAuthor());
      toUpdateBook.setTitle(book.getTitle());
      toUpdateBook.setPublisher(book.getPublisher());
      bookRepository.save(toUpdateBook);
  }

  public void deleteBookBy(String isbn) throws IsbnNotFoundException {
    if(bookRepository.findById(isbn).isPresent()) {
      bookRepository.deleteById(isbn);
    } else {
      throw new IsbnNotFoundException();
    }
  }

  public List<Book> filterBooksBy(String bookTitle) {
    return bookRepository.findAll(where(BookSpecification.hasTitle(bookTitle)));
  }
}
