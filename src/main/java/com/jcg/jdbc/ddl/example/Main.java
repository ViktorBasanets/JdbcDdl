package com.jcg.jdbc.ddl.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class Main {

    private static Connection connection;

    static {
        Locale locale = new Locale("en", "Us");
        ResourceBundle bundle = ResourceBundle.getBundle("jdbc", locale);
        try {
            Class.forName(bundle.getString("driver"));
            connection = DriverManager.getConnection(
                    bundle.getString("url"),
                    bundle.getString("username"),
                    bundle.getString("password"));
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
        throws SQLException {

        //createTables();
        fillTablesTestData();
    }

    public static void createTables()
        throws SQLException {

        Locale locale = new Locale("en", "Us");

        ResourceBundle bundle = ResourceBundle.getBundle("jdbc", locale);

        String query = bundle.getString("create.schema.companyA");
        connection.prepareStatement(query).executeUpdate();

        query = bundle.getString("create.schema.companyB");
        connection.prepareStatement(query).executeUpdate();

        ResourceBundle bundleComA = ResourceBundle.getBundle("sql/dbComA", locale);
        Set<String> queriesA = bundleComA.keySet();

        for (String key : queriesA) {
            String q = bundleComA.getString(key);
            connection.prepareStatement(q).executeUpdate();
        }

        ResourceBundle bundleComB = ResourceBundle.getBundle("sql/dbComB", locale);
        Set<String> queriesB = bundleComB.keySet();

        for (String key : queriesB) {
            String q = bundleComB.getString(key);
            connection.prepareStatement(q).executeUpdate();
        }
    }

    public static void fillTablesTestData() {

        Locale locale = new Locale("en", "Us");

        ResourceBundle bundleFirst = ResourceBundle.getBundle("raw/firstName", locale);
        ResourceBundle bundleLast = ResourceBundle.getBundle("raw/lastName", locale);

        Set<String> names = bundleFirst.keySet();
        Set<String> surnames = bundleLast.keySet();

        int counter = 0;

        for (String name : names) {
            for (String patronymic : names) {
                for (String surname : surnames) {

                    System.out.println(counter++ + "). "
                            + bundleFirst.getString(name) + " "
                            + bundleFirst.getString(patronymic) + " "
                            + bundleLast.getString(surname));
                }
            }
        }


    }
}
