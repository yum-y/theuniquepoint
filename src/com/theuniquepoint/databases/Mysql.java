/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theuniquepoint.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * <h1>Databases - Mysql</h1>
 * The Mysql class implements methods to manage databases more easily.
 *
 * @author Alex "yum-y" Torregrosa Romero
 * @version 1.00
 * @since 2017-03-01
 */
public class Mysql {

    private void exception_error(Exception e) {
        System.out.println(e);
        System.exit(0);
    }

    /**
     * Opens a connection to the MySQL Server running on.
     *
     * @param Host Can be either a host name or an IP address.
     * @param User The MySQL user name.
     * @param Pass If not provided or NULL, the MySQL server will attempt to
     * authenticate the user against those user records which have no password
     * only.
     * @param Database The default database to be used when performing queries.
     * @return Returns an object which represents the connection to a MySQL
     * Server.
     */
    public Connection connect(String Host, String User, String Pass, String Database) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://" + Host + ":3306/" + Database, User, Pass);
        } catch (ClassNotFoundException | SQLException e) {
            exception_error(e);
        }
        return null;
    }

    /**
     * Performs an execute query on the database.
     * 
     * @param link A link identifier returned by connect().
     * @param q The query string.
     */
    public void execute(Connection link, String q) {
        try {
            Statement st = link.createStatement();
            st.executeUpdate(q);
        } catch (SQLException e) {
            exception_error(e);
        }
    }

    /**
     * Fetch a result row as an associative map.
     * 
     * @param result A result set identifier returned by query().
     * @return Returns an associative map that corresponds to the fetched row or NULL if there are no more rows.
     */
    public Map<String, String> fetch_assoc(ResultSet result) {
        try {
            if (result.next()) {
                Map<String, String> values = new HashMap<>();
                ResultSetMetaData resultmd = result.getMetaData();
                for (int i = 0; i < field_count(result); i++) {
                    values.put(resultmd.getColumnName(i + 1), result.getString(i + 1));
                }
                return values;
            }
        } catch (SQLException e) {
            exception_error(e);
        }
        return null;
    }

    /**
     * Get a result row as an enumerated array.
     * 
     * @param result A result set identifier returned by query().
     * @return Returns an array of strings that corresponds to the fetched row or NULL if there are no more rows.
     */
    public String[] fetch_row(ResultSet result) {
        try {
            if (result.next()) {
                String values[] = new String[field_count(result)];
                for (int i = 0; i < field_count(result); i++) {
                    values[i] = result.getString(i + 1);
                }
                return values;
            }
        } catch (SQLException e) {
            exception_error(e);
        }
        return null;
    }

    /**
     * Returns the number of columns in statements result set.
     * 
     * @param result A result set identifier returned by query().
     * @return An integer representing the number of fields in a result set.
     */
    public int field_count(ResultSet result) {
        try {
            ResultSetMetaData resultmd = result.getMetaData();
            return resultmd.getColumnCount();
        } catch (SQLException e) {
            exception_error(e);
        }
        return 0;
    }

    /**
     * Return the number of rows in statements result set.
     * 
     * @param result A result set identifier returned by query().
     * @return An integer representing the number of rows in result set.
     */
    public int num_rows(ResultSet result) {
        int rows;
        try {
            result.last();
            rows = result.getRow();
            result.beforeFirst();
            return rows;
        } catch (SQLException e) {
            exception_error(e);
        }
        return 0;
    }

    /**
     * Performs a result query on the database.
     * 
     * @param link A link identifier returned by connect().
     * @param q The query string.
     * @return Returns NULL on failure. For successful SELECT, SHOW, DESCRIBE or EXPLAIN queries query() will return a ResultSet object.
     */
    public ResultSet query(Connection link, String q) {
        try {
            Statement st = link.createStatement();
            return st.executeQuery(q);
        } catch (SQLException e) {
            exception_error(e);
        }
        return null;
    }

    /**
     * Closes a previously opened database connection.
     * 
     * @param link A link identifier returned by connect().
     */
    public void close(Connection link) {
        try {
            link.close();
        } catch (SQLException e) {
            exception_error(e);
        }
    }
}
