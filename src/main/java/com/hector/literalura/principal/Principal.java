package com.hector.literalura.principal;

import com.hector.literalura.model.Author;
import com.hector.literalura.model.Book;
import com.hector.literalura.model.BookData;
import com.hector.literalura.model.GutendexResponse;
import com.hector.literalura.repository.AuthorRepository;
import com.hector.literalura.repository.BookRepository;
import com.hector.literalura.service.ReceiveData;
import com.hector.literalura.service.RequestAPI;


import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private final Scanner keyboard = new Scanner(System.in);
    private final RequestAPI requestAPI = new RequestAPI();
    private final ReceiveData receiveDataObject = new ReceiveData();

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public Principal(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void showMenu() {
        var selection = -1;
        while(selection != 0) {
            var menu = """
                    1 - Search book by title
                    2 - Show searched books
                    3 - Show books by language
                    4 - Show Authors Living in certain year
                    
                    0 - Salir
                    """;

            System.out.println(menu);

            try{
                selection = keyboard.nextInt();
                keyboard.nextLine();

                switch (selection) {
                    case 1 -> searchBookByName();
                    case 2 -> showSearchedBooks();
                    case 3 -> searchBookByLanguage();
                    case 4 -> searchAuthorsLivingByYear();
                    default -> System.out.println("Invalid Option");
                }
            }catch(InputMismatchException e){
                System.out.println("Please enter a valid number.");
                keyboard.nextLine(); // Consume the invalid input
            }

        }
    }

    private void searchAuthorsLivingByYear() {
        System.out.println("Write the year you want to find which authors lived");
        try{
            var year = keyboard.nextInt();
            List<Author> authors = authorRepository.findAuthorsByYear(year);
            if(authors.isEmpty()){
                System.out.println("No authors found living in year " + year);
            }else{
                authors.forEach(author -> System.out.println(author.toString()));
            }
        }catch (InputMismatchException e){
            System.out.println("Please enter a year.");
            keyboard.nextLine(); // Consume the invalid input
        }

    }

    private BookData getBookData () {
        System.out.println("Type the name of the book you look for:");
        var searchedBook = keyboard.nextLine();
        String BASE_URL = "https://gutendex.com/books/";
        var json = requestAPI.getData(BASE_URL + "?search=" + searchedBook.replace(" ", "%20"));
        GutendexResponse gutendexResponse = receiveDataObject.obtenerDatos(json, GutendexResponse.class);
        if(gutendexResponse.count() == 0){
            return null;
        }else{
            return gutendexResponse.books().get(0);
        }

    }

    private void searchBookByName() {
        try{
            BookData data = getBookData();
            Book book = new Book(data);
            Set<Author> authors = data.authors().stream()
                    .map(Author::new)
                    .collect(Collectors.toSet());
            Set<Book> books = new HashSet<>();
            books.add(book);

            //Save entities before modification
            bookRepository.save(book);
            Set<Author> savedAuthors = new HashSet<>();
            authors.forEach(a -> {
                Optional<Author> existingAuthor = authorRepository.findByName(a.getName());
                if (existingAuthor.isPresent()) {
                    // Author with the same name already exists
                    Author foundAuthor = existingAuthor.get();
                    // Now you can work with `foundAuthor` as needed
                    foundAuthor.setBooks(books);
                    savedAuthors.add(foundAuthor);
                } else {
                    // Author does not exist, so save it
                    authorRepository.save(a);
                    a.setBooks(books);
                    savedAuthors.add(a);
                }
            });

            //Add book to authors and authors to book
            book.setAuthors(savedAuthors);
            bookRepository.save(book);
            authorRepository.saveAll(savedAuthors);
        }catch (IndexOutOfBoundsException e){
            System.out.println("No books found");
        }

    }

    private void showSearchedBooks() {
        List<Book> books = bookRepository.findAll();
        books.forEach(System.out::println);
    }

    private void searchBookByLanguage() {
        System.out.println("Write the 2 character language code that you want to look for");
        var language = keyboard.nextLine().toLowerCase().strip();
        if(language.length() != 2){
            System.out.println("Not a Valid Code");
        }else{
            List<Book> books = bookRepository.findBooksByLanguage(language);
            if(books.isEmpty()){
                System.out.println("No books found for " + language);
            }else{
                books.forEach(System.out::println);
            }
        }
    }

}
