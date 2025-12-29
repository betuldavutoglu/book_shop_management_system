package service;

import dao.BookDAO;
import model.Book;
import java.util.List;

public class BookService {
    private BookDAO bookDAO;
    
    public BookService() {
        this.bookDAO = new BookDAO();
    }
    
    public boolean addBook(Book book) {
        return bookDAO.create(book);
    }
    
    public Book getBook(int id) {
        return bookDAO.read(id);
    }
    
    public List<Book> getAllBooks() {
        return bookDAO.readAll();
    }
    
    public boolean updateBook(Book book) {
        return bookDAO.update(book);
    }
    
    public boolean deleteBook(int id) {
        return bookDAO.delete(id);
    }
    
    public List<Book> searchBooks(String keyword) {
        return bookDAO.searchByTitle(keyword);
    }
    
    public boolean updateStock(int bookId, int quantityChange) {
        return bookDAO.updateStock(bookId, quantityChange);
    }
    
    public boolean sellBook(int bookId, int quantity) {
        return updateStock(bookId, -quantity);
    }
    
    public boolean restockBook(int bookId, int quantity) {
        return updateStock(bookId, quantity);
    }
}
