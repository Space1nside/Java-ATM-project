import javax.swing.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class BankController {
    private final ArrayList<Bank> banks;
    private final BankView bankView;
    private final KeyAdapter restrictCharacters = new RestrictCharacters();
    private final ActionListener checkLogin = new CheckLogin(this);
    private final ActionListener performAction = new PerformAction();
    private final WindowAdapter windowClosing = new WindowClosing();
    private Account opAcc;
    private final TransactionsTable transactions;

    BankController(ArrayList<Bank> banks, BankView bankView) {
        this.banks = banks;
        this.bankView = bankView;
        transactions = new TransactionsTable();
    }

    //Initialize controller for login window
    public void initController() {
        //Restricting characters so that user could only type numbers in the text field
        bankView.getLoginField().addKeyListener(restrictCharacters);
        bankView.getPasswordField().addKeyListener(restrictCharacters);
        bankView.getEnterButton().addActionListener(checkLogin);
    }

    //Initializing controller for ATM main menu frame
    public void initATMController() {
        bankView.getTransferMoneyButton().setActionCommand("Transfer money");
        bankView.getWithdrawMoneyButton().setActionCommand("Withdraw money");
        bankView.getCheckBalanceButton().setActionCommand("Check balance");
        bankView.getShowTransactionsButton().setActionCommand("Show transactions");
        bankView.getDepositMoneyButton().setActionCommand("Deposit money");
        bankView.getTransferMoneyButton().addActionListener(performAction);
        bankView.getWithdrawMoneyButton().addActionListener(performAction);
        bankView.getCheckBalanceButton().addActionListener(performAction);
        bankView.getShowTransactionsButton().addActionListener(performAction);
        bankView.getDepositMoneyButton().addActionListener(performAction);
        bankView.getATMFrame().addWindowListener(windowClosing);
    }

    //Initializing controller for panel where user should enter account number he wants to transfer money to in the same ATM frame
    public void initAccCheckController() {
        bankView.getCancelButton().setActionCommand("Cancel account confirmation");
        bankView.getConfirmButton().setActionCommand("Confirm account");
        bankView.getAccountNumField().addKeyListener(restrictCharacters);
        bankView.getCancelButton().addActionListener(performAction);
        bankView.getConfirmButton().addActionListener(performAction);
    }

    /*
        Initializing controller for panel where user should enter amount of money he wants to send
        after user enters correct account number he is transferring money to
    */
    public void initTransferController() {
        bankView.getCancelButton().setActionCommand("Cancel transfer");
        bankView.getConfirmButton().setActionCommand("Confirm transfer");
        bankView.getCancelButton().addActionListener(performAction);
        bankView.getConfirmButton().addActionListener(performAction);
    }

    //Initializing controller for panel where user should choose how much money he wants to send from the suggested amounts
    public void initSelectWithdrawalController() {
        bankView.getCancelButton().setActionCommand("Cancel withdrawal");

        for (JButton selectButton: bankView.getSelectAmountButtons()) {
            selectButton.setActionCommand(selectButton.getText());
            selectButton.addActionListener(performAction);
        }

        bankView.getCancelButton().addActionListener(performAction);
    }

    //Initializing controller for panel where user should choose a custom amount of money and currency
    public void initWithdrawAnotherSumController() {
        bankView.getCancelButton().setActionCommand("Cancel withdrawing");
        bankView.getConfirmButton().setActionCommand("Confirm withdrawing");
        bankView.getCancelButton().addActionListener(performAction);
        bankView.getConfirmButton().addActionListener(performAction);
    }

    //Initializing controller for panel where user can see his balance
    public void initCheckBalanceController() {
        bankView.getCancelButton().setActionCommand("Go back");
        bankView.getCancelButton().addActionListener(performAction);
    }

    //Initializing controller for panel where user can see all of jis transactions
    public void initShowTransactionsController() {
        bankView.getCancelButton().setActionCommand("Go back from transactions");
        bankView.getCancelButton().addActionListener(performAction);
    }

    //Initializing controller for panel where user can deposit money to his account by entering amount and currency
    public void initDepositMoneyController() {
        bankView.getCancelButton().setActionCommand("Cancel deposit");
        bankView.getConfirmButton().setActionCommand("Confirm deposit");
        bankView.getCancelButton().addActionListener(performAction);
        bankView.getConfirmButton().addActionListener(performAction);
    }

    //Restricting characters so that user could only type numbers in the text field
    static class RestrictCharacters extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                e.consume();
            }
        }
    }

    //Serializing modified by user banks to a file HoldersData when window is closed
    public void serializeBanks(ArrayList<Bank> banks) {
        try
        {
            FileOutputStream fos = new FileOutputStream("HoldersData");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(banks);
            oos.close();
            fos.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public class WindowClosing extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            serializeBanks(banks);
        }
    }

    //Action listener to check if entered by user credentials are correct in order to log in into account
     class CheckLogin implements ActionListener {
        BankController controller;
        CheckLogin(BankController controller) {
            this.controller = controller;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            //If user did not enter either account number or pin or both
            if(bankView.getLoginField().getText().equals("") || (String.valueOf(bankView.getPasswordField().getPassword()).equals(""))) {
                bankView.showIncorrectLoginMsg("Please enter credentials");
            } else {
                int i = 0;
                while(opAcc == null && i < banks.size()) {
                    //Check if the entered account number exists in the banks and if the pin to the account is correct
                    //opAcc is null if credentials are wrong
                    opAcc = banks.get(i).checkLogin(Integer.parseInt(bankView.getLoginField().getText()), Integer.parseInt(String.valueOf(bankView.getPasswordField().getPassword())));
                    i++;
                }

                //If user entered correct credentials opAcc should not be null
                if(opAcc != null){
                    //Initialize ATM frame
                    bankView.initATM();
                    controller.initATMController();
                    //Add user's previous transactions if he did them before login in
                    for (Transactions transaction: opAcc.getTransactions()) {
                        transactions.addTransaction(transaction.getName(), String.valueOf(transaction.getAmount()), transaction.getCurrentDate(), "Ft");
                    }
                } else {
                    //Show message that entered credentials are wrong
                    bankView.showIncorrectLoginMsg("Incorrect credentials");
                }
            }
        }
    }

    //Action listeners for all the buttons and text fields in the ATM frame
    class PerformAction implements ActionListener{
        private Account toAcc;
        @Override
        public void actionPerformed(ActionEvent e) {
            //User clicks on Transfer money button
            if(e.getActionCommand().equals("Transfer money")) {
                //Initialize another panel where user enters account number to which he wants to send money
                bankView.initAccCheckPanel();
                initAccCheckController();
                //User clicks on Confirm button
            } else if(e.getActionCommand().equals("Confirm account")) {
                //If user did not enter any account number the message is shown
                if(bankView.getAccountNumField().getText().equals("")) {
                    bankView.showMsg("Please enter account number", "Message");
                } else {
                    int i = 0;
                    int accNumberInField = Integer.parseInt(bankView.getAccountNumField().getText());
                    while(toAcc == null && i < banks.size()) {
                        //Check if the entered account to which user wants to send money exists in the banks
                        //hasAccount should return account in one of the banks if it exists
                        toAcc = banks.get(i).hasAccount(accNumberInField);
                        i++;
                    }
                    /*
                        If entered account exists, and it is not the same account the user logged into
                        initialize another panel where user should enter amount of money he wants to send and currency
                    */
                    if(toAcc != null && accNumberInField != opAcc.getAccountNumber()){
                        bankView.initTransferPanel();
                        initTransferController();
                        //If user entered his own account number in the field, show error message
                    } else if(accNumberInField == opAcc.getAccountNumber()) {
                        bankView.showMsg("Incorrect account number", "Message");
                        //If user entered non-existing account number
                    } else {
                        bankView.showMsg("Incorrect account number", "Message");
                    }
                }
                //User clicks on cancel button go back to main menu ATM frame
            } else if(e.getActionCommand().equals("Cancel account confirmation")) {
                bankView.addBack(bankView.getAccNumTransferPanel(), bankView.getATMPanel());
                //User clicks on Confirm button in the panel where he should enter amount of money he wants to send and currency
            } else if(e.getActionCommand().equals("Confirm transfer")) {
                double amount = Double.parseDouble(bankView.getEnterAmountField().getText());
                //User selected HUF currency
                if(bankView.getHUF().isSelected()) {
                    if(opAcc.transferMoney(amount, toAcc, "HUF")) {
                        //Show message that money are transferred and ask user if he wants to continue transferring money
                        int answer = bankView.showMsg("Money successfully transferred. Would you like to continue?", "Confirm");
                        //Adds new transaction of the user to the transaction table
                        Transactions transaction = opAcc.getTransactions().get(opAcc.getTransactions().size()-1);
                        transactions.addTransaction(transaction.getName(), String.valueOf(transaction.getAmount()), transaction.getCurrentDate(), "Ft");
                        if(answer == JOptionPane.NO_OPTION) {
                            bankView.addBack(bankView.getEnterAmountPanel(), bankView.getATMPanel());
                        }
                    } else {
                        //If user doesn't have amount of money he entered show error message
                        bankView.showMsg("No sufficient funds", "Message");
                    }
                    //User selects USD currency
                } else if(bankView.getUSD().isSelected()) {
                    if(opAcc.transferMoney(amount, toAcc, "USD")) {
                        int decision = bankView.showMsg("Conversion rate: 1$ = 389.11Ft. Would you like to proceed?", "Confirm");
                        if(decision == JOptionPane.YES_OPTION) {
                            decision = bankView.showMsg("Money successfully transferred. Would you like to continue?", "Confirm");
                            Transactions transaction = opAcc.getTransactions().get(opAcc.getTransactions().size()-1);
                            transactions.addTransaction(transaction.getName(), String.valueOf(transaction.getAmount()), transaction.getCurrentDate(), "$");
                            if(decision == JOptionPane.NO_OPTION) {
                                bankView.addBack(bankView.getEnterAmountPanel(), bankView.getATMPanel());
                            }
                        }
                    } else {
                        bankView.showMsg("No sufficient funds", "Message");
                    }
                }
                //User cancel transaction and goes back to panek where he should enter account number to which he wants to send money
            } else if(e.getActionCommand().equals("Cancel transfer")) {
                bankView.addBack(bankView.getEnterAmountPanel(), bankView.getAccNumTransferPanel());
            } else if(e.getActionCommand().equals("Withdraw money")) {
                bankView.initWithdrawPanel();
                initSelectWithdrawalController();
            } else if(e.getActionCommand().equals("Cancel withdrawal")) {
                bankView.addBack(bankView.getWithdrawPanel(), bankView.getATMPanel());
            } else if(e.getActionCommand().equals("1000")) {
                if(opAcc.cashWithdrawal(1000, "HUF")) {
                    int answer = bankView.showMsg("Money successfully withdrawn. Would you like to continue?", "Confirm");
                    Transactions transaction = opAcc.getTransactions().get(opAcc.getTransactions().size()-1);
                    transactions.addTransaction(transaction.getName(), String.valueOf(transaction.getAmount()), transaction.getCurrentDate(), "Ft");
                    if(answer == JOptionPane.NO_OPTION) {
                        bankView.addBack(bankView.getWithdrawPanel(), bankView.getATMPanel());
                    }
                } else {
                    bankView.showMsg("No sufficient funds", "Message");
                }
            } else if(e.getActionCommand().equals("5000")) {
                if(opAcc.cashWithdrawal(5000, "HUF")) {
                    int answer = bankView.showMsg("Money successfully withdrawn. Would you like to continue?", "Confirm");
                    Transactions transaction = opAcc.getTransactions().get(opAcc.getTransactions().size()-1);
                    transactions.addTransaction(transaction.getName(), String.valueOf(transaction.getAmount()), transaction.getCurrentDate(), "Ft");
                    if(answer == JOptionPane.NO_OPTION) {
                        bankView.addBack(bankView.getWithdrawPanel(), bankView.getATMPanel());
                    }
                } else {
                    bankView.showMsg("No sufficient funds", "Message");
                }
            } else if(e.getActionCommand().equals("10000")) {
                if(opAcc.cashWithdrawal(10000, "HUF")) {
                    int answer = bankView.showMsg("Money successfully withdrawn. Would you like to continue?", "Confirm");
                    Transactions transaction = opAcc.getTransactions().get(opAcc.getTransactions().size()-1);
                    transactions.addTransaction(transaction.getName(), String.valueOf(transaction.getAmount()), transaction.getCurrentDate(), "Ft");
                    if(answer == JOptionPane.NO_OPTION) {
                        bankView.addBack(bankView.getWithdrawPanel(), bankView.getATMPanel());
                    }
                } else {
                    bankView.showMsg("No sufficient funds", "Message");
                }
            } else if(e.getActionCommand().equals("20000")) {
                if(opAcc.cashWithdrawal(20000, "HUF")) {
                    int answer = bankView.showMsg("Money successfully withdrawn. Would you like to continue?", "Confirm");
                    Transactions transaction = opAcc.getTransactions().get(opAcc.getTransactions().size()-1);
                    transactions.addTransaction(transaction.getName(), String.valueOf(transaction.getAmount()), transaction.getCurrentDate(), "Ft");
                    if(answer == JOptionPane.NO_OPTION) {
                        bankView.addBack(bankView.getWithdrawPanel(), bankView.getATMPanel());
                    }
                } else {
                    bankView.showMsg("No sufficient funds", "Message");
                }
            } else if(e.getActionCommand().equals("30000")) {
                if(opAcc.cashWithdrawal(30000, "HUF")) {
                    int answer = bankView.showMsg("Money successfully withdrawn. Would you like to continue?", "Confirm");
                    Transactions transaction = opAcc.getTransactions().get(opAcc.getTransactions().size()-1);
                    transactions.addTransaction(transaction.getName(), String.valueOf(transaction.getAmount()), transaction.getCurrentDate(), "Ft");
                    if(answer == JOptionPane.NO_OPTION) {
                        bankView.addBack(bankView.getWithdrawPanel(), bankView.getATMPanel());
                    }
                } else {
                    bankView.showMsg("No sufficient funds", "Message");
                }
            } else if(e.getActionCommand().equals("60000")) {
                if(opAcc.cashWithdrawal(60000, "HUF")) {
                    int answer = bankView.showMsg("Money successfully withdrawn. Would you like to continue?", "Confirm");
                    Transactions transaction = opAcc.getTransactions().get(opAcc.getTransactions().size()-1);
                    transactions.addTransaction(transaction.getName(), String.valueOf(transaction.getAmount()), transaction.getCurrentDate(), "Ft");
                    if(answer == JOptionPane.NO_OPTION) {
                        bankView.addBack(bankView.getWithdrawPanel(), bankView.getATMPanel());
                    }
                } else {
                    bankView.showMsg("No sufficient funds", "Message");
                }
            } else if(e.getActionCommand().equals("100000")) {
                if(opAcc.cashWithdrawal(100000, "HUF")) {
                    int answer = bankView.showMsg("Money successfully withdrawn. Would you like to continue?", "Confirm");
                    Transactions transaction = opAcc.getTransactions().get(opAcc.getTransactions().size()-1);
                    transactions.addTransaction(transaction.getName(), String.valueOf(transaction.getAmount()), transaction.getCurrentDate(), "Ft");
                    if(answer == JOptionPane.NO_OPTION) {
                        bankView.addBack(bankView.getWithdrawPanel(), bankView.getATMPanel());
                    }
                } else {
                    bankView.showMsg("No sufficient funds", "Message");
                }
            } else if(e.getActionCommand().equals("Select other amount")) {
                bankView.initAnotherAmount();
                initWithdrawAnotherSumController();
            } else if(e.getActionCommand().equals("Confirm withdrawing")) {
                double amount = Double.parseDouble(bankView.getEnterAmountField().getText());
                if(bankView.getHUF().isSelected()) {
                    if(opAcc.cashWithdrawal(amount, "HUF")) {
                        int answer = bankView.showMsg("Money successfully withdrawn. Would you like to continue?", "Confirm");
                        Transactions transaction = opAcc.getTransactions().get(opAcc.getTransactions().size()-1);
                        transactions.addTransaction(transaction.getName(), String.valueOf(transaction.getAmount()), transaction.getCurrentDate(), "Ft");
                        if(answer == JOptionPane.NO_OPTION) {
                            bankView.addBack(bankView.getEnterAmountPanel(), bankView.getATMPanel());
                        }
                    } else {
                        bankView.showMsg("No sufficient funds", "Message");
                    }
                } else if(bankView.getUSD().isSelected()) {
                    if(opAcc.cashWithdrawal(amount, "USD")) {
                        int decision = bankView.showMsg("Conversion rate: 1$ = 389.11Ft. Would you like to proceed?", "Confirm");
                        if(decision == JOptionPane.YES_OPTION) {
                            decision = bankView.showMsg("Money successfully withdrawn. Would you like to continue?", "Confirm");
                            Transactions transaction = opAcc.getTransactions().get(opAcc.getTransactions().size()-1);
                            transactions.addTransaction(transaction.getName(), String.valueOf(transaction.getAmount()), transaction.getCurrentDate(), "$");
                            if(decision == JOptionPane.NO_OPTION) {
                                bankView.addBack(bankView.getEnterAmountPanel(), bankView.getATMPanel());
                            }
                        }

                    } else {
                        bankView.showMsg("No sufficient funds", "Message");
                    }
                }
            } else if(e.getActionCommand().equals("Cancel withdrawing")) {
                bankView.addBack(bankView.getEnterAmountPanel(), bankView.getATMPanel());
            } else if(e.getActionCommand().equals("Check balance")) {
                String accountNum = String.valueOf(opAcc.getAccountNumber());
                String balance = String.valueOf(opAcc.getBalance());
                String firstName = opAcc.getHolder().getFirstName();
                String lastName = opAcc.getHolder().getLastName();
                bankView.initCheckBalance(accountNum, balance, firstName, lastName);
                initCheckBalanceController();
            } else if(e.getActionCommand().equals("Go back")) {
                bankView.addBack(bankView.getCheckBalancePanel(), bankView.getATMPanel());
            } else if(e.getActionCommand().equals("Show transactions")) {
                if(transactions.getRowCount() == 0) {
                    bankView.showMsg("No transactions found", "Message");
                } else {
                    bankView.initShowTransactions(transactions);
                    initShowTransactionsController();
                }

            } else if (e.getActionCommand().equals("Go back from transactions")) {
                bankView.addBack(bankView.getShowTransactionsPanel(), bankView.getATMPanel());
            } else if(e.getActionCommand().equals("Deposit money")) {
                bankView.initCashDeposit();
                initDepositMoneyController();
            } else if(e.getActionCommand().equals("Confirm deposit")) {
                double amount = Double.parseDouble(bankView.getEnterAmountField().getText());
                if(bankView.getHUF().isSelected()) {
                    if(opAcc.cashDeposit(amount, "HUF")) {
                        int answer = bankView.showMsg("Money successfully withdrawn. Would you like to continue?", "Confirm");
                        Transactions transaction = opAcc.getTransactions().get(opAcc.getTransactions().size()-1);
                        transactions.addTransaction(transaction.getName(), String.valueOf(transaction.getAmount()), transaction.getCurrentDate(), "Ft");
                        if(answer == JOptionPane.NO_OPTION) {
                            bankView.addBack(bankView.getDepositMoneyPanel(), bankView.getATMPanel());
                        }
                    }
                } else if(bankView.getUSD().isSelected()) {
                    if(opAcc.cashDeposit(amount, "USD")) {
                        int decision = bankView.showMsg("Conversion rate: 1$ = 389.11Ft. Would you like to proceed?", "Confirm");
                        if(decision == JOptionPane.YES_OPTION) {
                            decision = bankView.showMsg("Money successfully withdrawn. Would you like to continue?", "Confirm");
                            Transactions transaction = opAcc.getTransactions().get(opAcc.getTransactions().size()-1);
                            transactions.addTransaction(transaction.getName(), String.valueOf(transaction.getAmount()), transaction.getCurrentDate(), "$");
                            if(decision == JOptionPane.NO_OPTION) {
                                bankView.addBack(bankView.getDepositMoneyPanel(), bankView.getATMPanel());
                            }
                        }

                    }
                }
            } else if(e.getActionCommand().equals("Cancel deposit")) {
                bankView.addBack(bankView.getDepositMoneyPanel(), bankView.getATMPanel());
            }
        }
    }
}