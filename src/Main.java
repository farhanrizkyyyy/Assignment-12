import classes.*;

import java.util.Scanner;

public class Main {
    final static BookController bookController = new BookController(); // initiate bookController object
    final static UserController userController = new UserController(); // initiate userController object
    final static Scanner scanner = new Scanner(System.in); // initiate scanner object
    final static String[] mainMenu = {"Book Management", "User Management", "Borrow & Lend Book", "Transaction History", "File Manager"}; // initiate main menu title
    final static String[] subMenu = {"Add", "Remove", "Show"}; // initiate sub menu title
    final static String[] borrowLendMenu = {"Borrow Book", "Lend Book"}; // initiate borrow menu title
    final static String[] saveLoadMenu = {"Save Books Data", "Load Books Data", "Save Users Data", "Load Users Data"};
    final static String regexNumbersOnly = "[0-9]+";

    public static void main(String[] args) {
//        bookController.addDummies();
//        userController.addDummies();
        mainMenu();
    }

    private static void mainMenu() { // function to show list of menu that available depending on mainMenu list
        int selectedMenu;

        for (int i = 0; i < mainMenu.length; i++) {
            System.out.println(i + 1 + ". " + mainMenu[i]);
        }
        System.out.println("\n0. Exit");

        while (true) {
            System.out.print("Select menu: ");
            try {
                selectedMenu = scanner.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Input must be a number, please try again.\n");
                scanner.next();
            }
        }

        System.out.println();
        mainMenuNavigation(selectedMenu);
    }

    private static void mainMenuNavigation(int selectedMenu) { // function to send user to next menu, depending to selectedMenu value
        switch (selectedMenu) {
            case 1 -> subMenu(true);
            case 2 -> subMenu(false);
            case 3 -> borrowAndLendMenu();
            case 4 -> {
                bookController.showBooksLogs();
                System.out.println();
                mainMenu();
            }
            case 5 -> saveLoadMenu();
            case 0 -> {
                scanner.close();
                System.exit(0);
            }
            default -> {
                System.out.println("Menu not available, please input with provided menu number.\n");
                mainMenu();
            }
        }
    }

    private static void subMenu(boolean isBook) { // function to show list of menu that available depends on subMenu list
        int selectedMenu;
        String type = "User";
        if (isBook) type = "Book";

        System.out.println("===== " + type.toUpperCase() + " MANAGEMENT =====");
        for (int i = 0; i < subMenu.length; i++) {
            if (i == 2) System.out.println(i + 1 + ". " + subMenu[i] + " " + type + "s");
            else System.out.println(i + 1 + ". " + subMenu[i] + " " + type);
        }
        if (isBook) System.out.println("4. Find Books");
        System.out.println("\n0. Back");

        while (true) {
            System.out.print("Select menu: ");
            try {
                selectedMenu = scanner.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Input must be a number, please try again.\n");
                scanner.next();
            }
        }

        System.out.println();
        subMenuNavigation(selectedMenu, isBook);
    }

    private static void subMenuNavigation(int selectedMenu, boolean isBook) { // function to send user to next menu, depending to selectedMenu value
        switch (selectedMenu) {
            case 1 -> {
                if (isBook) addBookMenu();
                else addUserMenu();
            }
            case 2 -> {
                if (isBook) removeBookMenu();
                else removeUserMenu();
            }
            case 3 -> {
                if (isBook) showBooksMenu();
                else showUsersMenu();

                System.out.println();
                subMenu(isBook);
            }
            case 4 -> {
                if (isBook) findBookMenu();
                else {
                    System.out.println("Menu not available, please input with provided menu number.\n");
                    subMenu(false);
                }
            }
            case 0 -> {
                System.out.println();
                mainMenu();
            }
            default -> {
                System.out.println("Menu not available, please input with provided menu number.\n");
                subMenu(isBook);
            }
        }
    }

    private static void addBookMenu() { // function to show menu to add new book
        int year;
        String isbn, title, author, formatedIsbn;

        while (true) {
            System.out.print("Insert book's ISBN (13 digits number): ");
            try {
                isbn = scanner.next();
                if (isbn.matches(regexNumbersOnly) && isbn.length() == 13) {
                    formatedIsbn = bookController.generateIsbn(isbn);
                    Book book = bookController.getBookByIsbn(formatedIsbn);

                    if (book != null) {
                        System.out.println("Book with inserted ISBN already exist, please insert another ISBN.");
                    } else break;
                } else {
                    System.out.println("Input must be 13 digits number, please try again.");
                }
            } catch (Exception e) {
                System.out.println("Input must be 13 digits number, please try again.");
                scanner.next();
            }
        }

        scanner.nextLine();

        while (true) {
            System.out.print("Insert book's title: ");
            try {
                title = scanner.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Input not valid, please try again.");
                scanner.next();
            }
        }

        while (true) {
            System.out.print("Insert book's author: ");
            try {
                author = scanner.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Input not valid, please try again.");
                scanner.next();
            }
        }

        while (true) {
            System.out.print("Insert book's release year: ");
            try {
                year = scanner.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Input must be a number, please try again.");
                scanner.next();
            }
        }

        bookController.addBook(formatedIsbn, title, author, year);
        System.out.println(title + " with " + formatedIsbn + " successfully added!\n");
        subMenu(true);
    }

    private static void removeBookMenu() { // function to show menu to remove selected book by its ISBN
        String isbn;
        String formatedIsbn;
        Book selectedBook;

        bookController.showBooks(true);
        while (true) {
            System.out.print("Insert book's ISBN to remove (13 digits number): ");
            try {
                isbn = scanner.next();

                if (isbn.matches(regexNumbersOnly) && isbn.length() == 13) {
                    formatedIsbn = bookController.generateIsbn(isbn);
                    selectedBook = bookController.getBookByIsbn(formatedIsbn);

                    if (selectedBook == null) {
                        System.out.println("No book with " + formatedIsbn + ".");
                    } else {
                        while (true) {
                            System.out.println("Are you sure want to remove this book (Y/n)? ");
                            try {
                                char confirmation = scanner.next().charAt(0);

                                if (confirmation == 'Y' || confirmation == 'y') {
                                    bookController.removeBook(selectedBook);
                                    System.out.println(selectedBook.getTitle() + " with " + selectedBook.getIsbn() + " successfully removed!\n");
                                    break;
                                } else if (confirmation == 'N' || confirmation == 'n') {
                                    break;
                                } else {
                                    System.out.println("Input not valid, please try again.");
                                }
                            } catch (Exception e) {
                                System.out.println("Input not valid, please try again.");
                                scanner.next();
                            }
                        }
                        break;
                    }
                } else {
                    System.out.println("Input must be 13 digits number, please try again.\n");
                }
            } catch (Exception e) {
                System.out.println("Input must be a number, please try again.\n");
                scanner.next();
            }
        }

        subMenu(true);
    }

    private static void findBookMenu() { // function to show menu to find book(s) by their title
        String titleKeyword;
        scanner.nextLine();

        while (true) {
            System.out.print("Insert book's title: ");
            try {
                titleKeyword = scanner.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Input not valid, please try again.");
                scanner.next();
            }
        }

        bookController.findBooksByTitle(titleKeyword);
        System.out.println();
        subMenu(true);
    }

    public static void showBooksMenu() { // function to show list of book from bookController object
        if (bookController.isBooksEmpty()) {
            System.out.println("There is no book in your library.");
        } else {
            bookController.showBooks(false);
        }
    }

    private static void addUserMenu() { // function to show menu to add new user
        String newUsername, newName;

        while (true) {
            System.out.print("Insert username: ");
            try {
                newUsername = scanner.next();
                User user = userController.getUserByUsername(newUsername);

                if (user != null) {
                    System.out.println("Username already used, please try another username.");
                } else break;
            } catch (Exception e) {
                System.out.println("Input not valid, please try again.");
                scanner.next();
            }
        }

        scanner.nextLine();

        while (true) {
            System.out.print("Insert name: ");
            try {
                newName = scanner.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Input not valid, please try again.");
                scanner.next();
            }
        }

        userController.addUser(newUsername, newName);
        System.out.println("User with username " + newUsername + " successfully added!\n");
        subMenu(false);
    }

    private static void removeUserMenu() { // function to show menu to remove user book by username
        String username;
        User selectedUser;

        userController.showUsers(true);
        while (true) {
            System.out.print("Insert username to remove: ");
            try {
                username = scanner.next();
                selectedUser = userController.getUserByUsername(username);

                if (selectedUser != null) break;
                else System.out.println("No user with username " + username + ".");
            } catch (Exception e) {
                System.out.println("Input not valid, please try again");
                scanner.next();
            }
        }

        userController.removeUser(selectedUser);
        System.out.println("User with username " + username + " successfully removed!\n");
        subMenu(false);
    }

    public static void showUsersMenu() { // function to show list of user from userController object
        if (userController.isUsersEmpty()) {
            System.out.println("No user registered.");
        } else {
            userController.showUsers(false);
        }
    }

    private static void borrowAndLendMenu() { // function to show list of menu that available depends on borrowLendMenu list
        int selectedMenu;

        for (int i = 0; i < borrowLendMenu.length; i++) {
            System.out.println(i + 1 + ". " + borrowLendMenu[i]);
        }
        System.out.println("\n0. Back");

        while (true) {
            System.out.print("Select menu: ");
            try {
                selectedMenu = scanner.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Input must be a number, please try again.");
                scanner.next();
            }
        }

        System.out.println();
        borrowAndLendMenuNavigation(selectedMenu);
    }

    private static void borrowAndLendMenuNavigation(int selectedMenu) { // function to send user to next menu, depending to selectedMenu value
        switch (selectedMenu) {
            case 1 -> borrowMenu();
            case 2 -> lendMenu();
            case 0 -> mainMenu();
            default -> {
                System.out.println("Menu not available, please input with provided menu number.\n");
                borrowAndLendMenu();
            }
        }
    }

    private static void borrowMenu() { // function to borrow a book by its ISBN and who borrow the book
        String isbn, username, formatedIsbn;
        Book selectedBook;
        User selectedUser;
        scanner.nextLine();

        System.out.println("===== BORROW MENU =====");
        bookController.showBooks(true);
        while (true) {
            System.out.print("Insert book's ISBN (only 13 digit numbers): ");
            try {
                isbn = scanner.nextLine();

                if (isbn.matches(regexNumbersOnly) && isbn.length() == 13) {
                    formatedIsbn = bookController.generateIsbn(isbn);
                    selectedBook = bookController.getBookByIsbn(formatedIsbn);

                    if (selectedBook == null) {
                        System.out.println("No book with " + formatedIsbn + ".");
                    } else break;
                } else {
                    System.out.println("Input must be 13 digits number, please try again.");
                }
            } catch (Exception e) {
                System.out.println("Input must be 13 digits number, please try again.");
                scanner.next();
            }
        }

        while (true) {
            System.out.print("Insert username: ");
            try {
                username = scanner.nextLine();
                selectedUser = userController.getUserByUsername(username);

                if (selectedUser != null) break;
                else System.out.println("No user with username " + username + ".");
            } catch (Exception e) {
                System.out.println("Input not valid, please try again.");
                scanner.next();
            }
        }

        bookController.borrow(selectedBook, selectedUser);
        System.out.println(selectedBook.getTitle() + " borrowed by " + selectedUser.getUsername() + "!\n");
        borrowAndLendMenu();
    }

    private static void lendMenu() { // function to lend a book by its ISBN
        if (bookController.isBorrowedBooksEmpty()) {
            System.out.println("There is no borrowed books.\n");
        } else {
            String isbn, formatedIsbn;
            Book selectedBook;
            scanner.nextLine();

            bookController.showBorrowedBooks();
            while (true) {
                System.out.print("Insert book's ISBN (only 13 digits number): ");
                try {
                    isbn = scanner.nextLine();
                    if (isbn.matches(regexNumbersOnly) && isbn.length() == 13) {
                        formatedIsbn = bookController.generateIsbn(isbn);
                        selectedBook = bookController.getBookByIsbn(formatedIsbn);

                        if (selectedBook == null) {
                            System.out.println("No book with " + formatedIsbn + ".");
                        } else break;
                        System.out.println("Input must be 13 digits number, please try again.");
                    }
                } catch (Exception e) {
                    System.out.println("Input must be 13 digits number, please try again.");
                    scanner.next();
                }
            }

            bookController.lend(selectedBook);
            System.out.println(selectedBook.getTitle() + " with " + selectedBook.getIsbn() + " successfully lent!\n");
        }

        borrowAndLendMenu();
    }

    private static void saveLoadMenu() {
        int selectedMenu;

        for (int i = 0; i < saveLoadMenu.length; i++) {
            System.out.println(i + 1 + ". " + saveLoadMenu[i]);
        }
        System.out.println("\n0. Back");

        while (true) {
            System.out.print("Select menu: ");
            try {
                selectedMenu = scanner.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Input must be a number, please try again.");
                scanner.next();
            }
        }

        System.out.println();
        saveLoadMenuNavigation(selectedMenu);
    }

    private static void saveLoadMenuNavigation(int selectedMenu) {
        switch (selectedMenu) {
            case 1 -> {
                bookController.exportToCsv();
                saveLoadMenu();
            }
            case 2 -> {
                bookController.importFromCsv();
                saveLoadMenu();
            }
            case 3 -> {
                userController.exportToCsv();
                saveLoadMenu();
            }
            case 4 -> {
                userController.importFromCsv();
                saveLoadMenu();
            }
            case 0 -> {
                System.out.println();
                mainMenu();
            }
            default -> {
                System.out.println("Menu not available, please input with provided menu number.\n");
                saveLoadMenu();
            }
        }
    }
}
