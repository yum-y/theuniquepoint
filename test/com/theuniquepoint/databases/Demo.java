/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theuniquepoint.databases;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Map;

/**
 *
 * @author Alex "yum-y" Torregrosa Romero
 */
public class Demo {
    static Mysql mysql = new Mysql();
    
    public static void main(String[] args) {
        transactions();
    }
    
    static void createTableInserts() {
        Connection link = mysql.connect("sql11.freemysqlhosting.net", "sql11163128", "VJZCP1VGTl", "sql11163128");
        
        mysql.query(link, "CREATE TABLE Servers ( ID int NOT NULL AUTO_INCREMENT, Name varchar(255), WebService ENUM('Yes', 'No'), DNService ENUM('Yes', 'No'), FileService ENUM('Yes', 'No'), DBService ENUM('Yes', 'No'), MailService ENUM('Yes', 'No'), VServer ENUM('Yes', 'No'), PRIMARY KEY (ID) )");
        System.out.println(mysql.affectedRows(link));
        
        mysql.query(link, "INSERT INTO Servers(Name, WebService, DNService, FileService, DBService, MailService, VServer) VALUES ('ws01.theuniquepoint.com', 'Yes', 'No', 'Yes', 'No', 'No', 'No')");
        mysql.query(link, "INSERT INTO Servers(Name, WebService, DNService, FileService, DBService, MailService, VServer) VALUES ('ns01.theuniquepoint.com', 'No', 'Yes', 'No', 'No', 'No', 'No')");
        mysql.query(link, "INSERT INTO Servers(Name, WebService, DNService, FileService, DBService, MailService, VServer) VALUES ('ns02.theuniquepoint.com', 'No', 'Yes', 'No', 'No', 'No', 'No')");
        mysql.query(link, "INSERT INTO Servers(Name, WebService, DNService, FileService, DBService, MailService, VServer) VALUES ('mysql01.theuniquepoint.com', 'No', 'No', 'No', 'Yes', 'No', 'No')");
        mysql.query(link, "INSERT INTO Servers(Name, WebService, DNService, FileService, DBService, MailService, VServer) VALUES ('mysql02.theuniquepoint.com', 'No', 'No', 'No', 'Yes', 'No', 'No')");
        mysql.query(link, "INSERT INTO Servers(Name, WebService, DNService, FileService, DBService, MailService, VServer) VALUES ('mail01.theuniquepoint.com', 'No', 'No', 'No', 'No', 'Yes', 'No')");
        mysql.query(link, "INSERT INTO Servers(Name, WebService, DNService, FileService, DBService, MailService, VServer) VALUES ('vm01.theuniquepoint.com', 'No', 'No', 'No', 'No', 'No', 'Yes')");
        mysql.query(link, "INSERT INTO Servers(Name, WebService, DNService, FileService, DBService, MailService, VServer) VALUES ('vm02.theuniquepoint.com', 'No', 'No', 'No', 'No', 'No', 'Yes')");
        
        mysql.close(link);
    }
    
    static void printRow() {
        String row[];
        
        Connection link = mysql.connect("sql11.freemysqlhosting.net", "sql11163128", "VJZCP1VGTl", "sql11163128");
        
        ResultSet result = mysql.query(link, "SELECT * FROM Servers WHERE DNService = 'Yes'");
        System.out.println("Retrieving data form database, number of rows: " + mysql.numRows(result) + "\n");
        
        System.out.println("ID, Name, WebService, DNService, FileService, DBService, MailService, VServer");
        while ((row = mysql.fetchRow(result)) != null) {
            for (int i = 0; i < mysql.fieldCount(result); i++) {
                System.out.print(row[i] + " ");
            }
            System.out.println("");
        }
        mysql.close(link);
    }
    
    static void printAssoc() {
        Map<String, String> row;
        
        Connection link = mysql.connect("sql11.freemysqlhosting.net", "sql11163128", "VJZCP1VGTl", "sql11163128");
        
        ResultSet result = mysql.query(link, "SELECT * FROM Servers");
        System.out.println("Retrieving data form database, number of rows: " + mysql.numRows(result) + "\n");
        
        System.out.println("ID, Name, WebService, DNService, FileService, DBService, MailService, VServer");
        while ((row = mysql.fetchAssoc(result)) != null) {
            System.out.println(row.get("ID") + " " + row.get("Name") + " " + row.get("WebService") + " " + row.get("DNService") + " " + row.get("FileService") + " " + row.get("DBService") + " " + row.get("MailService") + " " + row.get("VServer"));
        }
        mysql.close(link);
    }
    
    static void printInfo() {
        Connection link = mysql.connect("sql11.freemysqlhosting.net", "sql11163128", "VJZCP1VGTl", "sql11163128");
        
        System.out.println("Retrieving info from server");
        System.out.println(mysql.getHostInfo(link));
        System.out.println(mysql.getServerInfo(link));
    }
    
    static void firstdemo() {
        System.out.println("==================");
        printRow();
        System.out.println("==================\n\n");
        System.out.println("==================");
        printAssoc();
        System.out.println("==================\n\n");
        System.out.println("==================");
        printInfo();
        System.out.println("==================\n\n");
    }
    
    static void transactions() {
        ResultSet result;
        String row[];
        
        Connection link = mysql.connect("sql11.freemysqlhosting.net", "sql11163128", "VJZCP1VGTl", "sql11163128");
        
        System.out.println("==================");
        System.out.println("Mysql auto-commit by default is: " + mysql.getAutocommit(link));
        mysql.autocommit(link, false);
        System.out.println("Channing auto-commit to false: " + mysql.getAutocommit(link));
        mysql.autocommit(link, true);
        System.out.println("Reverting to true: " + mysql.getAutocommit(link));
        System.out.println("==================\n\n");
        
        System.out.println("==================");
        mysql.beginTransaction(link);
        System.out.println("Beggining a transaction, checking auto-commit: " + mysql.getAutocommit(link));
        result = mysql.query(link, "INSERT INTO Servers(Name, WebService, DNService, FileService, DBService, MailService, VServer) VALUES ('ws02.theuniquepoint.com', 'Yes', 'No', 'Yes', 'No', 'No', 'No')");
        System.out.println("Now query() detects DML and non-DML queries, this one doesn't return nothing. And this is the affected rows: " + mysql.affectedRows(link));
        result = mysql.query(link, "SELECT * FROM Servers WHERE WebService = 'Yes'");
        System.out.println("We make sure that is inserted, number of rows: " + mysql.numRows(result));
        while ((row = mysql.fetchRow(result)) != null) {
            for (int i = 0; i < mysql.fieldCount(result); i++) {
                System.out.print(row[i] + " ");
            }
            System.out.println("");
        }
        System.out.println("We dont want this, so we rollback: " + mysql.rollback(link) + " // True for success.");
        System.out.println("We check the transaction did not made any changes:");
        result = mysql.query(link, "SELECT * FROM Servers WHERE WebService = 'Yes'");
        System.out.println("We make sure that number of rows is one less: " + mysql.numRows(result));
        while ((row = mysql.fetchRow(result)) != null) {
            for (int i = 0; i < mysql.fieldCount(result); i++) {
                System.out.print(row[i] + " ");
            }
            System.out.println("");
        }
        System.out.println("==================\n\n");
        
        mysql.close(link);
    }
}
