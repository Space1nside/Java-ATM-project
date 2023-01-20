import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 6529685098267757690L;
    private final String firstName;
    private final String lastName;
    private final ArrayList<Account> accounts;

    User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accounts = new ArrayList<>();
    }


    public void createAccount(Account newAccount) {
        if(!accounts.contains(newAccount)) {
            for (int i = 0; i < accounts.size(); i++) {
                if(!accounts.get(i).getBank().getName().equals(newAccount.getBank().getName())) {
                    this.accounts.add(newAccount);
                }
            }
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
