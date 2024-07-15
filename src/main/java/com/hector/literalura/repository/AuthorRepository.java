package com.hector.literalura.repository;

import com.hector.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    boolean existsByName(String name);
    Optional<Author> findByName(String name);
    @Query("SELECT a FROM Author a WHERE :year BETWEEN a.birthYear AND a.deathYear")
    List<Author> findAuthorsByYear(@Param("year") int year);
}
