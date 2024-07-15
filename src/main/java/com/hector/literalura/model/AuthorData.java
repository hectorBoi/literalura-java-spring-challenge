package com.hector.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record AuthorData(
        @JsonAlias("birth_year") int bithYear,
        @JsonAlias("death_year") int deathYear,
        @JsonAlias("name") String name
) {
}
