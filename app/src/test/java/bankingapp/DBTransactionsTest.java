package bankingapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

public class DBTransactionsTest {

    private DBTransactions dbTransactions;

    // Set up the database for testing
    private void setup() throws SQLException {
        dbTransactions = new DBTransactions();
        dbTransactions.createDB();
    }

    // Close the database connection after testing
    private void tearDown() throws SQLException {
        if (dbTransactions != null) {
            dbTransactions.closeDB();
            dbTransactions = null;
        }
    }

    // Run before each test method
    @BeforeEach
    public void beforeTest() throws SQLException {
        setup();
    }

    // Run after each test method
    @AfterEach
    public void afterTest() throws SQLException {
        tearDown();
    }

    // Test inserting data into the database
    @Test
    public void testInsertDB() {
        dbTransactions.insertDB("John", "Smith", "2234567890000001", "1234");
        int balance = dbTransactions.checkBalance("2234567890000001");
        assertEquals(0, balance);
        // Clean up data after the test
        dbTransactions.deleteAcc("2234567890000001");
    }

    // Test adding income to an account
    @Test
    public void testAddIncome() {
        dbTransactions.insertDB("Alice", "Johnson", "8876543210000001", "5678");
        dbTransactions.addIncome(500, "8876543210000001");
        int balance = dbTransactions.checkBalance("8876543210000001");
        assertEquals(500, balance);
        // Clean up data after the test
        dbTransactions.deleteAcc("8876543210000001");
    }

    // Test deducting balance from an account
    @Test
    public void testDeductBalance() {
        dbTransactions.insertDB("Bob", "Brown", "5567891230000001", "9876");
        dbTransactions.addIncome(1000, "5567891230000001");
        dbTransactions.deductBalance(300, "5567891230000001");
        int balance = dbTransactions.checkBalance("5567891230000001");
        assertEquals(700, balance);
        // Clean up data after the test
        dbTransactions.deleteAcc("5567891230000001");
    }

    // Test checking an account with a pin
    @Test
    public void testCheckAcc() {
        dbTransactions.insertDB("Jane", "Doe", "0987654321", "4321");
        assertTrue(dbTransactions.checkAcc("0987654321", 4321));
    }

    // Test checking an account without a pin
    @Test
    public void testCheckAccWithoutPin() {
        dbTransactions.insertDB("Eve", "Johnson", "1357924680", "1357");
        assertTrue(dbTransactions.checkAcc_withoutPin("1357924680"));
    }

    // Test deleting an account
    @Test
    public void testDeleteAcc() {
        dbTransactions.insertDB("Sam", "Wilson", "2468013579", "9876");
        dbTransactions.deleteAcc("2468013579");
        assertFalse(dbTransactions.checkAcc_withoutPin("2468013579"));
    }

}
