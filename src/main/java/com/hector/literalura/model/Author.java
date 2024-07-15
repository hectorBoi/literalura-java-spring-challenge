package com.hector.literalura.model;


import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="birth_year")
    private int birthYear;
    @Column(name="death_year")
    private int deathYear;
    @Column(name="name", unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> books;

    public Author (AuthorData data) {
        this.birthYear = data.bithYear();
        this.deathYear = data.deathYear();
        this.name = data.name();
    }

    public Author() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public int getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(int deathYear) {
        this.deathYear = deathYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBooks (Set<Book> books) {
        this.books = books;
    }

    public Set<Book> getBooks () {
        return books;
    }

    @Override
    public String toString() {
        return "\n------ AUTHOR ------" +
                "\nname: " + name +
                "\nbirthYear: " + birthYear +
                "\ndeathYear: " + deathYear +
                "\n--------------------";
    }
}
