package com.example.bookservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Book {
  @Id
  @NotNull private String isbn;
  @NotNull private String author;
  @NotNull private String title;

  public Book(String isbn, String author, String title) {
    this.isbn = isbn;
    this.author = author;
    this.title = title;
  }

  public Book() {
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Book book = (Book) o;
    return isbn.equals(book.isbn) &&
            author.equals(book.author) &&
            title.equals(book.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(isbn, author, title);
  }
}