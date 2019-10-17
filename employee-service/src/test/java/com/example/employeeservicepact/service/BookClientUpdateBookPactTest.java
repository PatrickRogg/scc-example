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

public class BookClientUpdateBookPactTest {
  private BookClientService bookClientService;

  @Rule
  public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("book_service", this);

  @Pact(consumer="employee_service")
  public RequestResponsePact createUpdateBookPact(PactDslWithProvider builder) {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json;charset=UTF-8");

    DslPart request = new PactDslJsonBody()
            .stringValue("isbn", "9780132350883")
            .stringValue("author", "Robert Cecil Martin")
            .stringValue("title", "Clean Code")
            .stringValue("publisher", "Prentice Hall");

    return builder
            .given("updateBook")
            .uponReceiving("request to update a book")
            .path("/books/123456789")
            .method("PUT")
            .body(request)
            .headers(headers)
            .willRespondWith()
            .status(200)
            .toPact();
  }

  @Test
  @PactVerification
  public void should_return_http_status_200_and_updated_book_when_update_book() {
    bookClientService = new BookClientService(mockProvider.getUrl());
    Book book = new Book("9780132350883", "Robert Cecil Martin",
            "Clean Code", "Prentice Hall");
    ResponseEntity<?> response = bookClientService.updateBook("123456789", book);

    assertEquals(200, response.getStatusCode().value());
  }
}