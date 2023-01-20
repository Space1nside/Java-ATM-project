import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {
    private Bank bank;

    @BeforeEach
    public void init() {
        bank = new Bank("OTP");
        bank.addHolder("Jason", "Brody", 123456789, 6000, 2255);
        bank.clearBranch();
    }

    //Should return null if holder with similar account number already exists
    @Test
    void addSimilarHolder() {
        Account newAcc = bank.addHolder("Mike", "Wazovsky", 123456789, 9900, 3456);
        assertNull(newAcc, "Such account already exists, but it was added anyway");
    }

    //Should return created account if account with such account number doesn't exist
    @Test
    void addNewHolder() {
        Account newAcc = bank.addHolder("Viktor", "Vektor", 987654321, 9900, 3456);
        assertNotNull(newAcc, "Such account doesn't exist, but it was not added");
    }

    //Should return added account if account with such credentials exists
    @Test
    void checkCorrectLogin() {
        Account checkLogin = bank.checkLogin(123456789, 2255);
        assertNotNull(checkLogin, "The login was correct, but method returned null");
    }

    //Should return null if account with such credentials doesn't exist
    @Test
    void checkIncorrectLogin() {
        Account checkLogin = bank.checkLogin(112233445, 3658);
        assertNull(checkLogin, "Login wasn't correct, but method returned account");
    }

    //Should return null if pin isn't correct
    @Test
    void checkIncorrectPin() {
        Account checkLogin = bank.checkLogin(123456789, 6547);
        assertNull(checkLogin, "Account number was correct but pin wasn't, but method returned account");
    }

    //Should return null if account number isn't correct
    @Test
    void checkIncorrectAccNum() {
        Account checkLogin = bank.checkLogin(123456789, 6547);
        assertNull(checkLogin, "Pin was correct but account number wasn't, but method returned account");
    }

    //Should return account, if such account number exists
    @Test
    void hasAccount() {
        Account account = bank.hasAccount(123456789);
        assertNotNull(account, "Such account number exists, but it was not found");
    }

    //Should return null, if such account number doesn't exist
    @Test
    void noHasAccount() {
        Account account = bank.hasAccount(987654321);
        assertNull(account, "Such account number doesn't exist, but it was found");
    }
}