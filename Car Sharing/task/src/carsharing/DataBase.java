package carsharing;

import carsharing.entity.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataBase  {
    Statement stmt = null;
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:./src/carsharing/db/carsharing";

    public DataBase () throws ClassNotFoundException, SQLException {
        createCompanyTable();
        createCarTable();
        createCustomerTable();
    }

    public void cleanResourses (Connection conn, Statement stmt) throws java.sql.SQLException {

            if(stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
            if(stmt != null && !conn.isClosed()) {
                conn.createStatement().execute("SHUTDOWN");
                conn.close();
            }
    }
    public void createCompanyTable () throws SQLException, ClassNotFoundException {
        if (checkTableExist("Company"))
            return ;
        Connection connection = getConnection();
        System.out.println("Creating table in given database...");
        stmt = connection.createStatement();
        String sql =  "CREATE TABLE COMPANY"  +
                "(id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                " NAME VARCHAR(255) NOT NULL UNIQUE)";
        stmt.executeUpdate(sql);
        cleanResourses(connection, stmt);
        System.out.println("Created table in given database...");
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, "", "");
            conn.setAutoCommit(true);
        } catch (ClassNotFoundException e) {
            System.out.println("Error in driver founding");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void createCustomerTable () throws SQLException, ClassNotFoundException {
        if (checkTableExist("CUSTOMER"))
            return ;
        Connection conn = getConnection();
        System.out.println("Creating table in given database...");
        stmt = conn.createStatement();
        String sql =  "CREATE TABLE CUSTOMER " +
                "(id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                " NAME VARCHAR(255) NOT NULL UNIQUE, " +
                "RENTED_CAR_ID INTEGER DEFAULT NULL, " +
                " CONSTRAINT FK_CUSTOMER FOREIGN KEY (RENTED_CAR_ID)" +
                " REFERENCES CAR(ID))";
        stmt.executeUpdate(sql);
        cleanResourses(conn, stmt);
        System.out.println("Created table in given database...");
    }
    public void createCarTable () throws SQLException, ClassNotFoundException {
        if (checkTableExist("car"))
            return ;
        Connection conn = getConnection();
        System.out.println("Creating table in given database...");
        stmt = conn.createStatement();
        String sql =  "CREATE TABLE  CAR"  +
                "(id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                " NAME VARCHAR(255) NOT NULL UNIQUE, " +
                "IS_RENTED INT DEFAULT 0, " +
                "COMPANY_ID INTEGER NOT NULL, " +
                " CONSTRAINT FK_CAR FOREIGN KEY (COMPANY_ID)" +
                "    REFERENCES COMPANY(ID))";
        stmt.executeUpdate(sql);
        cleanResourses(conn, stmt);
        System.out.println("Created table in given database...");
    }

    private boolean checkTableExist(String tableName) throws SQLException, ClassNotFoundException {
        Connection conn = getConnection();
        stmt = conn.createStatement();
        String sql = "SHOW tables";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            if (Objects.equals(rs.getString(1), tableName.toUpperCase())) {
                rs.close();
                return (true);
            }
        }
        rs.close();
        cleanResourses(conn, stmt);
        return (false);
    }
}
