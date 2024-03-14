package main.java.com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.com.example.model.BookCategory;
import main.java.com.example.util.MySQLConnector;

public class BookCategoryDao {
    // Xem danh sách các danh mục sách
    public List<BookCategory> getAllCategories() {
        List<BookCategory> categories = new ArrayList<>();
        String query = "SELECT * FROM BookCategories";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                BookCategory category = new BookCategory();
                category.setCategoryID(resultSet.getInt("CategoryID"));
                category.setCategoryName(resultSet.getString("CategoryName"));
                category.setActive(resultSet.getBoolean("IsActive"));
                categories.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    // Tìm kiếm danh mục sách theo tên
    public BookCategory getCategoryByName(String categoryName) {
        BookCategory category = null;
        String query = "SELECT * FROM BookCategories WHERE CategoryName = ?";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, categoryName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                category = new BookCategory();
                category.setCategoryID(resultSet.getInt("CategoryID"));
                category.setCategoryName(resultSet.getString("CategoryName"));
                category.setActive(resultSet.getBoolean("IsActive"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;
    }
    // Thêm một thể loại sách mới
    public boolean addCategory(BookCategory category) {
        String query = "INSERT INTO BookCategories (CategoryName, IsActive) VALUES (?, ?)";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, category.getCategoryName());
            preparedStatement.setBoolean(2, category.isActive());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
