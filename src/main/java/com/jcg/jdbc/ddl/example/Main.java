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

    public static void fillTablesTestData() throws SQLException {

        Locale locale = new Locale("en", "Us");

        ResourceBundle bundlePatterns = ResourceBundle.getBundle("patterns", locale);
        ResourceBundle bundleComA = ResourceBundle.getBundle("sql/initComA", locale);
        ResourceBundle bundleComB = ResourceBundle.getBundle("sql/initComB", locale);
        String query, pattern;
        Random age = new Random();
        PreparedStatement statement;
        int MAX = 500;

        for (int i = 0; i < MAX; i++) {
            query = bundleComA.getString("insert.into.staff");
            statement = connection.prepareStatement(query);
            pattern = bundlePatterns.getString("firstName") + i + " "
                    + bundlePatterns.getString("familyName") + i + " "
                    + bundlePatterns.getString("lastName") + i;
            statement.setString(1, pattern);
            statement.setInt(2, age.nextInt(20) + 30);
            statement.executeUpdate();
            statement.close();

            query = bundleComA.getString("insert.into.departments");
            statement = connection.prepareStatement(query);
            pattern = bundlePatterns.getString("departmentName") + i;
            statement.setString(1, pattern);
            pattern = bundlePatterns.getString("districtName") + i;
            statement.setString(2, pattern);
            statement.executeUpdate();
            statement.close();

            
        }





/*

        head:
        for (String name : names) {
            for (String patronymic : names) {
                for (String surname : surnames) {

                    fullName = bundleFirst.getString(name) + " "
                            + bundleFirst.getString(patronymic) + " "
                            + bundleLast.getString(surname);
                    statement.setString(1, fullName);
                    statement.setString(2, Integer.toString(age.nextInt(20) + 30));
                    statement.executeUpdate();

                    counter++;

                    for (String patronymicSecond : names) {

                        fullName = bundleFirst.getString(name) + " "
                                + bundleFirst.getString(patronymicSecond) + " "
                                + bundleLast.getString(surname);
                        statement.setString(1, fullName);
                        statement.setString(2, Integer.toString(age.nextInt(20) + 30));
                        statement.executeUpdate();

                        if (counter++ > 1000000) {
                            break head;
                        }
                    }
                }

                for (String patronymicSecond : names) {
                    for (String surname : surnames) {

                        */
/*To Do*//*

                        counter++;
                    }
                }
            }
        }
*/
    }
}
