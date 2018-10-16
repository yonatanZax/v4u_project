package db;

import java.sql.*;

public class DatabaseManager {

    public static final String DATABASE = "v4u_db";
    public static final String PATH_DB = System.getProperty("user.dir") + "/db";
    public static final String TABLE_USER = "userInfo";

    /**
     * TODO - add if exists for the db
     * Connect to a sample database
     * currently works only with 'DatabaseManager.DATABASE'
     */
    public static void createNewDatabase() {

        String url = PATH_DB + DATABASE;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create a new table in the test database
     * currently works only with 'DatabaseManager.TABLE_USER'
     */
    public static void createNewTable(String tableName) {
        // SQLite connection string
        String url = PATH_DB + tableName;

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n"
                + "	userName integer PRIMARY KEY,\n"
                + "	password text NOT NULL,\n"
                + "	firstName text NOT NULL,\n"
                + "	lastName text NOT NULL,\n"
                + "	city text NOT NULL,\n"
                + "	birthday date NOT NULL\n" /*CHECK DATE PARAMETER*/
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Connect to the test.db database
     *
     * @return the Connection object
     */
    private static Connection connect() {
        // SQLite connection string
        String url = PATH_DB + TABLE_USER;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    /**
     * Insert a new row into the warehouses table
     * TODO - fix thi fucking func
     */
    public void insert(String name, double capacity) {
        String sql = "INSERT INTO warehouses(name,capacity) VALUES(?,?)";
        Connection conn = this.connect();
        if (conn != null) {
            try {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, name);
                pstmt.setDouble(2, capacity);
                pstmt.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            } finally {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

    }

/*    *//**
     * select all rows in the warehouses table
     *//*
    public void selectAll(){
        String sql = "SELECT id, name, capacity FROM warehouses";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("name") + "\t" +
                        rs.getDouble("capacity"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }*/


    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
    }
}
