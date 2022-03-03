import java.sql.*;

public class DataBase {

    private static int rowCount;

    public static void createNewDatabase(String fileName) {


        String dbURL = "jdbc:sqlite:" + fileName;

        try (Connection conn = DriverManager.getConnection(dbURL)) {
            if (conn == null) { // if no database with such name created - creating a new one
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createNewTable(String fileName, String tableName) {

        String dbURL = "jdbc:sqlite:" + fileName;

        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n"
                + "	id INTEGER,\n"
                + "	number TEXT NOT NULL,\n"
                + "	pin TEXT,\n"
                + " balance INTEGER DEFAULT 0"
                + ");";

        String countNumberOfRows = "SELECT COUNT(1) FROM " + tableName + ";";


        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql); // create a new table executing query

            ResultSet rs = stmt.executeQuery(countNumberOfRows); // count number of rows
            rowCount = (int) rs.getInt(1); // if we do have rows in result set - we return number of rows, otherwise - we return 0;
            System.out.println("Number of rows: " + rowCount);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static boolean checkAccount(String fileName, String tableName, String cardNumber) {
        String dbURL = "jdbc:sqlite:" + fileName;

        boolean checkAccountExistion = false;

        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement statement = conn.createStatement()) {

            String query = "SELECT * FROM " + tableName + " WHERE number = " + cardNumber;

            ResultSet rs = statement.executeQuery(query); // Statement execution
            checkAccountExistion = rs.next(); // checking if entry exist
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return checkAccountExistion;
    }

    public static void insertAccount(String fileName, String tableName, String cardNumber, String pin) {
        String dbURL = "jdbc:sqlite:" + fileName;


        rowCount++;
        String query = "INSERT INTO " + tableName + "(id, number, pin) VALUES(?,?,?)";

        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, rowCount);
            pstmt.setString(2, cardNumber);
            pstmt.setString(3, pin);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkAccount(String fileName, String tableName, String cardNumber, String pin) {
        String dbURL = "jdbc:sqlite:" + fileName;

        boolean checkAccount = false;

        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement stmt = conn.createStatement()) {

            String query = "SELECT * FROM " + tableName + " WHERE number = " + cardNumber + " AND pin = " + pin + ";";
            ResultSet rs = stmt.executeQuery(query);
            checkAccount = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return checkAccount;
    }

    public static int getBalance(String fileName, String tableName, String cardNumber, String pin) {
        String dbURL = "jdbc:sqlite:" + fileName;

        int result = -1;

        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement stmt = conn.createStatement()) {

            String query = "SELECT balance FROM " + tableName + " WHERE number = " + cardNumber + " AND pin = " + pin + ";";
            ResultSet rs = stmt.executeQuery(query);
            result = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void addIncome(String fileName, String tableName, String cardNumber, int income) {
        String dbURL = "jdbc:sqlite:" + fileName;

        String addIncome = "UPDATE "
                + tableName +
                " SET balance = balance + ? WHERE number = ?;";

        try (Connection conn = DriverManager.getConnection(dbURL)) {

            // Disable auto-commit mode
            conn.setAutoCommit(false);

            try (
                    PreparedStatement balanceUpdate = conn.prepareStatement(addIncome)) {

                // update balance with income

                balanceUpdate.setInt(1, income);
                balanceUpdate.setString(2, cardNumber);
                balanceUpdate.executeUpdate();

                conn.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void deleteAccount(String fileName, String tableName, String cardNumber) {
        String dbURL = "jdbc:sqlite:" + fileName;

        String deleteAccount = "DELETE FROM " + tableName + " WHERE number = " + cardNumber;

        try (Connection conn = DriverManager.getConnection(dbURL); Statement statement = conn.createStatement()) {
            statement.executeUpdate(deleteAccount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
