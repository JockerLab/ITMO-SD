package ru.shaldin.sd.refactoring.sql;

import ru.shaldin.sd.refactoring.database.Database;
import ru.shaldin.sd.refactoring.domain.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ru.shaldin.sd.refactoring.database.Database.executeQuery;
import static ru.shaldin.sd.refactoring.database.Database.executeUpdate;

public class Queries {
    public static final String TABLE_NAME = "PRODUCT";

    public static void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)";
        executeUpdate(sql);
    }

    public static void insert(String name, long price) throws SQLException {
        executeUpdate("INSERT INTO " + TABLE_NAME +
                " (NAME, PRICE) VALUES (\"" + name + "\"," + price + ")");
    }

    public static List<Product> selectAll() throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME;
        return executeQuery(sql, Database::getListOfProductsAsResult);
    }

    public static List<Product> getMax() throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY PRICE DESC LIMIT 1";
        return executeQuery(sql, Database::getListOfProductsAsResult);
    }

    public static List<Product> getMin() throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY PRICE LIMIT 1";
        return executeQuery(sql, Database::getListOfProductsAsResult);
    }

    public static Optional<Long> getSum() throws SQLException {
        String sql = "SELECT SUM(price) FROM " + TABLE_NAME;
        return executeQuery(sql, Database::getLongAsResult);
    }

    public static Optional<Long> getCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
        return executeQuery(sql, Database::getLongAsResult);
    }
}
