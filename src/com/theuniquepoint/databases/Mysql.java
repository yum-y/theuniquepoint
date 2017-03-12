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
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * <h1>Databases - Mysql</h1>
 * The Mysql class implements methods to manage databases more easily.
 *
 * @author Alex "yum-y" Torregrosa Romero
 * @version 1.03
 * @since 2017-03-01
 */
public class Mysql {

    private int AffectedRows = -1;

    private void exception(Exception e) {
        System.out.println(e);
        System.exit(0);
    }

    /**
     * Gets the number of affected rows in a previous MySQL operation.
     *
     * @param link A link identifier returned by connect().
     * @return An integer greater than zero indicates the number of rows
     * affected or retrieved. Zero indicates that no records were updated for an
     * UPDATE statement, no rows matched the WHERE clause in the query or that
     * no query has yet been executed. -1 indicates that the query returned a
     * ResultSet or an error.
     */
    public int affectedRows(Connection link) {
        return AffectedRows;
    }

    /**
     * Turns on or off auto-committing database modifications.
     *
     * @param link A link identifier returned by connect().
     * @param mode Whether to turn on auto-commit or not.
     * @return Returns TRUE on success or FALSE on failure.
     */
    public boolean autocommit(Connection link, boolean mode) {
        try {
            link.setAutoCommit(mode);
            return true;
        } catch (SQLException e) {
            exception(e);
        }
        return false;
    }

    /**
     * Starts a transaction.
     *
     * @param link A link identifier returned by connect().
     * @return Returns TRUE on success or FALSE on failure.
     */
    public boolean beginTransaction(Connection link) {
        try {
            link.setAutoCommit(false);
            return true;
        } catch (SQLException e) {
            exception(e);
        }
        return false;
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
            exception(e);
        }
    }

    /**
     * Commits the current transaction
     *
     * @param link A link identifier returned by connect().
     * @return Returns TRUE on success or FALSE on failure.
     */
    public boolean commit(Connection link) {
        try {
            link.commit();
            link.setAutoCommit(true);
        } catch (SQLException e) {
            exception(e);
        }
        return false;
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
            exception(e);
        }
        return null;
    }

    /**
     * Frees the memory associated with a result
     *
     * @param result A result set identifier returned by query().
     */
    public void freeResult(ResultSet result) {
        try {
            result.close();
        } catch (SQLException e) {
            exception(e);
        }
    }

    /**
     * Returns the status of Autocommit.
     *
     * @param link
     * @return Returns TRUE if Autocommit is enabled or FALSE if disabled.
     */
    public boolean getAutocommit(Connection link) {
        try {
            return link.getAutoCommit();
        } catch (SQLException e) {
            exception(e);
        }
        return false;
    }

    /**
     * Returns a string representing the type of connection used.
     *
     * @param link A link identifier returned by connect().
     * @return A character string representing the server hostname and the
     * connection type.
     */
    public String getHostInfo(Connection link) {
        try {
            return link.getMetaData().getURL();
        } catch (SQLException e) {
            exception(e);
        }
        return null;
    }

    /**
     * Returns the version of the MySQL server.
     *
     * @param link A link identifier returned by connect().
     * @return A character string representing the server version.
     */
    public String getServerInfo(Connection link) {
        try {
            return link.getMetaData().getDatabaseProductVersion();
        } catch (SQLException e) {
            exception(e);
        }
        return null;
    }

    /**
     * Fetch a result row as an associative map.
     *
     * @param result A result set identifier returned by query().
     * @return Returns an associative map that corresponds to the fetched row or
     * NULL if there are no more rows.
     */
    public Map<String, String> fetchAssoc(ResultSet result) {
        try {
            if (result.next()) {
                Map<String, String> values = new HashMap<>();
                ResultSetMetaData resultmd = result.getMetaData();
                for (int i = 0; i < fieldCount(result); i++) {
                    values.put(resultmd.getColumnName(i + 1), result.getString(i + 1));
                }
                return values;
            }
        } catch (SQLException e) {
            exception(e);
        }
        return null;
    }

    /**
     * Get a result row as an enumerated array.
     *
     * @param result A result set identifier returned by query().
     * @return Returns an array of strings that corresponds to the fetched row
     * or NULL if there are no more rows.
     */
    public String[] fetchRow(ResultSet result) {
        try {
            if (result.next()) {
                String values[] = new String[fieldCount(result)];
                for (int i = 0; i < fieldCount(result); i++) {
                    values[i] = result.getString(i + 1);
                }
                return values;
            }
        } catch (SQLException e) {
            exception(e);
        }
        return null;
    }

    /**
     * Returns the number of columns in statements result set.
     *
     * @param result A result set identifier returned by query().
     * @return An integer representing the number of fields in a result set.
     */
    public int fieldCount(ResultSet result) {
        try {
            ResultSetMetaData resultmd = result.getMetaData();
            return resultmd.getColumnCount();
        } catch (SQLException e) {
            exception(e);
        }
        return 0;
    }

    /**
     * Return the number of rows in statements result set.
     *
     * @param result A result set identifier returned by query().
     * @return An integer representing the number of rows in result set.
     */
    public int numRows(ResultSet result) {
        int rows;
        try {
            result.last();
            rows = result.getRow();
            result.beforeFirst();
            return rows;
        } catch (SQLException e) {
            exception(e);
        }
        return 0;
    }

    /**
     * Performs a query on the database.
     *
     * @param link A link identifier returned by connect().
     * @param q The query string.
     * @return For successful SELECT, SHOW, DESCRIBE or EXPLAIN queries query()
     * will return a ResultSet object. Returns NULL for DML queries like INSERT,
     * UPDATE or DELETE or on failure.
     */
    public ResultSet query(Connection link, String q) {
        try {
            Statement st = link.createStatement();
            if (st.execute(q)) {
                AffectedRows = -1;
                return st.getResultSet();
            } else {
                AffectedRows = st.getUpdateCount();
            }
        } catch (SQLException e) {
            exception(e);
        }
        return null;
    }

    /**
     * Performs an execute query on the database.
     *
     * @param link A link identifier returned by connect().
     * @param q The query string.
     * @deprecated Use query() instead.
     */
    @Deprecated
    public void queryExecute(Connection link, String q) {
        try {
            Statement st = link.createStatement();
            st.executeUpdate(q);
        } catch (SQLException e) {
            exception(e);
        }
    }

    /**
     * Performs a result query on the database.
     *
     * @param link A link identifier returned by connect().
     * @param q The query string.
     * @return Returns NULL on failure. For successful SELECT, SHOW, DESCRIBE or
     * EXPLAIN queries queryRead() will return a ResultSet object.
     * @deprecated Use query() instead.
     */
    @Deprecated
    public ResultSet queryRead(Connection link, String q) {
        try {
            Statement st = link.createStatement();
            return st.executeQuery(q);
        } catch (SQLException e) {
            exception(e);
        }
        return null;
    }

    /**
     * Rolls back current transaction.
     *
     * @param link A link identifier returned by connect().
     * @return Returns TRUE on success or FALSE on failure.
     */
    public boolean rollback(Connection link) {
        try {
            link.rollback();
            link.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            exception(e);
        }
        return false;
    }

    /**
     * Roll back the current transaction to a saved point.
     *
     * @param link A link identifier returned by connect().
     * @param save A save point object.
     * @return Returns TRUE on success or FALSE on failure.
     */
    public boolean rollback(Connection link, Savepoint save) {
        try {
            link.rollback(save);
            return true;
        } catch (SQLException e) {
            exception(e);
        }
        return false;
    }

    /**
     * Set a named transaction savepoint
     *
     * @param link A link identifier returned by connect().
     * @return The save point object on success or NULL on failure.
     */
    public Savepoint savepoint(Connection link) {
        try {
            return link.setSavepoint();
        } catch (SQLException e) {
            exception(e);
        }
        return null;
    }
}
