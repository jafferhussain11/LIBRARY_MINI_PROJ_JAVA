package com.example.demo.dto;

import com.example.demo.models.Author;
import com.example.demo.models.Book;
import com.example.demo.models.Genre;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String authorName;

    @NotBlank
    private String authorEmail;
    @NotNull
    private Genre genre;

    public Book toBook(){

        Author author = Author.builder().email(this.authorEmail).name(this.authorName).build();

        return Book.builder().
                name(name).
                genre(this.genre)
                .author(author)
                .build();

    }
}
