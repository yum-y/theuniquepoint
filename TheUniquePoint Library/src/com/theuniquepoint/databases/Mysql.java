/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theuniquepoint.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author mati
 */
public class Mysql {
    private Exception errno, connect_errno;
    private String error, connect_error;
    
    public Connection connect(String Host, String User, String Pass, String Database) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://"+Host+":3306/"+Database, User, Pass);  
        } catch(ClassNotFoundException | SQLException e) {
            this.connect_errno = e;
            this.connect_error = "Cannot connect to the server.";
        }
        return null;
    }
    public Exception connect_errno() {
        return connect_errno;
    }
    public String connect_error() {
        return connect_error;
    }
    public Exception errno() {
        return errno;
    }
    public String error() {
        return error;
    }
    public ResultSet query(Connection link, String q) {
        try {
            Statement st = link.createStatement();
            return st.executeQuery(q);
        } catch(SQLException e) {
            this.errno = e;
            this.error = "Cannot execute the query.";
        }
        return null;
    }
}