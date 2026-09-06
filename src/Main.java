import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Book> bookList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n==========================================");
            System.out.println("        LIBRARY MANAGEMENT SYSTEM         ");
            System.out.println("==========================================");
            System.out.println("1. Add New Book");
            System.out.println("2. View All Books");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Books (Title / Author)");
            System.out.println("6. Generate Summary Report");
            System.out.println("7. Delete Book Record");
            System.out.println("8. Exit");
            System.out.println("==========================================");
            System.out.print("Enter your choice (1-8): ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(">> Error: Please enter a valid number (1-8).");
                continue;
            }

            switch (choice) {
                case 1 -> addBook();
                case 2 -> viewBooks();
                case 3 -> issueBook();
                case 4 -> returnBook();
                case 5 -> searchBooks();
                case 6 -> generateReport();
                case 7 -> deleteBook();
                case 8 -> {
                    System.out.println("Exiting Library Management System. Goodbye!");
                    return;
                }
                default -> System.out.println(">> Invalid choice! Please select 1 to 8.");
            }
        }
    }

    private static void addBook() {
        System.out.println("\n--- Add New Book ---");
        System.out.print("Enter Book ID: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(">> Invalid ID format.");
            return;
        }

        if (findBookById(id) != null) {
            System.out.println(">> Error: Book with ID " + id + " already exists!");
            return;
        }

        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine().trim();

        System.out.print("Enter Author Name: ");
        String author = scanner.nextLine().trim();

        bookList.add(new Book(id, title, author));
        System.out.println(">> Success: Book added successfully!");
    }

    private static void viewBooks() {
        System.out.println("\n--- Library Catalog ---");
        if (bookList.isEmpty()) {
            System.out.println(">> No books currently in catalog.");
            return;
        }

        System.out.println("---------------------------------------------------------------------------------");
        for (Book b : bookList) {
            System.out.println(b);
        }
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("Total Books: " + bookList.size());
    }

    private static void issueBook() {
        System.out.println("\n--- Issue Book ---");
        System.out.print("Enter Book ID to issue: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(">> Invalid ID.");
            return;
        }

        Book book = findBookById(id);
        if (book == null) {
            System.out.println(">> Error: Book ID not found.");
            return;
        }

        if (book.isIssued()) {
            System.out.println(">> Error: This book is already issued!");
        } else {
            book.setIssued(true);
            System.out.println(">> Success: Book '" + book.getTitle() + "' has been issued.");
        }
    }

    private static void returnBook() {
        System.out.println("\n--- Return Book ---");
        System.out.print("Enter Book ID to return: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(">> Invalid ID.");
            return;
        }

        Book book = findBookById(id);
        if (book == null) {
            System.out.println(">> Error: Book ID not found.");
            return;
        }

        if (!book.isIssued()) {
            System.out.println(">> Error: This book was not issued. It is already available.");
        } else {
            book.setIssued(false);
            System.out.println(">> Success: Book '" + book.getTitle() + "' returned successfully.");
        }
    }

    private static void searchBooks() {
        System.out.println("\n--- Search Books ---");
        System.out.print("Enter search keyword (Title or Author): ");
        String keyword = scanner.nextLine().trim().toLowerCase();

        boolean found = false;
        System.out.println("---------------------------------------------------------------------------------");
        for (Book b : bookList) {
            if (b.getTitle().toLowerCase().contains(keyword) || b.getAuthor().toLowerCase().contains(keyword)) {
                System.out.println(b);
                found = true;
            }
        }
        System.out.println("---------------------------------------------------------------------------------");

        if (!found) {
            System.out.println(">> No matching books found.");
        }
    }

    private static void generateReport() {
        System.out.println("\n==========================================");
        System.out.println("       LIBRARY STATUS SUMMARY REPORT      ");
        System.out.println("==========================================");
        int total = bookList.size();
        long issued = bookList.stream().filter(Book::isIssued).count();
        long available = total - issued;

        System.out.printf("Total Books in Inventory : %d%n", total);
        System.out.printf("Books Currently Issued   : %d%n", issued);
        System.out.printf("Books Available to Issue : %d%n", available);
        System.out.println("==========================================");
    }

    private static void deleteBook() {
        System.out.println("\n--- Delete Book Record ---");
        System.out.print("Enter Book ID to delete: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(">> Invalid ID.");
            return;
        }

        Book book = findBookById(id);
        if (book != null) {
            bookList.remove(book);
            System.out.println(">> Success: Book ID " + id + " removed from catalog.");
        } else {
            System.out.println(">> Error: Book ID not found.");
        }
    }

    private static Book findBookById(int id) {
        for (Book b : bookList) {
            if (b.getId() == id) return b;
        }
        return null;
    }
}