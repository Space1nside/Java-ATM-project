import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.util.ArrayList;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;

public class BankView {
    //3 frames
    private JFrame authFrame;
    private JFrame ATMFrame;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JTextField accountNumField;
    private JTextField enterAmountField;
    private JLabel accNumLabel;
    private JLabel enterAmountLabel;
    private JPanel ATMPanel;
    private JPanel accNumTransferPanel;
    private JPanel enterAmountPanel;
    private JPanel moneyAmountTextPanel;
    private JPanel radioButtonPanel;
    private JPanel amountButtonPanel;
    private JPanel withdrawPanel;
    private JPanel checkBalancePanel;
    private JPanel goBackPanel;
    private JPanel showTransactionsPanel;
    private JPanel depositMoneyPanel;
    private JButton enterButton;
    private JButton transferMoneyButton;
    private JButton withdrawMoneyButton;
    private JButton checkBalanceButton;
    private JButton showTransactionsButton;
    private JButton depositMoneyButton;
    private JButton confirmButton;
    private JButton cancelButton;
    private ArrayList<JButton> selectAmountButtons;
    private JRadioButton USD;
    private JRadioButton HUF;
    private ButtonGroup HUForUSD;

    public void initAuthFrame() {
        int x = 100;
        int y = 10;
        int width = 100;
        int height = 20;
        JPanel authPanel = new JPanel();
        authPanel.setLayout(null);

        authFrame = new JFrame();
        authFrame.setLocation(new Point(500, 300));
        authFrame.add(authPanel);
        authFrame.setSize(new Dimension(400, 200));
        authFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel loginLabel = new JLabel("Account number");
        loginLabel.setBounds(x, y, width, height);
        authPanel.add(loginLabel);

        loginField = new JTextField(10);
        loginField.setBounds(x, y+20, width+93, height+8);
        loginField.setDocument(new JTextFieldLimit(9));
        authPanel.add(loginField);

        JLabel passwordLabel = new JLabel("Pin");
        passwordLabel.setBounds(x, y+45, width, height);
        authPanel.add(passwordLabel);

        passwordField = new JPasswordField(4);
        passwordField.setBounds(x, y+65, width+93, height+8);
        passwordField.setDocument(new JTextFieldLimit(4));
        authPanel.add(passwordField);

        enterButton = new JButton("Enter");
        enterButton.setBounds(x+5, y+100, width+80, height+10);
        authPanel.add(enterButton);

        authFrame.setResizable(false);
        authFrame.setVisible(true);
    }

    public void initATM() {
        authFrame.dispose();
        GridLayout gridLayout = new GridLayout(0, 2, 50, 50);
        ATMPanel = new JPanel();
        ATMPanel.setLayout(gridLayout);

        ATMFrame = new JFrame("ATM");
        ATMFrame.setLocation(new Point(500, 300));
        ATMFrame.setSize(new Dimension(480, 280));
        ATMFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        transferMoneyButton = new JButton("Transfer money");
        withdrawMoneyButton = new JButton("Withdraw money");
        checkBalanceButton = new JButton("Check balance");
        showTransactionsButton = new JButton("Show transactions");
        depositMoneyButton = new JButton("Deposit money");

        ATMPanel.add(transferMoneyButton);
        ATMPanel.add(withdrawMoneyButton);
        ATMPanel.add(checkBalanceButton);
        ATMPanel.add(showTransactionsButton);
        ATMPanel.add(depositMoneyButton);

        ATMFrame.add(ATMPanel);
        ATMFrame.setResizable(false);
        ATMFrame.setVisible(true);
    }

    public void initAccCheckPanel() {
        ATMFrame.remove(ATMPanel);
        accNumTransferPanel = new JPanel();
        accNumTransferPanel.setLayout(new BorderLayout(10, 10));

        accNumLabel = new JLabel("Enter Account number:");
        accNumLabel.setPreferredSize(new Dimension(120, 30));
        accNumLabel.setHorizontalAlignment(0);
        accNumTransferPanel.add(accNumLabel, BorderLayout.NORTH);

        JPanel accountButtonPanel = new JPanel();
        accountButtonPanel.setPreferredSize(new Dimension(70, 80));
        accNumTransferPanel.add(accountButtonPanel, BorderLayout.SOUTH);

        confirmButton = new JButton("Confirm");
        cancelButton = new JButton("Cancel");
        accountButtonPanel.add(confirmButton);
        accountButtonPanel.add(cancelButton);

        accountNumField = new JTextField(10);
        JPanel accountNumTextPanel = new JPanel();
        accountNumField.setPreferredSize(new Dimension(100, 30));
        accountNumField.setDocument(new JTextFieldLimit(9));
        accountNumTextPanel.add(accountNumField);
        accNumTransferPanel.add(accountNumTextPanel, BorderLayout.CENTER);

        ATMFrame.add(accNumTransferPanel);
        ATMFrame.repaint();
        ATMFrame.revalidate();
    }

    public void initTransferPanel() {
        ATMFrame.remove(accNumTransferPanel);

        enterAmountPanel = new JPanel(new BorderLayout(5, 5));
        enterAmountLabel = new JLabel("Enter amount:");
        enterAmountLabel.setPreferredSize(new Dimension(120, 30));
        enterAmountLabel.setHorizontalAlignment(0);
        enterAmountPanel.add(enterAmountLabel, BorderLayout.NORTH);

        enterAmountField = new JTextField();
        moneyAmountTextPanel = new JPanel();
        enterAmountField.setPreferredSize(new Dimension(250, 30));
        moneyAmountTextPanel.add(enterAmountField);
        enterAmountPanel.add(moneyAmountTextPanel, BorderLayout.CENTER);

        radioButtonPanel = new JPanel();
        HUForUSD = new ButtonGroup();
        HUF = new JRadioButton("HUF", true);
        USD = new JRadioButton("USD");
        HUForUSD.add(HUF);
        HUForUSD.add(USD);
        radioButtonPanel.add(HUF);
        radioButtonPanel.add(USD);
        enterAmountPanel.add(radioButtonPanel, BorderLayout.SOUTH);

        amountButtonPanel = new JPanel();
        amountButtonPanel.setPreferredSize(new Dimension(80, 80));
        enterAmountPanel.add(amountButtonPanel, BorderLayout.EAST);

        confirmButton = new JButton("Confirm");
        cancelButton = new JButton("Cancel");
        amountButtonPanel.add(confirmButton);
        amountButtonPanel.add(cancelButton);

        ATMFrame.add(enterAmountPanel);
        ATMFrame.repaint();
        ATMFrame.revalidate();
    }

    public void initWithdrawPanel() {
        ATMFrame.remove(ATMPanel);

        withdrawPanel = new JPanel(new BorderLayout(5, 5));
        JPanel selectAmountPanel = new JPanel(new GridLayout(0, 2, 20, 20));
        withdrawPanel.add(selectAmountPanel, BorderLayout.CENTER);

        selectAmountButtons = new ArrayList<>();
        selectAmountButtons.add(new JButton("1000"));
        selectAmountButtons.add(new JButton("5000"));
        selectAmountButtons.add(new JButton("10000"));
        selectAmountButtons.add(new JButton("20000"));
        selectAmountButtons.add(new JButton("30000"));
        selectAmountButtons.add(new JButton("60000"));
        selectAmountButtons.add(new JButton("100000"));
        selectAmountButtons.add(new JButton("Select other amount"));
        for (JButton selectAmountButton : selectAmountButtons) {
            selectAmountPanel.add(selectAmountButton);
        }

        JLabel selectAmountTopLabel = new JLabel("Select amount:");
        selectAmountTopLabel.setHorizontalAlignment(0);
        withdrawPanel.add(selectAmountTopLabel, BorderLayout.NORTH);

        JPanel selectAmountBottomPanel = new JPanel();
        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(80, 20));
        selectAmountBottomPanel.add(cancelButton);
        withdrawPanel.add(selectAmountBottomPanel, BorderLayout.SOUTH);

        ATMFrame.add(withdrawPanel);
        ATMFrame.repaint();
        ATMFrame.revalidate();
    }

    public void initAnotherAmount() {
        ATMFrame.remove(withdrawPanel);

        enterAmountPanel = new JPanel(new BorderLayout(5, 5));
        enterAmountLabel = new JLabel("Enter amount:");
        enterAmountLabel.setPreferredSize(new Dimension(120, 30));
        enterAmountLabel.setHorizontalAlignment(0);
        enterAmountPanel.add(enterAmountLabel, BorderLayout.NORTH);

        enterAmountField = new JTextField();
        moneyAmountTextPanel = new JPanel();
        enterAmountField.setPreferredSize(new Dimension(250, 30));
        moneyAmountTextPanel.add(enterAmountField);
        enterAmountPanel.add(moneyAmountTextPanel, BorderLayout.CENTER);

        radioButtonPanel = new JPanel();
        HUForUSD = new ButtonGroup();
        HUF = new JRadioButton("HUF", true);
        USD = new JRadioButton("USD");
        HUForUSD.add(HUF);
        HUForUSD.add(USD);
        radioButtonPanel.add(HUF);
        radioButtonPanel.add(USD);
        enterAmountPanel.add(radioButtonPanel, BorderLayout.SOUTH);

        amountButtonPanel = new JPanel();
        amountButtonPanel.setPreferredSize(new Dimension(80, 80));
        enterAmountPanel.add(amountButtonPanel, BorderLayout.EAST);

        confirmButton = new JButton("Confirm");
        cancelButton = new JButton("Cancel");
        amountButtonPanel.add(confirmButton);
        amountButtonPanel.add(cancelButton);

        ATMFrame.add(enterAmountPanel);
        ATMFrame.repaint();
        ATMFrame.revalidate();
    }

    public void initCashDeposit() {
        ATMFrame.remove(ATMPanel);

        depositMoneyPanel = new JPanel(new BorderLayout(5, 5));
        enterAmountLabel = new JLabel("Enter amount:");
        enterAmountLabel.setPreferredSize(new Dimension(120, 30));
        enterAmountLabel.setHorizontalAlignment(0);
        depositMoneyPanel.add(enterAmountLabel, BorderLayout.NORTH);

        enterAmountField = new JTextField();
        moneyAmountTextPanel = new JPanel();
        enterAmountField.setPreferredSize(new Dimension(250, 30));
        moneyAmountTextPanel.add(enterAmountField);
        depositMoneyPanel.add(moneyAmountTextPanel, BorderLayout.CENTER);

        radioButtonPanel = new JPanel();
        HUForUSD = new ButtonGroup();
        HUF = new JRadioButton("HUF", true);
        USD = new JRadioButton("USD");
        HUForUSD.add(HUF);
        HUForUSD.add(USD);
        radioButtonPanel.add(HUF);
        radioButtonPanel.add(USD);
        depositMoneyPanel.add(radioButtonPanel, BorderLayout.SOUTH);

        amountButtonPanel = new JPanel();
        amountButtonPanel.setPreferredSize(new Dimension(80, 80));
        depositMoneyPanel.add(amountButtonPanel, BorderLayout.EAST);

        confirmButton = new JButton("Confirm");
        cancelButton = new JButton("Cancel");
        amountButtonPanel.add(confirmButton);
        amountButtonPanel.add(cancelButton);

        ATMFrame.add(depositMoneyPanel);
        ATMFrame.repaint();
        ATMFrame.revalidate();
    }

    public void initCheckBalance(String accountNum, String amount, String firstname, String lastname) {
        ATMFrame.remove(ATMPanel);
        checkBalancePanel = new JPanel(new BorderLayout(5, 5));
        JPanel balancePanel = new JPanel(new GridBagLayout());
        checkBalancePanel.add(balancePanel, BorderLayout.CENTER);
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        JLabel holderName = new JLabel("Holder name:");
        holderName.setHorizontalAlignment(JLabel.CENTER);
        balancePanel.add(holderName, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.5;
        JTextField holderNameField = new JTextField(lastname + " " + firstname);
        holderNameField.setEditable(false);
        balancePanel.add(holderNameField, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        accNumLabel = new JLabel("Account number:");
        accNumLabel.setHorizontalAlignment(JLabel.CENTER);
        balancePanel.add(accNumLabel, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0.5;
        JTextField accNumField = new JTextField(accountNum);
        accNumField.setEditable(false);
        balancePanel.add(accNumField, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        JLabel accBalanceLabel = new JLabel("Balance:");
        accBalanceLabel.setHorizontalAlignment(JLabel.CENTER);
        balancePanel.add(accBalanceLabel, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 0.5;
        JTextField accBalanceField = new JTextField(amount);
        accBalanceField.setEditable(false);
        balancePanel.add(accBalanceField, c);

        goBackPanel = new JPanel();
        cancelButton = new JButton("Go back");
        cancelButton.setPreferredSize(new Dimension(80, 20));
        goBackPanel.add(cancelButton);
        checkBalancePanel.add(goBackPanel, BorderLayout.SOUTH);

        ATMFrame.add(checkBalancePanel);
        ATMFrame.repaint();
        ATMFrame.revalidate();
    }

    public void initShowTransactions(TransactionsTable transactionsData) {
        ATMFrame.remove(ATMPanel);

        showTransactionsPanel = new JPanel(new BorderLayout());
        JTable transactions = new JTable(transactionsData);
        transactions.setFillsViewportHeight(true);
        showTransactionsPanel.add(new JScrollPane(transactions), BorderLayout.CENTER);

        cancelButton = new JButton("Go back");
        goBackPanel = new JPanel();
        goBackPanel.add(cancelButton);
        cancelButton.setPreferredSize(new Dimension(80, 20));
        showTransactionsPanel.add(goBackPanel, BorderLayout.SOUTH);

        ATMFrame.add(showTransactionsPanel);
        ATMFrame.repaint();
        ATMFrame.revalidate();
    }

    public void addBack(JPanel unwanted, JPanel wanted) {
        ATMFrame.remove(unwanted);
        ATMFrame.add(wanted);
        ATMFrame.repaint();
        ATMFrame.revalidate();
    }

    public void showIncorrectLoginMsg(String msg) {
        JOptionPane.showMessageDialog(authFrame, msg);
    }

    public int showMsg(String msg, String type) {

        if(type.equals("Message")) {
            JOptionPane.showMessageDialog(ATMFrame, msg, "Attention!", ERROR_MESSAGE);
        } else if(type.equals("Confirm")) {
            return JOptionPane.showConfirmDialog(ATMFrame, msg, "Successful", YES_NO_OPTION);
        }
        return 0;
    }

    static class JTextFieldLimit extends PlainDocument {
        private final int limit;
        JTextFieldLimit(int limit) {
            super();
            this.limit = limit;
        }

        @Override
        public void insertString(int offset, String charNum, AttributeSet attributeSet) throws BadLocationException {
            if (charNum == null)
                return;
            if ((getLength() + charNum.length()) <= limit) {
                super.insertString(offset, charNum, attributeSet);
            }
        }
    }

    public JTextField getLoginField() {
        return this.loginField;
    }

    public JPasswordField getPasswordField() {
        return this.passwordField;
    }

    public JButton getEnterButton() {
        return this.enterButton;
    }

    public JButton getTransferMoneyButton() {
        return this.transferMoneyButton;
    }

    public JTextField getAccountNumField() {
        return accountNumField;
    }


    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JPanel getAccNumTransferPanel() {
        return accNumTransferPanel;
    }

    public JPanel getATMPanel() {
        return ATMPanel;
    }

    public JPanel getEnterAmountPanel() {
        return enterAmountPanel;
    }

    public JTextField getEnterAmountField() {
        return enterAmountField;
    }

    public JRadioButton getHUF() {
        return HUF;
    }

    public JRadioButton getUSD() {
        return USD;
    }

    public JButton getWithdrawMoneyButton() {
        return withdrawMoneyButton;
    }

    public JPanel getWithdrawPanel() {
        return withdrawPanel;
    }

    public ArrayList<JButton> getSelectAmountButtons() {
        return selectAmountButtons;
    }

    public JButton getCheckBalanceButton() {
        return checkBalanceButton;
    }

    public JPanel getCheckBalancePanel() {
        return checkBalancePanel;
    }

    public JButton getShowTransactionsButton() {
        return showTransactionsButton;
    }

    public JPanel getShowTransactionsPanel() {
        return showTransactionsPanel;
    }

    public JButton getDepositMoneyButton() {
        return depositMoneyButton;
    }

    public JPanel getDepositMoneyPanel() {
        return depositMoneyPanel;
    }

    public JFrame getATMFrame() {
        return ATMFrame;
    }
}