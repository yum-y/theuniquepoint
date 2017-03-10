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
 * The Mysql class implements 
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
    public Connection connect(String Host, String User, String Pass, String Database) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://"+Host+":3306/"+Database, User, Pass);  
        } catch(ClassNotFoundException | SQLException e) {
            exception_error(e);
        }
        return null;
    }
    public void execute(Connection link, String q) {
        try {
            Statement st = link.createStatement();
            st.executeUpdate(q);
        } catch(SQLException e) {
            exception_error(e);
        }
    }
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
    public int field_count(ResultSet result) {
        try {
            ResultSetMetaData resultmd = result.getMetaData();
            return resultmd.getColumnCount();
        } catch (SQLException e) {
            exception_error(e);
        }
        return 0;
    }
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
    public ResultSet query(Connection link, String q) {
        try {
            Statement st = link.createStatement();
            return st.executeQuery(q);
        } catch(SQLException e) {
            exception_error(e);
        }
        return null;
    }
    public void close(Connection link) {
        try {
            link.close();
        } catch (SQLException e) {
            exception_error(e);
        }
    }
}