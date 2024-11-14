import java.util.*;

// User class
class User {
    private String userId;
    private String pin;
    private Account account;

    public User(String userId, String pin, double initialBalance) {
        this.userId = userId;
        this.pin = pin;
        this.account = new Account(initialBalance);
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public Account getAccount() {
        return account;
    }
}

// Account class
class Account {
    private double balance;
    private TransactionHistory transactionHistory;

    public Account(double initialBalance) {
        this.balance = initialBalance;
        this.transactionHistory = new TransactionHistory();
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.addTransaction(new Transaction("Deposit", amount));
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println( "Balance after withdrawal: " + balance );
            transactionHistory.addTransaction(new Transaction("Withdraw", amount));
            return true;
        }
        return false;
    }

    public boolean transfer(Account targetAccount, double amount) {
        if (withdraw(amount)) {
            targetAccount.deposit(amount);
            transactionHistory.addTransaction(new Transaction("Transfer", amount));
            return true;
        }
        return false;
    }

    public TransactionHistory getTransactionHistory() {
        return transactionHistory;
    }
}

// Transaction class
class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return type + ": Rs. " + amount;
    }
}

// TransactionHistory class
class TransactionHistory {
    private List<Transaction> transactions;

    public TransactionHistory() {
        transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void displayHistory() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }
}

// ATM class
public class ATM {
    private static final Scanner scanner = new Scanner(System.in);
    private User currentUser ;

    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.initializeUsers();
        atm.start();
    }

    private void initializeUsers() {
        // In a real application, users would be stored in a database.
        currentUser  = new User("user01", "1234", 1000.00);
    }

    private void start() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        if (userId.equals(currentUser.getUserId()) && pin.equals(currentUser.getPin())) {
            System.out.println("Access Granted!");
            atmMenu();
        } else {
            System.out.println("Invalid User ID or PIN.");
        }
    }
    //ATM Menu
    private void atmMenu() {
        while (true) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    currentUser .getAccount().getTransactionHistory().displayHistory();
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    if (currentUser .getAccount().withdraw(withdrawAmount)) {
                        System.out.println("Withdraw successful!");
                    } else {
                        System.out.println("Insufficient funds.");
                    }
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    currentUser .getAccount().deposit(depositAmount);
                    System.out.println("Deposit successful!");
                    break;
                case 4:
                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = scanner.nextDouble();
                    System.out.print("Enter target User ID (for demo, it will be the same user): ");
                    String targetUserId = scanner.next(); 
                    if (targetUserId.equals(currentUser .getUserId())) {
                        if (currentUser .getAccount().transfer(currentUser .getAccount(), transferAmount)) {
                            System.out.println("Transfer successful!");
                        } else {
                            System.out.println("Insufficient funds.");
                        }
                    } else {
                        System.out.println("Invalid target User ID.");
                        
                    }
                    break;
                case 5:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            scanner.nextLine(); // Consume newline left-over
        }
    }
}