package com.hector.literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name="book")
public class Book {
    @Id
    @Column(name="id")
    private Long id;
    @Column(name="title")
    private String title;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "book_language", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "language")
    private Set<String> languages;
    @Column(name="download_count")
    private int downloadCount;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;

    public Book(BookData data) {
        this.id = data.id();
        this.title = data.title();
        this.languages = data.languages();
        this.downloadCount = data.downloadCount();
    }

    public Book() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<String> getLanguages() {
        return languages;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        StringBuilder authorsStr = new StringBuilder();
        if (authors != null) {
            for (Author author : authors) {
                authorsStr.append(author.getName()).append(", ");
            }
            if (authorsStr.length() > 0) {
                authorsStr.setLength(authorsStr.length() - 2); // Remove trailing comma and space
            }
        }
        return "----- BOOK ------" +
                "\ntitle: '" + title +
                "\nlanguages: " + (languages != null ? languages.toString() : "[]") +
                "\ndownloadCount: " + downloadCount +
                "\nauthors: " + authorsStr +
                "\n-------------";
    }
}
