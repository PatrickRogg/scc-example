package com.example.employeeservice.service;

import com.example.employeeservice.entity.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerPort;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= WebEnvironment.NONE)
@AutoConfigureStubRunner(ids = {"com.example:book-service:+:stubs:6565"},
        stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class BookClientServiceTest {
  private BookClientService bookClientService;

  @StubRunnerPort("book-service")
  private int stubPort;

  @Before
  public void setUp() {
    String targetUrl = "http://localhost:" + stubPort;
    bookClientService = new BookClientService(targetUrl);
  }

  @Test
  public void shouldReturnStatusCode200AndAllBooksWhenGetAllBooks() {
    // arrange
    Book[] expected = {
            new Book("9780132350884", "Robert Cecil Martin", "Clean Code",
                    "Prentice Hall")
    };

    // act
    ResponseEntity<Book[]> response =
            (ResponseEntity<Book[]>) bookClientService.getAllBooks();

    // assert
    assertEquals(200, response.getStatusCode().value());
    assertArrayEquals(expected, response.getBody());
  }

  @Test
  public void shouldReturnStatusCode200AndBookWithIsbnWhenGetBookBy() {
    Book expected = new Book("9780132350884", "Robert Cecil Martin",
            "Clean Code", "Prentice Hall");
    ResponseEntity<Book> response = (ResponseEntity<Book>) bookClientService.getBookBy("9780132350884");

    assertEquals(200, response.getStatusCode().value());
    assertEquals(expected, response.getBody());
  }

  @Test
  public void shouldReturnStatusCode404AndErrorMessageWhenGetBookByWithInvalidIsbn() {
    ResponseEntity<?> response =  bookClientService.getBookBy("0");

    assertEquals(404, response.getStatusCode().value());
    assertEquals("Isbn not found", response.getBody());
  }

  @Test
  public void shouldReturnStatusCode200AndAllBooksMatchingTitleWhenSearchBookBy() {
    Book[] expected = {new Book("9780132350884", "Robert Cecil Martin",
            "Clean Code", "Prentice Hall")};
    ResponseEntity<Book[]> response = (ResponseEntity<Book[]>) bookClientService.searchBooksBy("Clean Code");

    assertEquals(200, response.getStatusCode().value());
    assertArrayEquals(expected, response.getBody());
  }

  @Test
  public void shouldReturnStatusCode201AndCreateBookWhenCreateBook() {
    Book expected = new Book("9780132350884", "Robert Cecil Martin",
            "Clean Code", "Prentice Hall");
    ResponseEntity<Book> response = (ResponseEntity<Book>) bookClientService.createBook(expected);

    assertEquals(201, response.getStatusCode().value());
    assertEquals(expected, response.getBody());
  }

  @Test
  public void shouldReturnStatusCode400AndErrorMessageWhenCreateBookByWithNoIsbn() {
    Book book = new Book();
    book.setAuthor("Robert Cecil Martin");
    book.setPublisher("Prentice Hall");
    book.setTitle("Clean Code");
    ResponseEntity<Book> response = (ResponseEntity<Book>) bookClientService.createBook(book);

    assertEquals(400, response.getStatusCode().value());
    assertEquals("isbn must not be null", response.getBody());
  }

  @Test
  public void shouldReturnStatusCode200WhenUpdateBook() {
    Book book = new Book("9780132350884", "Robert Cecil Martin",
            "Clean Code", "Prentice Hall");
    ResponseEntity<?> response = bookClientService.updateBook("123456789", book);

    assertEquals(200, response.getStatusCode().value());
  }

  @Test
  public void shouldReturnStatusCode400AndErrorMessageWhenUpdateBookWithNoIsbn() {
    Book book = new Book();
    book.setAuthor("Robert Cecil Martin");
    book.setPublisher("Prentice Hall");
    book.setTitle("Clean Code");
    ResponseEntity<?> response = bookClientService.updateBook("123456789", book);

    assertEquals(400, response.getStatusCode().value());
    assertEquals("isbn must not be null", response.getBody());
  }

  @Test
  public void shouldReturnStatusCode404AndErrorMessageWhenUpdateBookWithInvalidIsbn() {
    Book book = new Book("9780132350884", "Robert Cecil Martin",
            "Clean Code", "Prentice Hall");
    ResponseEntity<?> response = bookClientService.updateBook("0", book);

    assertEquals(404, response.getStatusCode().value());
    assertEquals("Isbn not found", response.getBody());
  }

  @Test
  public void shouldReturnStatusCode200WhenDeleteBookBy() {
    ResponseEntity<?> response = bookClientService.deleteBookBy("123456789");

    assertEquals(200, response.getStatusCode().value());
  }

  @Test
  public void shouldReturnStatusCode404AndErrorMessageWhenDeleteBookByWithInvalidIsbn() {
    ResponseEntity<?> response = bookClientService.deleteBookBy("0");

    assertEquals(404, response.getStatusCode().value());
    assertEquals("Isbn not found", response.getBody());
  }
}

Expected :isbn: 9780132350884 author: Robert Cecil Martin title:Clean Codepublisher: Prentice Hall
        Actual   :isbn: 9780132350884 author: null title:Clean Codepublisher: Prentice Hall