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
        printRow();
    }
    static void exampleDatabase() {
        Connection link = mysql.connect("sql11.freemysqlhosting.net", "sql11163128", "VJZCP1VGTl", "sql11163128");
        
        mysql.execute(link, "CREATE TABLE Servers ( ID int NOT NULL AUTO_INCREMENT, Name varchar(255), WebService ENUM('Yes', 'No'), DNService ENUM('Yes', 'No'), FileService ENUM('Yes', 'No'), DBService ENUM('Yes', 'No'), MailService ENUM('Yes', 'No'), VServer ENUM('Yes', 'No'), PRIMARY KEY (ID) )");
        mysql.execute(link, "INSERT INTO Servers(Name, WebService, DNService, FileService, DBService, MailService, VServer) VALUES ('ws01.theuniquepoint.com', 'Yes', 'No', 'Yes', 'No', 'No', 'No')");
        mysql.execute(link, "INSERT INTO Servers(Name, WebService, DNService, FileService, DBService, MailService, VServer) VALUES ('ns01.theuniquepoint.com', 'No', 'Yes', 'No', 'No', 'No', 'No')");
        mysql.execute(link, "INSERT INTO Servers(Name, WebService, DNService, FileService, DBService, MailService, VServer) VALUES ('ns02.theuniquepoint.com', 'No', 'Yes', 'No', 'No', 'No', 'No')");
        mysql.execute(link, "INSERT INTO Servers(Name, WebService, DNService, FileService, DBService, MailService, VServer) VALUES ('mysql01.theuniquepoint.com', 'No', 'No', 'No', 'Yes', 'No', 'No')");
        mysql.execute(link, "INSERT INTO Servers(Name, WebService, DNService, FileService, DBService, MailService, VServer) VALUES ('mysql02.theuniquepoint.com', 'No', 'No', 'No', 'Yes', 'No', 'No')");
        mysql.execute(link, "INSERT INTO Servers(Name, WebService, DNService, FileService, DBService, MailService, VServer) VALUES ('mail01.theuniquepoint.com', 'No', 'No', 'No', 'No', 'Yes', 'No')");
        mysql.execute(link, "INSERT INTO Servers(Name, WebService, DNService, FileService, DBService, MailService, VServer) VALUES ('vm01.theuniquepoint.com', 'No', 'No', 'No', 'No', 'No', 'Yes')");
        mysql.execute(link, "INSERT INTO Servers(Name, WebService, DNService, FileService, DBService, MailService, VServer) VALUES ('vm02.theuniquepoint.com', 'No', 'No', 'No', 'No', 'No', 'Yes')");
        
        mysql.close(link);
    }
    static void printRow() {
        String row[];
        
        Connection link = mysql.connect("sql11.freemysqlhosting.net", "sql11163128", "VJZCP1VGTl", "sql11163128");
        
        ResultSet result = mysql.query(link, "SELECT * FROM Servers WHERE DNService = 'Yes'");
        System.out.println("Retrieving data form database, number of rows: " + mysql.num_rows(result) + "\n");
        
        System.out.println("ID, Name, WebService, DNService, FileService, DBService, MailService, VServer");
        while ((row = mysql.fetch_row(result)) != null) {
            for (int i = 0; i < mysql.field_count(result); i++) {
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
        System.out.println("Retrieving data form database, number of rows: " + mysql.num_rows(result) + "\n");
        
        System.out.println("ID, Name, WebService, DNService, FileService, DBService, MailService, VServer");
        while ((row = mysql.fetch_assoc(result)) != null) {
            System.out.println(row.get("ID") + " " + row.get("Name") + " " + row.get("WebService") + " " + row.get("DNService") + " " + row.get("FileService") + " " + row.get("DBService") + " " + row.get("MailService") + " " + row.get("VServer"));
        }
        
        mysql.close(link);
    }
}
