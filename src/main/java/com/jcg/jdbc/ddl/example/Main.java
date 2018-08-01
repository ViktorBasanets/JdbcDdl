package com.jcg.jdbc.ddl.example;

import java.sql.*;
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

    private static void fillStaff(ResourceBundle bundleComA, ResourceBundle bundleComB,
                                  ResourceBundle bundlePatterns, Locale locale, int MAX)
        throws SQLException {

        String query, name, departmentName, districtName;
        Random age = new Random();
        PreparedStatement statement;
        ResultSet resultSet;

        for (int i = 0; i < MAX; i++) {
            query = bundleComA.getString("insert.into.staff");
            statement = connection.prepareStatement(query);
            name = bundlePatterns.getString("firstName") + i + " "
                    + bundlePatterns.getString("familyName") + i + " "
                    + bundlePatterns.getString("lastName") + i;
            statement.setString(1, name);
            statement.setInt(2, age.nextInt(20) + 30);
            statement.executeUpdate();
            statement.close();
        }
    }

    public static void fillTablesTestData() throws SQLException {

        Locale locale = new Locale("en", "Us");

        ResourceBundle bundlePatterns = ResourceBundle.getBundle("patterns", locale);
        ResourceBundle bundleComA = ResourceBundle.getBundle("sql/initComA", locale);
        ResourceBundle bundleComB = ResourceBundle.getBundle("sql/initComB", locale);

        String query, name, departmentName, districtName;
        Random age = new Random();
        PreparedStatement statement;
        ResultSet resultSet;
        int MAX = 500;

        for (int i = 0; i < MAX; i++) {
            query = bundleComA.getString("insert.into.staff");
            statement = connection.prepareStatement(query);
            name = bundlePatterns.getString("firstName") + i + " "
                    + bundlePatterns.getString("familyName") + i + " "
                    + bundlePatterns.getString("lastName") + i;
            statement.setString(1, name);
            statement.setInt(2, age.nextInt(20) + 30);
            statement.executeUpdate();
            statement.close();

            query = bundleComA.getString("insert.into.departments");
            statement = connection.prepareStatement(query);
            departmentName = bundlePatterns.getString("departmentName") + i % 20;
            statement.setString(1, departmentName);
            districtName = bundlePatterns.getString("districtName") + i % 10;
            statement.setString(2, districtName);
            statement.executeUpdate();
            statement.close();

            query = bundleComA.getString("select.coma.by.id");
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, departmentName);
            resultSet = statement.executeQuery();
//            statement.close();
            if (resultSet.first()) {
                query = bundleComA.getString("insert.into.employees");
                statement = connection.prepareStatement(query);
                statement.setLong(1, resultSet.getLong(1));
                statement.setLong(2, resultSet.getLong(2));
                statement.executeUpdate();
                statement.close();
            }

///////////////////////////////////////////////////////////////////////////////////

            query = bundleComB.getString("insert.into.staff");
            statement = connection.prepareStatement(query);
            name = bundlePatterns.getString("firstName") + MAX + i + " "
                    + bundlePatterns.getString("familyName") + MAX + i + " "
                    + bundlePatterns.getString("lastName") + MAX + i;
            statement.setString(1, name);
            statement.setInt(2, age.nextInt(20) + 30);
            statement.executeUpdate();
            statement.close();

            query = bundleComB.getString("insert.into.departments");
            statement = connection.prepareStatement(query);
            departmentName = bundlePatterns.getString("departmentName") + i % 25;
            statement.setString(1, departmentName);
            districtName = bundlePatterns.getString("districtName") + i % 15 ;
            statement.setString(2, districtName);
            statement.executeUpdate();
            statement.close();

            query = bundleComB.getString("select.comb.by.id");
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, departmentName);
            resultSet = statement.executeQuery();
//            statement.close();
            if (resultSet.first()) {
                query = bundleComB.getString("insert.into.employees");
                statement = connection.prepareStatement(query);
                statement.setLong(1, resultSet.getLong(1));
                statement.setLong(2, resultSet.getLong(2));
                statement.executeUpdate();
                statement.close();
            }
        }
    }
}
