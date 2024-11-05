package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/hotelinfo";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean insertHotel(Hotel hotel) {
        if (hotelExists(hotel.getName())) {
            return false;
        }
        
        String query = "INSERT INTO hotel (hotel_name, hotel_loc) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, hotel.getName());
            statement.setString(2, hotel.getLocation());
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static String getCustomerName(String phoneNumber) {
        String query = "SELECT name FROM customer WHERE number = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, phoneNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("name");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static boolean hotelExists(String hotelName) {
        String query = "SELECT COUNT(*) FROM hotel WHERE hotel_name = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, hotelName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void insertCustomer(Customer customer) {
        String sql = "INSERT INTO customer (number, name, password) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getNumber());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public enum AuthenticationResult {
        SUCCESS,
        WRONG_PASSWORD,
        NO_SUCH_USER
    }

    public static AuthenticationResult authenticateCustomer(String phoneNumber, String password) {
        String query = "SELECT password FROM customer WHERE number = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, phoneNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("password");
                    if (storedPassword.equals(password)) {
                        return AuthenticationResult.SUCCESS;
                    } else {
                        return AuthenticationResult.WRONG_PASSWORD;
                    }
                } else {
                    return AuthenticationResult.NO_SUCH_USER;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AuthenticationResult.NO_SUCH_USER;
        }
    }
    public static ResultSet getHotelsWithAverageRatings() {
        String sql = "SELECT h.hotel_name, h.hotel_loc, AVG(r.rating) as avg_rating " +
                     "FROM hotel h LEFT JOIN rates r ON h.hotel_name = r.hotel_name " +
                     "GROUP BY h.hotel_name, h.hotel_loc " +
                     "ORDER BY avg_rating DESC";
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static boolean verifyCustomer(String number, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean isValid = false;

        try {
            conn = getConnection();
            String sql = "SELECT * FROM customer WHERE number = ? AND password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, number);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                isValid = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
            close(conn);
        }

        return isValid;
    }
    public static String[] getHotelNames() {
        List<String> hotelNames = new ArrayList<>();
        String query = "SELECT hotel_name FROM hotel";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                hotelNames.add(rs.getString("hotel_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotelNames.toArray(new String[0]);
    }


    public static void insertRate(Rate rate) {
        String query = "INSERT INTO rates (hotel_name, customer_number, rating, review) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, rate.getHotelName());
            stmt.setString(2, rate.getCustomerNumber());
            stmt.setInt(3, rate.getRating());
            stmt.setString(4, rate.getReview());
            stmt.executeUpdate();
            System.out.println("Rate inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Hotel> getSortedHotelData() throws SQLException {
        List<Hotel> hotels = new ArrayList<>();
        Connection conn = getConnection();
        String query = "SELECT hotel_name, AVG(rating) AS average_rating " +
                       "FROM rates " +
                       "GROUP BY hotel_name " +
                       "ORDER BY average_rating DESC";

        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String hotelName = resultSet.getString("hotel_name");
                double averageRating = resultSet.getDouble("average_rating");

                
                Hotel hotel = new Hotel(hotelName, averageRating);
                hotels.add(hotel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hotels;
    }
    public static boolean isCustomerRegistered(String phoneNumber) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            String query = "SELECT COUNT(*) FROM customer WHERE number = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, phoneNumber);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
    public static List<Hotel> getHotelsWithRatings() {
        List<Hotel> hotels = new ArrayList<>();
        String query = "SELECT h.hotel_name, h.hotel_loc, AVG(r.rating) AS averageRating " +
                       "FROM hotel h " +
                       "LEFT JOIN rates r ON h.hotel_name = r.hotel_name " +
                       "GROUP BY h.hotel_name " +
                       "ORDER BY averageRating DESC";

        try (Connection connection = getConnection(); 
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String name = resultSet.getString("hotel_name");
                String location = resultSet.getString("hotel_loc");
                double averageRating = resultSet.getDouble("averageRating");
                hotels.add(new Hotel(name, location, averageRating));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hotels;
    }
    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
