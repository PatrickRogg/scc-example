package com.example.employeeservicepact.service;


import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import com.example.employeeservicepact.entity.Book;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class BookClientCreateBookWithNoIsbnPactTest {
  private BookClientService bookClientService;

  @Rule
  public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("book_service", this);

  @Pact(consumer="employee_service")
  public RequestResponsePact createCreateBookPact(PactDslWithProvider builder) {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json;charset=UTF-8");

    DslPart request = new PactDslJsonBody()
                .nullValue("isbn")
                .stringValue("author", "Robert Cecil Martin")
                .stringValue("title", "Clean Code")
                .stringValue("publisher", "Prentice Hall");

    return builder
            .given("createBookWithNoIsbn")
            .uponReceiving("request to create a new book with no isbn value set")
            .path("/books")
            .method("POST")
            .body(request)
            .headers(headers)
            .willRespondWith()
            .status(400)
            .body("Isbn can not be null")
            .toPact();
  }

  @Test
  @PactVerification
  public void should_return_http_status_404_and_error_message_when_create_book_without_isbn() {
    bookClientService = new BookClientService(mockProvider.getUrl());
    Book book = new Book();
    book.setAuthor("Robert Cecil Martin");
    book.setPublisher("Prentice Hall");
    book.setTitle("Clean Code");
    ResponseEntity<Book> response = (ResponseEntity<Book>) bookClientService.createBook(book);

    assertEquals(400, response.getStatusCode().value());
    assertEquals("Isbn can not be null", response.getBody());
  }
}