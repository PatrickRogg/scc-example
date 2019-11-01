package com.example.bookservice.controller;

import com.example.bookservice.BookServiceApplication;
import com.example.bookservice.entity.Book;
import com.example.bookservice.repository.BookRepository;
import com.example.bookservice.service.BookService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public abstract class BookControllerTest {

  @Mock
  private BookRepository bookRepository;

  @InjectMocks
  private BookService bookService;

  @Before
  public void setUp() {
    setMocks();
    bookService = new BookService(bookRepository);
    RestAssuredMockMvc.standaloneSetup(new BookController(bookService));
  }

  private void setMocks() {
    MockitoAnnotations.initMocks(this);
    List<Book> books = new ArrayList<>();
    Book book = new Book("9780132350884", "Robert Cecil Martin",
            "Clean Code", "Prentice Hall");
    books.add(book);

    when(bookRepository.findAll()).thenReturn(books);
    when(bookRepository.findById("9780132350884")).thenReturn(Optional.of(book));
    when(bookRepository.findById("0")).thenReturn(Optional.empty());
    when(bookRepository.findByTitle("Clean Code")).thenReturn(books);
    when(bookRepository.save(any())).then(i -> (Book) i.getArgument(0));
    when(bookRepository.findById("123456789")).thenReturn(Optional.of(book));
  }
}