import java.io.*;
import java.util.ArrayList;

public class Main {

    //Deserializing file which contains array list of banks
    public static ArrayList<Bank> deserializeBanks(String fileName) throws IOException {
        ArrayList<Bank> temp = new ArrayList<>();
        try
        {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);

            temp = (ArrayList<Bank>)ois.readObject();

            ois.close();
            fis.close();
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found");
            e.printStackTrace();
            return null;
        }
        return temp;
    }

    /*
    Accounts:
    "Jake", "Larons", 987654321, 4321 in OTP
    "Paul", "Atreides", 123456789, 1234 in OTP
    "Drake", "Joshensky", 852456739, 4567 in OTP

    "Michael", "Jackson", 137948625, 9988 in Optima
    "John", "Krasinsky", 112233445, 2233 in Optima
    "Loki", "Laufeyson", 666777888, 6969 in Optima
     */
    public static void main(String[] args) throws IOException {
        ArrayList<Bank> banks = deserializeBanks("HoldersData");
        BankView bankView = new BankView();
        bankView.initAuthFrame();
        BankController controller = new BankController(banks, bankView);
        controller.initController();
    }
}