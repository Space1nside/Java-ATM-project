import java.io.Serializable;
import java.time.LocalDate;

public class Transactions implements Serializable {
    private final double amount;
    private final String name;
    Account account;
    LocalDate currentDate;

    public Transactions(double amount, String name, Account account) {
        this.amount = amount;
        this.name = name;
        this.account = account;
        currentDate = LocalDate.now();
    }

    public boolean moneyTransfer(Account transferringAccount, Account receivingAccount, double amount, Transactions newTransaction, String currency) {
        //Check if currency is HUF
        if(currency.equals("HUF")) {
            //Check if amount is less than user's balance
            if(transferringAccount.getBalance() >= amount) {
                //add one transaction to the user and one transaction to another holder to whom user send money
                transferringAccount.addTransaction(amount * -1, newTransaction);
                receivingAccount.addTransaction(amount, new Transactions(amount, "Money received", receivingAccount));
                return true;
            }
            //Check if currency is USD
        } else if(currency.equals("USD")) {
            if(transferringAccount.getBalance() >= amount * 389.11) {
                transferringAccount.addTransaction((amount * 389.11) * -1, newTransaction);
                receivingAccount.addTransaction(amount * 389.11, new Transactions(amount, "Money received", receivingAccount));
                return true;
            }
        }
        return false;
    }

    public boolean cashWithdrawal(Account account, double amount, Transactions newTransaction, String currency) {
        if(currency.equals("HUF")) {
            if(account.getBalance() >= amount) {
                account.addTransaction(amount * -1, newTransaction);
                return true;
            }
        } else if(currency.equals("USD")) {
            if(account.getBalance() >= amount * 389.11) {
                account.addTransaction((amount * 389.11) * -1, newTransaction);
                return true;
            }
        }

        return false;
    }

    public boolean cashDeposit(Account account, double amount, Transactions newTransaction, String currency) {
        if(currency.equals("HUF")) {
            account.addTransaction(amount, newTransaction);
            return true;

        } else if(currency.equals("USD")) {
            account.addTransaction(amount * 389.11, newTransaction);
            return true;

        }
        return true;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrentDate() {
        return currentDate.toString();
    }

}
