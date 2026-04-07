import java.util.*;

class BankAccount {
    String accountNumber;
    String username;
    double balance;

    public BankAccount(String accountNumber, String username, double balance) {
        this.accountNumber = accountNumber;
        this.username = username;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return accountNumber + " | " + username + " -- Balance: " + balance;
    }
}
public class Main {
    //  PART 1 —Task 1
    static LinkedList<BankAccount> accounts = new LinkedList<>();
    //  PART 1 — Task 3
    static Stack<String> transactionHistory = new Stack<>();
    //  PART 1 — Task 4
    static Queue<String> billQueue = new LinkedList<>();
    //  PART 1 — Task 5
    static Queue<BankAccount> accountRequests = new LinkedList<>();

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initPhysicalArray();
        mainMenu();
    }
    //  PART 2 —Task 6
    static void initPhysicalArray() {
        BankAccount[] physicalAccounts = new BankAccount[3];
        physicalAccounts[0] = new BankAccount("ACC001", "Ali",    150000);
        physicalAccounts[1] = new BankAccount("ACC002", "Sara",   220000);
        physicalAccounts[2] = new BankAccount("ACC003", "Arman",  310000);

        System.out.println("=== PART 2: Physical Data Structures (Array) ===");
        for (int i = 0; i < physicalAccounts.length; i++) {
            System.out.println((i + 1) + ". " + physicalAccounts[i]);
        }
        System.out.println();
        Collections.addAll(accounts, physicalAccounts);
    }
    //  PART 3
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
                case "4" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
    //  PART 3 — BANK MENU
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
    //  PART 3 — ATM MENU
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
    //  PART 3 — ADMIN MENU
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
    //  PART 1 — Task 1
    static void displayAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
        System.out.println("Accounts List:");
        int i = 1;
        for (BankAccount acc : accounts) {
            System.out.println(i++ + ". " + acc.username + " -- Balance: " + acc.balance);
        }
    }
    //  PART 1 — Task 1
    static void searchByUsername() {
        System.out.print("Enter username to search: ");
        String name = scanner.nextLine().trim();
        for (BankAccount acc : accounts) {
            if (acc.username.equalsIgnoreCase(name)) {
                System.out.println("Found: " + acc);
                return;
            }
        }
        System.out.println("Account not found.");
    }
    //  PART 1 — Task 1
    static BankAccount findAccount(String username) {
        for (BankAccount acc : accounts) {
            if (acc.username.equalsIgnoreCase(username)) return acc;
        }
        return null;
    }
    //  PART 1 — Task 2
    static void deposit() {
        System.out.print("Enter username: ");
        String name = scanner.nextLine().trim();
        BankAccount acc = findAccount(name);
        if (acc == null) { System.out.println("Account not found."); return; }

        System.out.print("Deposit amount: ");
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            if (amount <= 0) { System.out.println("Amount must be positive."); return; }
            acc.balance += amount;
            System.out.println("New balance: " + acc.balance);

            // Task 3
            String record = "Deposit " + amount + " to " + name;
            transactionHistory.push(record);
            System.out.println("Transaction recorded: " + record);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
        }
    }
    //  PART 1 — Task 2
    static void withdraw() {
        System.out.print("Enter username: ");
        String name = scanner.nextLine().trim();
        BankAccount acc = findAccount(name);
        if (acc == null) { System.out.println("Account not found."); return; }

        System.out.print("Withdraw amount: ");
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            if (amount <= 0) { System.out.println("Amount must be positive."); return; }
            if (acc.balance < amount) { System.out.println("Insufficient funds."); return; }
            acc.balance -= amount;
            System.out.println("New balance: " + acc.balance);

            // Task 3: записать транзакцию в Stack
            String record = "Withdraw " + amount + " from " + name;
            transactionHistory.push(record);
            System.out.println("Transaction recorded: " + record);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
        }
    }
    //  ATM
    static void balanceEnquiry() {
        System.out.print("Enter username: ");
        String name = scanner.nextLine().trim();
        BankAccount acc = findAccount(name);
        if (acc == null) { System.out.println("Account not found."); return; }
        System.out.println("Balance for " + acc.username + ": " + acc.balance);
    }
    //  PART 1 — Task 3
    static void showTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }
        System.out.println("Transaction History (last first):");
        for (int i = transactionHistory.size() - 1; i >= 0; i--) {
            System.out.println("  - " + transactionHistory.get(i));
        }
        System.out.println("Last transaction (peek): " + transactionHistory.peek());
    }
    //  PART 1 — Task 3
    static void undoLastTransaction() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions to undo.");
            return;
        }
        String removed = transactionHistory.pop();
        System.out.println("Undo → " + removed + " removed.");
    }
    //  PART 1 — Task 4
    static void addBill() {
        System.out.print("Enter bill description (e.g. Electricity Bill): ");
        String bill = scanner.nextLine().trim();
        if (bill.isEmpty()) { System.out.println("Description cannot be empty."); return; }
        billQueue.add(bill);
        System.out.println("Added: " + bill);
    }
    //  PART 1 — Task 4
    static void processNextBill() {
        if (billQueue.isEmpty()) { System.out.println("Bill queue is empty."); return; }
        String processed = billQueue.poll();
        System.out.println("Processing: " + processed);

        // Task 3
        String record = "Bill payment: " + processed;
        transactionHistory.push(record);

        if (!billQueue.isEmpty()) {
            System.out.println("Remaining: " + billQueue.peek());
        } else {
            System.out.println("No remaining bills.");
        }
    }
    //  PART 1 — Task 4
    static void displayBillQueue() {
        if (billQueue.isEmpty()) { System.out.println("Bill queue is empty."); return; }
        System.out.println("Bill Queue:");
        int i = 1;
        for (String bill : billQueue) {
            System.out.println(i++ + ". " + bill);
        }
    }
    //  PART 1 — Task 5
    static void submitAccountRequest() {
        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine().trim();
        System.out.print("Enter username: ");
        String uname = scanner.nextLine().trim();
        System.out.print("Enter initial balance: ");
        try {
            double bal = Double.parseDouble(scanner.nextLine().trim());
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