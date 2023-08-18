package classes;

import java.sql.Timestamp;
import java.util.*;

public class BookController {
    private final List<Book> books = new ArrayList<>(); // List to store all books from library
    private final Map<Book, User> borrowedBooks = new HashMap<>(); // Map to store borrow data with format Book:User

    public void addBook(String isbn, String title, String author, int year) { // function to add new book to books list
        Book book = new Book(isbn, title, author, year, new ArrayList<>(), false, true);
        this.books.add(book);
    }

    public boolean isBooksEmpty() { // function to check is books list empty or not
        return this.books.size() == 0;
    }

    public boolean isBorrowedBooksEmpty() { // function to check is borrowedBooks map empty or not
        return this.borrowedBooks.size() == 0;
    }

    public void borrow(Book book, User user) { // function to borrow books, add book to borrowedBooks map & hide it from books list
        book.setBorrowStatus(true);
        this.borrowedBooks.put(book, user);
        String log = "Borrowed by " + user.getUsername() + ".";
        book.addLog(generateLog(log));
    }

    public void lend(Book book) { // function to lend book, remove book from borrowedBooks map & show it on books list
        User user = this.borrowedBooks.get(book);
        book.setBorrowStatus(false);
        this.borrowedBooks.remove(book);
        String log = "Lent by " + user.getUsername() + ".";
        book.addLog(generateLog(log));
    }

    public String generateIsbn(String isbn) { // function to generate ISBN with existing format -> ISBN 123-123-123-123-4
        isbn = new StringBuilder(isbn).insert(3, '-').toString();
        isbn = new StringBuilder(isbn).insert(7, '-').toString();
        isbn = new StringBuilder(isbn).insert(11, '-').toString();
        isbn = new StringBuilder(isbn).insert(15, '-').toString();
        isbn = "ISBN " + isbn;

        return isbn;
    }

    public void showBooks(boolean activeOnly) { // function to show books list to CLI with filter option, activeOnly
        if (activeOnly) {
            List<Book> activeBooks = new ArrayList<>();

            for (Book book : this.books) {
                if (book.getActiveStatus() && !book.getBorrowStatus()) activeBooks.add(book);
            }

            System.out.println("List of available books:");
            for (int i = 0; i < activeBooks.size(); i++) {
                Book book = this.books.get(i);
                String isbn = book.getIsbn();
                String title = book.getTitle();
                int year = book.getYear();
                System.out.println(i + 1 + ". " + isbn + " - " + title + " (" + year + ") by " + book.getAuthor());
            }
        } else {
            System.out.println("List of books:");
            for (int i = 0; i < this.books.size(); i++) {
                Book book = this.books.get(i);
                String isbn = book.getIsbn();
                String title = book.getTitle();
                int year = book.getYear();
                System.out.print(i + 1 + ". " + isbn + " - " + title + " (" + year + ") by " + book.getAuthor());
                if (book.getBorrowStatus()) System.out.println(" - Borrowed.");
                else if (!book.getActiveStatus()) System.out.println(" - Inactive.");
                else System.out.println();
            }
        }
    }

    public void showBorrowedBooks() { // function to show list of borrowed books from borrowedBooks map
        int i = 1;

        for (Map.Entry<Book, User> entry : this.borrowedBooks.entrySet()) {
            Book book = entry.getKey();
            User user = entry.getValue();

            System.out.println(i + ". " + book.getIsbn() + " - " + book.getTitle() + " by " + user.getUsername());

            i++;
        }
    }

    public void removeBook(Book book) { // function to change book active status to false
        book.setActiveStatus(false);
        book.addLog(generateLog("Book was removed."));
    }

    public Book getBookByIsbn(String isbn) { // function to get 1 book from given ISBN
        Book resultBook = null;

        for (Book book : this.books) {
            if (book.getActiveStatus() && book.getIsbn().equalsIgnoreCase(isbn)) {
                resultBook = book;
                break;
            }
        }

        return resultBook;
    }

    public void findBooksByTitle(String titleKeyword) { // function to show list of book by given title keyword
        List<Book> filteredBooks = new ArrayList<>();

        for (Book book : this.books) {
            if (book.getActiveStatus() && book.getTitle().toLowerCase().contains(titleKeyword.toLowerCase())) {
                filteredBooks.add(book);
            }
        }

        if (filteredBooks.size() == 0) {
            System.out.println("No books that contains " + titleKeyword + ".");
        } else {
            System.out.println("Search result:");
            for (int i = 0; i < filteredBooks.size(); i++) {
                Book book = filteredBooks.get(i);
                String isbn = book.getIsbn();
                String title = book.getTitle();
                int year = book.getYear();
                String author = book.getAuthor();
                System.out.println(i + 1 + ". " + isbn + " - " + title + " (" + year + ") by " + author);
            }
        }
    }

    public void showBooksLogs() { // function to show book transaction history
        for (int i = 0; i < this.books.size(); i++) {
            Book book = this.books.get(i);
            System.out.println(i + 1 + ". " + book.getTitle() + " (" + book.getYear() + ") - " + book.getIsbn());
            if (book.getLogs().size() == 0) {
                System.out.println("   > No transaction history for this book.");
            } else {
                for (int j = 0; j < book.getLogs().size(); j++) {
                    System.out.println(book.getLogs().get(j));
                }
            }
        }
    }

    public void exportToCsv() {
        FileController controller = new FileController(); // initiate fileController object
        controller.saveBooks(this.books); // export books from list to .csv format
    }

    public void importFromCsv() {
        FileController controller = new FileController(); // initiate fileController object
        this.books.clear(); // remove all book entry from books list
        controller.loadBooks(this.books); // import all data entry from books.csv to books list
    }

    public void addDummies() { // function to add dummy data when program started
        this.books.add(new Book("ISBN 123-123-123-123-1", "Book 1", "Farhan", 2020, new ArrayList<>(), false, true));
        this.books.add(new Book("ISBN 123-123-123-123-2", "Book 2", "Farhan", 2020, new ArrayList<>(), false, true));
        this.books.add(new Book("ISBN 123-123-123-123-3", "Book 3", "Farhan", 2020, new ArrayList<>(), false, true));
        this.books.add(new Book("ISBN 123-123-123-123-4", "Book 4", "Farhan", 2020, new ArrayList<>(), false, true));
        this.books.add(new Book("ISBN 123-123-123-123-5", "Book 5", "Farhan", 2020, new ArrayList<>(), false, true));
        this.books.add(new Book("ISBN 123-123-123-123-6", "Book 6", "Farhan", 2020, new ArrayList<>(), false, true));
    }

    private String generateLog(String message) {
        return "   > " + new Timestamp(System.currentTimeMillis()) + " - " + message;
    }
}
