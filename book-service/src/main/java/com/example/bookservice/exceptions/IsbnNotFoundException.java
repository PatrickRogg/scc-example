package com.example.bookservice.exceptions;

public class IsbnNotFoundException extends Exception {
  public IsbnNotFoundException() {
    super("Isbn not found");
  }
}
