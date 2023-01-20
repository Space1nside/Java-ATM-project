import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Bank implements Serializable {
    @Serial
    private static final long serialVersionUID = 6529685098267757690L;
    private final String name; //Bank name
    private final ArrayList<Account> accounts; //Array of accounts in the bank
    private static ArrayList<Integer> branch;

    static {
        branch = new ArrayList<>();
    }

    Bank(String name) {
        this.name = name;
        this.accounts = new ArrayList<>();
    }
    //Adding new account to the bank if such account is not already in the bank
    private void createAccount(Account account) {
        if(!accounts.contains(account)) {
            this.accounts.add(account);
        }
    }

    //Adding new holder to the bank
    public Account addHolder(String firstName, String lastName, int accountNumber, double balance, int pin) {
        //Checking if holder with such account number already exists and if it exists holder is not added
        if (accounts.size() != 0) {
            if(!branch.isEmpty()) {
                for (Account acc : accounts) {
                    if (accountNumber == acc.getAccountNumber() || branch.contains(accountNumber)) {
                        return null;
                    }
                }
            } else {
                for (Account acc : accounts) {
                    if (accountNumber == acc.getAccountNumber()) {
                        return null;
                    }
                }
                branch.add(accountNumber);
            }
            branch.add(accountNumber);
            User user = new User(lastName, firstName);
            Account account = new Account(accountNumber, user, this, balance, pin);
            this.createAccount(account);
            user.createAccount(account);
            return account;
        } else {
            if(!branch.isEmpty()) {
                if(!branch.contains(accountNumber)) {
                    User user = new User(lastName, firstName);
                    Account account = new Account(accountNumber, user, this, balance, pin);
                    this.createAccount(account);
                    user.createAccount(account);
                    branch.add(accountNumber);
                    return account;
                } else {
                    return null;
                }
            } else {
                User user = new User(lastName, firstName);
                Account account = new Account(accountNumber, user, this, balance, pin);
                this.createAccount(account);
                user.createAccount(account);
                branch.add(accountNumber);
                return account;
            }
        }
    }

    //Check if the login is correct
    public Account checkLogin(int accountNumber, int pin) {
        for (Account account: this.accounts) {
            if(account.getAccountNumber() == accountNumber && account.validate(pin)) {
                return account;
            }
        }
        return null;
    }

    //Check if bank has account if such account number
    public Account hasAccount(int accountNumber) {
        for (Account acc: accounts) {
            if(accountNumber == acc.getAccountNumber()) {
                return acc;
            }
        }
        return null;
    }

    //Get name of the bank
    public String getName() {
        return name;
    }

    //Sets branch to be empty for testing
    public void clearBranch() {
        branch.clear();
    }
}
