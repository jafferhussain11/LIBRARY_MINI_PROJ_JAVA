package com.example.demo.service;

import com.example.demo.models.Author;
import com.example.demo.models.Book;
import com.example.demo.models.Genre;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorService authorService;

    public void createOrUpdateBook(Book book){

        Author bookAuthor = book.getAuthor();

        Author existingAuthor = authorService.getOrCreate(bookAuthor);

        book.setAuthor(existingAuthor);

        bookRepository.save(book);
    }

    public List<Book> findBook(String key, String value) throws Exception {

        if(key.equals("name")){
            return bookRepository.findByName(value);
        }
        else if (key.equals("author")){
            return bookRepository.findByAuthor(value);
        }else if(key.equals("genre")){
            return bookRepository.findByGenre(Genre.valueOf(value.toUpperCase()));
        }else if(key.equals("id")){

            Optional<Book> book = bookRepository.findById(Integer.parseInt(value));
            if(book.isPresent()){
                return Arrays.asList(book.get());
            }else {
                return new ArrayList<>();
            }
        }
        else{
            throw new Exception("Invalid search key");
        }
    }
}
