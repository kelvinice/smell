package uts.after.services;

import java.util.ArrayList;
import java.util.List;
import uts.after.models.Book;
import uts.after.models.User;

public class LibraryService {
    private List<User> users = new ArrayList<>();
    private List<Book> books = new ArrayList<>();

    public void registerUser(User user) {
        users.add(user);
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Book> getBooks() {
        return books;
    }
}


