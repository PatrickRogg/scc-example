package com.example.bookservice.controller;

import com.example.bookservice.entity.Book;
import com.example.bookservice.exceptions.IsbnNotFoundException;
import com.example.bookservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

  private BookService bookService;

  @Autowired
  public BookController(final BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping
  public ResponseEntity<?> getAllBooks() {
    List<Book> books = bookService.getAllBooks();
    return ResponseEntity.status(200).body(books);
  }

  @GetMapping("{isbn}")
  public ResponseEntity<?> getBookByIsbn(@PathVariable String isbn) {
    try {
      Book book = bookService.getBookByIsbn(isbn);
      return ResponseEntity.status(200).body(book);
    } catch (IsbnNotFoundException e) {
      return ResponseEntity.status(404).body(e.getMessage());
    }
  }

  @GetMapping("search")
  public ResponseEntity<?> searchBooksBy(@RequestParam String bookTitle) {
    List<Book> filteredBooks = bookService.filterBooksBy(bookTitle);
    return ResponseEntity.status(200).body(filteredBooks);
  }

  @PostMapping
  public ResponseEntity<?> createBook(@RequestBody @Valid Book book) {
    Book createdBook = bookService.create(book);
    return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON_UTF8).body(createdBook);
  }

  @PutMapping("{isbn}")
  public ResponseEntity<?> updateBook(@PathVariable String isbn, @RequestBody @Valid Book book) {
    try {
      bookService.update(isbn, book);
      return ResponseEntity.ok().build();
    } catch (IsbnNotFoundException e) {
      return ResponseEntity.status(404).body(e.getMessage());
    }
  }

  @DeleteMapping("{isbn}")
  public ResponseEntity<?> deleteBook(@PathVariable String isbn) {
    try {
      bookService.deleteBookBy(isbn);
      return ResponseEntity.ok().build();
    } catch (IsbnNotFoundException e) {
      return ResponseEntity.status(404).body(e.getMessage());
    }
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleIOException(MethodArgumentNotValidException ex, HttpServletRequest request) {
    StringBuilder sb = new StringBuilder();

    for(FieldError error : ex.getBindingResult().getFieldErrors()) {
      sb.append(error.getField() + ", ");
    }

    sb.setLength(sb.length() - 2);
    String errorMessage = sb.toString() + " must not be null";
    return ResponseEntity.status(400).body(errorMessage);
  }
}
