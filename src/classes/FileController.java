package classes;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileController {
    public void saveBooks(List<Book> books) {
        try {
            File file = new File("books.csv"); // initiate file object that refers to books.csv
            FileWriter fw = new FileWriter(file); // initiate fileWriter object
            BufferedWriter bw = new BufferedWriter(fw); // initiate bufferedWriter object

            for (int i = 0; i < books.size(); i++) { // looping through books list
                Book book = books.get(i); // initiate book object for every index
                // get book's properties
                String isbn = book.getIsbn();
                String title = book.getTitle();
                String author = book.getAuthor();
                int year = book.getYear();
                List<String> logs = book.getLogs();
                boolean isBorrowed = book.getBorrowStatus();
                boolean isActive = book.getActiveStatus();
                // create entry on books.csv file
                bw.write(i + "," + isbn + "," + title + "," + author + "," + year + "," + logs + "," + isBorrowed + "," + isActive);
                bw.newLine(); // create new line after entry successfully created
            }

            // close bufferedWriter & fileWriter object if looping is done
            bw.close();
            fw.close();

            System.out.println("File saved!"); // give message to user when books.csv successfully generated
        } catch (FileNotFoundException e) { // throw this exception if books.csv not found in project directory
            System.out.println("File not found.");
            e.printStackTrace();
        } catch (Exception e) { // throw this exception if something wrong happen when writing books.csv
            System.out.println("Something happen when reaching the file, please try again.");
            e.printStackTrace();
        }
    }

    public void saveUsers(List<User> users) {
        try {
            File file = new File("users.csv"); // initiate file object that refers to users.csv
            FileWriter fw = new FileWriter(file); // initiate fileWriter object
            BufferedWriter bw = new BufferedWriter(fw); // initiate bufferedWriter object

            for (int i = 0; i < users.size(); i++) { // looping through users list
                User user = users.get(i); // initiate book object for every index
                // get user's properties
                String username = user.getUsername();
                String name = user.getName();
                boolean isActive = user.getStatus();
                // create entry on books.csv file
                bw.write(i + "," + username + "," + name + "," + isActive);
                bw.newLine(); // create new line after entry successfully created
            }

            // close bufferedWriter & fileWriter object if looping is done
            bw.close();
            fw.close();

            System.out.println("File saved!"); // give message to user when books.csv successfully generated
        } catch (FileNotFoundException e) { // throw this exception if books.csv not found in project directory
            System.out.println("File not found.");
            e.printStackTrace();
        } catch (Exception e) { // throw this exception if something wrong happen when writing books.csv
            System.out.println("Something happen when reaching the file, please try again.");
            e.printStackTrace();
        }
    }

    public void loadBooks(List<Book> books) {
        try {
            File file = new File("books.csv"); // initiate file object that refers to books.csv
            FileReader fr = new FileReader(file); // initiate fileReader object
            BufferedReader br = new BufferedReader(fr); // initiate bufferedReader object

            String record; // initiate record variable to store every entry from books.csv
            while ((record = br.readLine()) != null) { // looping for every entry from books.csv
                String[] data = record.split(","); // convert entry into an array, value separated by comma
                String isbn = data[1];
                String title = data[2];
                String author = data[3];
                int year = Integer.parseInt(data[4]);
                List<String> logs = new ArrayList<>(Arrays.asList(data[5].split(",")));
                boolean isBorrowed = Boolean.parseBoolean(data[6]);
                boolean isActive = Boolean.parseBoolean(data[7]);
                Book book = new Book(isbn, title, author, year, logs, isBorrowed, isActive); // create book object for every entry

                books.add(book); // add book object to books list
            }

            System.out.println("Books successfully loaded!\n"); // give message to user when all entry successfully loaded
        } catch (FileNotFoundException e) { // throw this exception if books.csv not found in project directory
            System.out.println("File not found.");
            e.printStackTrace();
        } catch (Exception e) { // throw this exception if something wrong happen when reading books.csv
            System.out.println("Something happen when reaching the file, please try again.");
            e.printStackTrace();
        }
    }

    public void loadUsers(List<User> users) {
        try {
            File file = new File("users.csv"); // initiate file object that refers to users.csv
            FileReader fr = new FileReader(file); // initiate fileReader object
            BufferedReader br = new BufferedReader(fr); // initiate bufferedReader object

            String record; // initiate record variable to store every entry from books.csv
            while ((record = br.readLine()) != null) { // looping for every entry from books.csv
                String[] data = record.split(","); // convert entry into an array, value separated by comma
                String username = data[1];
                String name = data[2];
                boolean isActive = Boolean.parseBoolean(data[3]);
                User user = new User(username, name, isActive); // create user object for every entry

                users.add(user); // add user object to books list
            }

            System.out.println("Users successfully loaded!\n"); // give message to user when all entry successfully loaded
        } catch (FileNotFoundException e) { // throw this exception if users.csv not found in project directory
            System.out.println("File not found.");
            e.printStackTrace();
        } catch (Exception e) { // throw this exception if something wrong happen when reading books.csv
            System.out.println("Something happen when reaching the file, please try again.");
            e.printStackTrace();
        }
    }
}
