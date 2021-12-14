import java.io.IOException;
import java.sql.*;

public class JDBC {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {

        Class.forName("org.postgresql.Driver");
        Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "");

        //atomicity
        c.setAutoCommit(false);
        //isolation
        c.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        Statement stmt = null;
        try {
            //statement object
            stmt = c.createStatement();

            //sql command
            String sql = "DELETE FROM Product WHERE prod_id='p1'";
            //atomicity
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.err.println("An exception was thrown: " + e.getClass().getName()+": "+e.getMessage());
            //atomicity
            c.rollback();
            stmt.close();
            c.close();
            return;
        }
        c.commit();
        stmt.close();
        c.close();
    }
}
