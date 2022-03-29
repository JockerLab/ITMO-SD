package ru.shaldin.sd.refactoring.database;

import ru.shaldin.sd.refactoring.domain.Product;

import java.sql.*;
import java.util.*;
import java.util.function.Function;

public class Database {
    public static final String URL = "jdbc:sqlite:test.db";

    public static void executeUpdate(String sql) throws SQLException {
        try (Connection c = DriverManager.getConnection(URL)) {
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    public static List<Product> getListOfProductsAsResult(ResultSet rs) {
        try {
            List<Product> products = new ArrayList<>();
            while (rs.next()) {
                String name = rs.getString("name");
                long price = rs.getInt("price");
                products.add(new Product(name, price));
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Optional<Long> getLongAsResult(ResultSet rs) {
        try {
            if (rs.next()) {
                return Optional.of(rs.getLong(1));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T executeQuery(String sql, Function<ResultSet, T> method) throws SQLException {
        try (Connection c = DriverManager.getConnection(URL)) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            T result = method.apply(rs);
            rs.close();
            stmt.close();

            return result;
        }
    }
}
