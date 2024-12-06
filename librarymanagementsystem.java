import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

// Book class to represent each book
class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean isAvailable;

    public Book(String title, String author, String isbn, boolean isAvailable) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = isAvailable;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", ISBN: " + isbn + ", Available: " + (isAvailable ? "Yes" : "No");
    }
}

// Library class to manage books
class Library {
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public void updateBook(String isbn, boolean isAvailable) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                book.setAvailable(isAvailable);
                return;
            }
        }
    }

    public void deleteBook(String isbn) {
        books.removeIf(book -> book.getIsbn().equals(isbn));
    }

    public List<Book> searchBooks(String query) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                book.getAuthor().toLowerCase().contains(query.toLowerCase())) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    public List<Book> getAllBooks() {
        return books;
    }
}

// GUI Class
public class LibraryApp extends Frame {
    private Library library = new Library();
    private TextArea outputArea;
    private TextField titleField, authorField, isbnField, searchField;
    private Checkbox availableCheckbox;

    public LibraryApp() {
        setTitle("Library Management System");
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Header
        Label header = new Label("Library Management System", Label.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        add(header, BorderLayout.NORTH);

        // Center Panel for output
        outputArea = new TextArea();
        outputArea.setEditable(false);
        add(outputArea, BorderLayout.CENTER);

        // Footer with controls
        Panel controls = new Panel();
        controls.setLayout(new GridLayout(6, 2, 5, 5));

        controls.add(new Label("Title:"));
        titleField = new TextField();
        controls.add(titleField);

        controls.add(new Label("Author:"));
        authorField = new TextField();
        controls.add(authorField);

        controls.add(new Label("ISBN:"));
        isbnField = new TextField();
        controls.add(isbnField);

        controls.add(new Label("Available:"));
        availableCheckbox = new Checkbox();
        controls.add(availableCheckbox);

        controls.add(new Label("Search:"));
        searchField = new TextField();
        controls.add(searchField);

        Button addButton = new Button("Add Book");
        addButton.addActionListener(e -> addBook());
        controls.add(addButton);

        Button updateButton = new Button("Update Book");
        updateButton.addActionListener(e -> updateBook());
        controls.add(updateButton);

        Button deleteButton = new Button("Delete Book");
        deleteButton.addActionListener(e -> deleteBook());
        controls.add(deleteButton);

        Button searchButton = new Button("Search Books");
        searchButton.addActionListener(e -> searchBooks());
        controls.add(searchButton);

        Button displayButton = new Button("Display All Books");
        displayButton.addActionListener(e -> displayAllBooks());
        controls.add(displayButton);

        Button exitButton = new Button("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        controls.add(exitButton);

        add(controls, BorderLayout.SOUTH);

        setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String isbn = isbnField.getText();
        boolean isAvailable = availableCheckbox.getState();

        if (title.isEmpty() || author.isEmpty() || isbn.isEmpty()) {
            outputArea.setText("All fields (Title, Author, ISBN) must be filled!");
            return;
        }

        Book book = new Book(title, author, isbn, isAvailable);
        library.addBook(book);
        outputArea.setText("Book added successfully.");
        clearFields();
    }

    private void updateBook() {
        String isbn = isbnField.getText();
        boolean isAvailable = availableCheckbox.getState();

        if (isbn.isEmpty()) {
            outputArea.setText("Please enter ISBN to update a book.");
            return;
        }

        library.updateBook(isbn, isAvailable);
        outputArea.setText("Book updated successfully (if found).");
        clearFields();
    }

    private void deleteBook() {
        String isbn = isbnField.getText();

        if (isbn.isEmpty()) {
            outputArea.setText("Please enter ISBN to delete a book.");
            return;
        }

        library.deleteBook(isbn);
        outputArea.setText("Book deleted successfully (if found).");
        clearFields();
    }

    private void searchBooks() {
        String query = searchField.getText();

        if (query.isEmpty()) {
            outputArea.setText("Please enter a title or author to search.");
            return;
        }

        List<Book> results = library.searchBooks(query);
        if (results.isEmpty()) {
            outputArea.setText("No books found matching the query.");
        } else {
            StringBuilder resultText = new StringBuilder("Search results:\n");
            for (Book book : results) {
                resultText.append(book).append("\n");
            }
            outputArea.setText(resultText.toString());
        }
        clearFields();
    }

    private void displayAllBooks() {
        List<Book> books = library.getAllBooks();
        if (books.isEmpty()) {
            outputArea.setText("No books in the library.");
        } else {
            StringBuilder resultText = new StringBuilder("All books in the library:\n");
            for (Book book : books) {
                resultText.append(book).append("\n");
            }
            outputArea.setText(resultText.toString());
        }
    }

    private void clearFields() {
        titleField.setText("");
        authorField.setText("");
        isbnField.setText("");
        searchField.setText("");
        availableCheckbox.setState(false);
    }

    public static void main(String[] args) {
        new LibraryApp();
    }
}
