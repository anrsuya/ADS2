import java.util.*;

class BankAccount {
    private String accountNumber;
    private String username;
    private double balance;

    public BankAccount(String accountNumber, String username, double balance) {
        this.accountNumber = accountNumber;
        this.username      = username;
        this.balance       = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getUsername()      { return username; }
    public double getBalance()       { return balance; }

    public void deposit(double amount) {
        this.balance += amount;
    }
    public boolean withdraw(double amount) {
        if (amount > balance) return false;
        this.balance -= amount;
        return true;
    }

    @Override
    public String toString() {
        return accountNumber + " | " + username +
                " -- Balance: " + String.format("%.2f", balance);
    }
}

class ArrayStack {
    private String[] data;
    private int top;
    private static final int DEFAULT_CAPACITY = 64;

    public ArrayStack() {
        data = new String[DEFAULT_CAPACITY];
        top  = -1;
    }

    public void push(String item) {
        if (top == data.length - 1) {
            data = Arrays.copyOf(data, data.length * 2);
        }
        data[++top] = item;
    }

    public String pop() {
        if (isEmpty()) throw new EmptyStackException();
        String item = data[top];
        data[top--] = null;
        return item;
    }

    public String peek() {
        if (isEmpty()) throw new EmptyStackException();
        return data[top];
    }

    public boolean isEmpty() { return top == -1; }

    public int size() { return top + 1; }

    public String get(int i) {
        if (i < 0 || i > top) throw new IndexOutOfBoundsException();
        return data[i];
    }
}
public class Main {

    // PART 1 – Task 1
    static LinkedList<BankAccount> accounts = new LinkedList<>();

    // PART 1 – Task 3
    static ArrayStack transactionHistory = new ArrayStack();

    // PART 1 – Task 4
    static Queue<String> billQueue = new LinkedList<>();

    // PART 1 – Task 5
    static Queue<BankAccount> accountRequests = new LinkedList<>();

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initPhysicalArray();   // PART 2 – Task 6
        mainMenu();            // PART 3
        scanner.close();
    }

    static void initPhysicalArray() {
        BankAccount[] physicalAccounts = new BankAccount[3];
        physicalAccounts[0] = new BankAccount("ACC001", "Ali",   150000);
        physicalAccounts[1] = new BankAccount("ACC002", "Sara",  220000);
        physicalAccounts[2] = new BankAccount("ACC003", "Arman", 310000);

        System.out.println("=== PART 2: Physical Data Structures (Array) ===");
        for (int i = 0; i < physicalAccounts.length; i++) {
            System.out.println((i + 1) + ". " + physicalAccounts[i]);
        }
        System.out.println();

        Collections.addAll(accounts, physicalAccounts);
    }
    static void mainMenu() {
        while (true) {
            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1 -- Enter Bank");
            System.out.println("2 -- Enter ATM");
            System.out.println("3 -- Admin Area");
            System.out.println("4 -- Exit");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> bankMenu();
                case "2" -> atmMenu();
                case "3" -> adminMenu();
                case "4" -> { System.out.println("Goodbye!"); return; }
                default  -> System.out.println("Invalid option. Try again.");
            }
        }
    }
    static void bankMenu() {
        while (true) {
            System.out.println("\n--- BANK MENU ---");
            System.out.println("1 -- Submit account opening request");
            System.out.println("2 -- Deposit money");
            System.out.println("3 -- Withdraw money");
            System.out.println("4 -- Display all accounts");
            System.out.println("5 -- Search account by username");
            System.out.println("6 -- Show transaction history");
            System.out.println("7 -- Undo last transaction");
            System.out.println("0 -- Back");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> submitAccountRequest();   // Task 5
                case "2" -> deposit();                // Task 2
                case "3" -> withdraw();               // Task 2
                case "4" -> displayAllAccounts();     // Task 1
                case "5" -> searchByUsername();       // Task 1
                case "6" -> showTransactionHistory(); // Task 3
                case "7" -> undoLastTransaction();    // Task 3
                case "0" -> { return; }
                default  -> System.out.println("Invalid option.");
            }
        }
    }
    static void atmMenu() {
        while (true) {
            System.out.println("\n--- ATM MENU ---");
            System.out.println("1 -- Balance enquiry");
            System.out.println("2 -- Withdraw");
            System.out.println("0 -- Back");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> balanceEnquiry();
                case "2" -> withdraw();
                case "0" -> { return; }
                default  -> System.out.println("Invalid option.");
            }
        }
    }
    static void adminMenu() {
        while (true) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1 -- View pending account requests");
            System.out.println("2 -- Process next account request");
            System.out.println("3 -- Add bill payment");
            System.out.println("4 -- Process next bill payment");
            System.out.println("5 -- Display bill queue");
            System.out.println("0 -- Back");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> viewAccountQueue();      // Task 5
                case "2" -> processAccountRequest(); // Task 5
                case "3" -> addBill();               // Task 4
                case "4" -> processNextBill();       // Task 4
                case "5" -> displayBillQueue();      // Task 4
                case "0" -> { return; }
                default  -> System.out.println("Invalid option.");
            }
        }
    }
    //  PART 1 – Task 1
    static void displayAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
        System.out.println("Accounts List:");
        int i = 1;
        for (BankAccount acc : accounts) {
            System.out.println(i++ + ". " + acc.getUsername() +
                    " -- Balance: " + String.format("%.2f", acc.getBalance()));
        }
    }

    static void searchByUsername() {
        System.out.print("Enter username to search: ");
        String name = scanner.nextLine().trim();
        for (BankAccount acc : accounts) {
            if (acc.getUsername().equalsIgnoreCase(name)) {
                System.out.println("Found: " + acc);
                return;
            }
        }
        System.out.println("Account not found.");
    }

    static BankAccount findAccount(String username) {
        for (BankAccount acc : accounts) {
            if (acc.getUsername().equalsIgnoreCase(username)) return acc;
        }
        return null;
    }

    //  PART 1 – Task 2
    static void deposit() {
        System.out.print("Enter username: ");
        String name = scanner.nextLine().trim();
        BankAccount acc = findAccount(name);
        if (acc == null) { System.out.println("Account not found."); return; }

        System.out.print("Deposit amount: ");
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            if (amount <= 0) { System.out.println("Amount must be positive."); return; }

            acc.deposit(amount);
            System.out.println("New balance: " + String.format("%.2f", acc.getBalance()));

            // Task 3 – push onto ArrayStack
            String record = "Deposit " + amount + " to " + name;
            transactionHistory.push(record);
            System.out.println("Transaction recorded: " + record);

        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
        }
    }

    static void withdraw() {
        System.out.print("Enter username: ");
        String name = scanner.nextLine().trim();
        BankAccount acc = findAccount(name);
        if (acc == null) { System.out.println("Account not found."); return; }

        System.out.print("Withdraw amount: ");
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            if (amount <= 0) { System.out.println("Amount must be positive."); return; }

            if (!acc.withdraw(amount)) {
                System.out.println("Insufficient funds.");
                return;
            }
            System.out.println("New balance: " + String.format("%.2f", acc.getBalance()));

            // Task 3 – push onto ArrayStack
            String record = "Withdraw " + amount + " from " + name;
            transactionHistory.push(record);
            System.out.println("Transaction recorded: " + record);

        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
        }
    }

    // ── ATM helper
    static void balanceEnquiry() {
        System.out.print("Enter username: ");
        String name = scanner.nextLine().trim();
        BankAccount acc = findAccount(name);
        if (acc == null) { System.out.println("Account not found."); return; }
        System.out.println("Balance for " + acc.getUsername() + ": " +
                String.format("%.2f", acc.getBalance()));
    }
    //  PART 1 – Task 3
    static void showTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }
        System.out.println("Transaction History (newest first):");
        for (int i = transactionHistory.size() - 1; i >= 0; i--) {
            System.out.println("  - " + transactionHistory.get(i));
        }
        System.out.println("Last transaction (peek): " + transactionHistory.peek());
    }
    static void undoLastTransaction() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions to undo.");
            return;
        }

        String last = transactionHistory.peek();
        String[] parts = last.split(" ");
        String type = parts[0];

        if (type.equals("Bill")) {
            transactionHistory.pop();
            System.out.println("Undo → bill payment log removed " +
                    "(money cannot be returned automatically).");
            return;
        }
        try {
            double amount = Double.parseDouble(parts[1]);
            String username = parts[parts.length - 1];
            BankAccount acc = findAccount(username);

            if (acc == null) {
                System.out.println("Cannot undo: account '" + username + "' not found.");
                return;
            }

            if (type.equals("Deposit")) {
                if (!acc.withdraw(amount)) {
                    System.out.println("Cannot undo deposit: insufficient balance.");
                    return;
                }
            } else if (type.equals("Withdraw")) {
                acc.deposit(amount);
            }

            transactionHistory.pop();
            System.out.println("Undo → \"" + last + "\" reversed.");
            System.out.println("New balance for " + username + ": " +
                    String.format("%.2f", acc.getBalance()));

        } catch (NumberFormatException e) {
            System.out.println("Cannot parse transaction record.");
        }
    }
    //  PART 1 – Task 4  (Queue – FIFO)
    static void addBill() {
        System.out.print("Enter bill description (e.g. Electricity Bill): ");
        String bill = scanner.nextLine().trim();
        if (bill.isEmpty()) { System.out.println("Description cannot be empty."); return; }
        billQueue.add(bill);
        System.out.println("Added: " + bill);
    }

    static void processNextBill() {
        if (billQueue.isEmpty()) { System.out.println("Bill queue is empty."); return; }
        String processed = billQueue.poll();
        System.out.println("Processing: " + processed);

        // Task 3
        String record = "Bill payment: " + processed;
        transactionHistory.push(record);
        System.out.println("Transaction recorded: " + record);

        if (!billQueue.isEmpty()) {
            System.out.println("Next in queue: " + billQueue.peek());
        } else {
            System.out.println("No remaining bills.");
        }
    }

    static void displayBillQueue() {
        if (billQueue.isEmpty()) { System.out.println("Bill queue is empty."); return; }
        System.out.println("Bill Queue:");
        int i = 1;
        for (String bill : billQueue) {
            System.out.println(i++ + ". " + bill);
        }
    }
    //  PART 1 – Task 5
    static void submitAccountRequest() {
        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine().trim();

        System.out.print("Enter username: ");
        String uname = scanner.nextLine().trim();

        if (findAccount(uname) != null) {
            System.out.println("Username '" + uname + "' already exists.");
            return;
        }
        for (BankAccount a : accounts) {
            if (a.getAccountNumber().equalsIgnoreCase(accNum)) {
                System.out.println("Account number '" + accNum + "' already exists.");
                return;
            }
        }

        System.out.print("Enter initial balance: ");
        try {
            double bal = Double.parseDouble(scanner.nextLine().trim());
            if (bal < 0) { System.out.println("Balance cannot be negative."); return; }
            BankAccount newAcc = new BankAccount(accNum, uname, bal);
            accountRequests.add(newAcc);
            System.out.println("Request submitted for: " + uname + ". Pending admin approval.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid balance.");
        }
    }

    static void viewAccountQueue() {
        if (accountRequests.isEmpty()) { System.out.println("No pending requests."); return; }
        System.out.println("Pending Account Requests:");
        int i = 1;
        for (BankAccount acc : accountRequests) {
            System.out.println(i++ + ". " + acc);
        }
    }

    static void processAccountRequest() {
        if (accountRequests.isEmpty()) { System.out.println("No pending requests."); return; }
        BankAccount approved = accountRequests.poll();
        accounts.add(approved);
        System.out.println("Account approved and added: " + approved);
    }
}