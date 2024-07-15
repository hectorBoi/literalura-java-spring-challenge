package com.hector.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookData(
        @JsonAlias("id") Long id,
        @JsonAlias("title") String title,
        @JsonAlias("authors") Set<AuthorData> authors,
        @JsonAlias("languages") Set<String> languages,
        @JsonAlias("download_count") int downloadCount
        ) {

}
