import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 6529685098267757690L;
    private final int accountNumber;
    private final User holder;
    private double balance;
    private final ArrayList<Transactions> transactions;
    private final Bank bank;
    private final int pin;

    Account(int accountNumber, User holder, Bank bank, double balance, int pin) {
        this.accountNumber = accountNumber;
        this.holder = holder;
        this.transactions = new ArrayList<>();
        this.bank = bank;
        this.balance = balance;
        this.pin = pin;
    }

    public int getAccountNumber() {
        return accountNumber;
    }
    public double getBalance() {
        return this.balance;
    }

    public void addTransaction(double amount, Transactions transaction) {
        balance += amount;
        transactions.add(transaction);
    }

    //Transfer money to another holder and create new transaction for the user
    public boolean transferMoney(double amount, Account receivingAccount, String currency) {
        Transactions newTransaction = new Transactions(amount, "Money transfer", this);
        return newTransaction.moneyTransfer(this, receivingAccount, amount ,newTransaction, currency);
    }

    //Withdraw money and create new transaction for the user
    public boolean cashWithdrawal(double amount, String currency) {
         Transactions newTransaction = new Transactions(amount, "Cash withdrawal", this);
         return newTransaction.cashWithdrawal(this, amount, newTransaction, currency);
    }

    //Deposit money and create new transaction for the user
    public boolean cashDeposit(double amount, String currency) {
        Transactions newTransaction = new Transactions(amount, "Cash deposit", this);
        return newTransaction.cashDeposit(this, amount, newTransaction, currency);
    }

    //Validate that pin is correct
    public boolean validate(int pin) {
        return this.pin == pin;
    }
    public Bank getBank() {
        return this.bank;
    }

    public ArrayList<Transactions> getTransactions() {
        return transactions;
    }

    public User getHolder() {
        return holder;
    }
}