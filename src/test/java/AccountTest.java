import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    private Account account1;
    private Account account2;
    private Bank bank;

    @BeforeEach
    public void init() {
        bank = new Bank("OTP");
        account1 = bank.addHolder("Judy", "Alvarez",789456123, 1000, 1234);
        account2 = bank.addHolder("Joe", "K",456789123, 2000, 4321);
        bank.clearBranch();
    }

    //Should return true if the transferring amount in HUF is less than account balance
    @Test
    void transferLessHUF() {
        boolean wasSent = account1.transferMoney(500, account2, "HUF");
        assertTrue(wasSent, "Transferring amount in HUF is less than account balance, but it returned false");
    }

    //Should return false if the transferring amount in HUF is greater than account balance
    @Test
    void transferMoreHUF() {
        boolean wasSent = account1.transferMoney(1500, account2, "HUF");
        assertFalse(wasSent, "Transferring amount in HUF is greater than account balance, but it returned true");
    }

    //Should return true if the transferring amount in USD is less than account balance
    @Test
    void transferLessUSD() {
        boolean wasSent = account1.transferMoney(1, account2, "USD");
        assertTrue(wasSent, "Transferring amount in USD is less than account balance, but it returned false");
    }

    //Should return false if the transferring amount in USD is greater than account balance
    @Test
    void transferMoreUSD() {
        boolean wasSent = account1.transferMoney(5, account2, "USD");
        assertFalse(wasSent, "Transferring amount in USD is greater than account balance, but it returned true");
    }

    //Should return true if amount of withdrawing in HUF is less than account balance
    @Test
    void cashWithdrawalLessHUF() {
        boolean wasWithdrawn = account1.cashWithdrawal(100, "HUF");
        assertTrue(wasWithdrawn, "The withdrawal amount in HUF was less then account balance, but it returned false");
    }

    //Should return false if amount of withdrawing in HUF is greater than account balance
    @Test
    void cashWithdrawalMoreHUF() {
        boolean wasWithdrawn = account1.cashWithdrawal(1500, "HUF");
        assertFalse(wasWithdrawn, "The withdrawal amount in HUF was greater then account balance, but it returned true");
    }

    //Should return true if amount of withdrawing in USD is less than account balance
    @Test
    void cashWithdrawalLessUSD() {
        boolean wasWithdrawn = account1.cashWithdrawal(1, "USD");
        assertTrue(wasWithdrawn, "The withdrawal amount in USD was less then account balance, but it returned false");
    }

    //Should return false if amount of withdrawing in USD is greater than account balance
    @Test
    void cashWithdrawalMoreUSD() {
        boolean wasWithdrawn = account1.cashWithdrawal(5, "USD");
        assertFalse(wasWithdrawn, "The withdrawal amount in USD was greater then account balance, but it returned true");
    }

    //should return true if the entering pin is correct
    @Test
    void validateCorrect() {
        boolean correctPin = account1.validate(1234);
        assertTrue(correctPin, "Pin was correct, but it returned false");
    }

    //should return false if the entering pin is incorrect
    @Test
    void validateIncorrect() {
        boolean incorrectPin = account2.validate(6666);
        assertFalse(incorrectPin, "Pin was incorrect, but it returned true");
    }
}