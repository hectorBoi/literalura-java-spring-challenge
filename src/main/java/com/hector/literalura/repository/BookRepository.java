package com.hector.literalura.repository;

import com.hector.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b JOIN b.languages bl WHERE bl = :language")
    List<Book> findBooksByLanguage(@Param("language") String language);
}
