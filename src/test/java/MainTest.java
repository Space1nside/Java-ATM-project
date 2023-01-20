import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    static String correctFilename;
    static String incorrectFilename;

    @BeforeAll
    public static void initFiles() {
        correctFilename = "HoldersData";
        incorrectFilename = "QWERTY";
    }

    //Should return deserialized ArrayList of banks if the file with such name exists
    @Test
    void deserializeBanks() throws IOException {
        assertNotNull(Main.deserializeBanks(correctFilename));
    }

    //Should throw an exception if file with such name does not exist
    @Test
    void deserializeIncorrectBanks() {
        assertThrows(IOException.class, ()->{Main.deserializeBanks(incorrectFilename);});
    }
}