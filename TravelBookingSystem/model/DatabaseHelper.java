package model;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/travel_sys_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    public int getUserIdByEmail(String email) throws SQLException {
        String sql = "SELECT user_id FROM users WHERE email = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("user_id");
                } else {
                    return -1;
                }
            }
        }
    }
    public int getAdminIdByPhone(String phone) throws SQLException {
        String sql = "SELECT admin_id FROM admin WHERE phone_number = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, phone);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("admin_id");
                } else {
                    return -1;
                }
            }
        }
    }
    public static int getBookingIdByDetails(int userId, String serviceType, int serviceId) throws SQLException {
        String sql = "SELECT booking_id FROM booking WHERE user_id = ? AND service_type = ? AND service_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, serviceType);
            stmt.setInt(3, serviceId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("booking_id");
                } else {
                    return -1;
                }
            }
        }
    }
    public static int getFlightIdByDetails(String airlineName, String destination, String departureDate) throws SQLException {
        String sql = "SELECT flight_id FROM flights WHERE airline_name = ? AND destination = ? AND departure_date = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, airlineName);
            stmt.setString(2, destination);
            stmt.setString(3, departureDate);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("flight_id");
                } else {
                    return -1; 
                }
            }
        }
    }


    public static boolean authenticateAdmin(String phoneNumber, String password) throws SQLException {
        String sql = "SELECT password FROM admin WHERE phone_number = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, phoneNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    return storedPassword.equals(password);
                } else {
                    return false;
                }
            }
        }
    }
    public void addAdmin(String name, String phoneNumber, String password) {
        String query = "INSERT INTO admin (admin_name, phone_number, password) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, phoneNumber);
            stmt.setString(3, password);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean doesAdminExistByPhoneNumber(String phoneNumber) {
        String query = "SELECT COUNT(*) FROM admin WHERE phone_number = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, phoneNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean addFlight(String airlineName, String destination, String departureDate, double price) {
        String query = "INSERT INTO flights (airline_name, destination, departure_date, price) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, airlineName);
            stmt.setString(2, destination);
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = dateFormat.parse(departureDate);
            java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
            stmt.setDate(3, sqlDate);
            
            stmt.setDouble(4, price);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<String> loadFlightOptions() {
        List<String> flights = new ArrayList<>();
        String query = "SELECT flight_id, airline_name FROM flights";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String flightId = rs.getString("flight_id");
                String airlineName = rs.getString("airline_name");
                flights.add(flightId + " - " + airlineName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }

    public static Flight getFlightDetails(String flightId) {
        String query = "SELECT * FROM flights WHERE flight_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, flightId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String airlineName = rs.getString("airline_name");
                    String destination = rs.getString("destination");
                    String depart_date = rs.getString("departure_date");
                    double price = rs.getDouble("price");
                    return new Flight(airlineName, destination,depart_date ,price);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean updateFlight(String flightId, String airlineName, String destination, String departureDate, double price) {
        String query = "UPDATE flights SET airline_name = ?, destination = ?, departure_date = ?, price = ? WHERE flight_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = dateFormat.parse(departureDate);
            java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

            stmt.setString(1, airlineName);
            stmt.setString(2, destination);
            stmt.setDate(3, sqlDate);
            stmt.setDouble(4, price);
            stmt.setString(5, flightId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static ResultSet getFlightOptions() throws SQLException {
        String query = "SELECT flight_id, airline_name FROM flights";
        return getConnection().createStatement().executeQuery(query);
    }

    public static boolean deleteFlight(String flightId) {
        String query = "DELETE FROM flights WHERE flight_id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setString(1, flightId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    }
    public static boolean addHotel(String hotelName, String location, double pricePerNight) {
        String query = "INSERT INTO hotels (name, location, price_per_night) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setString(1, hotelName);
            stmt.setString(2, location);
            stmt.setDouble(3, pricePerNight);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    }
    public static boolean updateHotel(String hotelId, String name, String location, int availableRooms, double pricePerNight) {
        String query = "UPDATE hotels SET name = ?, location = ?, available_rooms = ?, price_per_night = ? WHERE hotel_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, location);
            stmt.setInt(3, availableRooms);
            stmt.setDouble(4, pricePerNight);
            stmt.setString(5, hotelId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void loadHotelOptions(JComboBox<String> comboBoxHotels) throws SQLException {
        String query = "SELECT hotel_id, name FROM hotels";
        try (ResultSet rs = getConnection().createStatement().executeQuery(query)) {
            while (rs.next()) {
                String hotelId = rs.getString("hotel_id");
                String hotelName = rs.getString("name");
                comboBoxHotels.addItem(hotelId + " - " + hotelName);
            }
        }
    }

    public static boolean deleteHotel(String hotelId) throws SQLException {
        String query = "DELETE FROM hotels WHERE hotel_id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setString(1, hotelId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    public boolean addTransportation(String type, String location, double price) {
        String query = "INSERT INTO transportation (type, location, price) VALUES (?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, type);
            stmt.setString(2, location);
            stmt.setDouble(3, price);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    }
    public DefaultComboBoxModel<String> getTransportOptions() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        String query = "SELECT transport_id, type FROM transportation";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("transport_id");
                String type = rs.getString("type");
                model.addElement(id + " - " + type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }

    public boolean deleteTransportById(String transportId) {
        String query = "DELETE FROM transportation WHERE transport_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, Integer.parseInt(transportId));
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean authenticateUser(String email, String password) {
        String query = "SELECT password FROM users WHERE email = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                return storedPassword.equals(password); // In a real application, use hashing
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean userExists(String email) {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addUser(String name, String email, String phone, String password) {
        String query = "INSERT INTO users (name, password, email, phone_number) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static Booking getBookingById(int bookingId) throws SQLException {
        String sql = "SELECT * FROM bookings WHERE booking_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookingId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Booking(
                        rs.getInt("booking_id"), 
                        rs.getString("user_email"),
                        rs.getString("destination"),
                        rs.getString("flight"),
                        rs.getString("departure_date"),
                        rs.getString("hotel"),
                        rs.getString("transportation"),
                        Booking.Status.valueOf(rs.getString("status"))
                    );
                }
            }
        }
        return null;
    }


    public static int getBookingIdByDetails(String userEmail, String destination, String flight, 
            Date departDate, String hotel, String transportation, 
            Date bookingDate) throws SQLException {
    		String query = "SELECT booking_id FROM bookings WHERE user_email = ? AND destination = ? AND flight = ? " +
    					   "AND departure_date = ? AND hotel = ? AND transportation = ?";

    		try (Connection conn = getConnection();
    				PreparedStatement stmt = conn.prepareStatement(query)){
    				stmt.setString(1, userEmail);
    				stmt.setString(2, destination);
					stmt.setString(3, flight);
					stmt.setDate(4, departDate);
					stmt.setString(5, hotel);
					stmt.setString(6, transportation);
					

						try (ResultSet rs = stmt.executeQuery()) {
							if (rs.next()) {
								return rs.getInt("booking_id");
								}
							return -1;
							}
    					}
    				}
    public static Users getUserByEmail(String email) throws SQLException {
        Users user = null;
        String query = "SELECT * FROM users WHERE email = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String name = rs.getString("name");
                String phone = rs.getString("phone_number");
                String password = rs.getString("password");
                user = new Users(name, email, phone, password);
            }
        }
        
        return user;
    }

    public static boolean updateUserInfo(String currentEmail, String name, String newEmail, String phone, String password) throws SQLException {
        String query = "UPDATE users SET name = ?, email = ?, phone_number = ?, password = ? WHERE email = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, name);
            stmt.setString(2, newEmail);
            stmt.setString(3, phone);
            stmt.setString(4, password);
            stmt.setString(5, currentEmail);
            
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }
    public static List<Booking> getBookingsByEmail(String email) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings WHERE user_email = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("booking_id");
                String destination = rs.getString("destination");
                String flight = rs.getString("flight");
                String hotel = rs.getString("hotel");
                String transportation = rs.getString("transportation");
                String departDate = rs.getString("depart_date");
                String statusString = rs.getString("status");
                
                Booking.Status status;
                try {
                    status = Booking.Status.valueOf(statusString.toUpperCase());
                } catch (IllegalArgumentException e) {
                  
                    status = Booking.Status.CANCELED; 
                }
                
                Booking booking = new Booking(id, email, destination, flight, departDate, hotel, transportation, status);
                bookings.add(booking);
            }
        }
        
        return bookings;
    }

    public static List<Flight> getAvailableFlights() {
        List<Flight> flights = new ArrayList<>();
        String query = "SELECT * FROM flights"; 

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Flight flight = new Flight();
                flight.setFlightID(rs.getInt("flight_id"));
                flight.setAirline_name(rs.getString("airline_name"));
                flight.setDestination(rs.getString("destination"));
                flight.setDeparture_date(rs.getString("departure_time"));
                flights.add(flight);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return flights;
    }
    public static List<Hotel> getHotelsByDestination(String destination) {
        List<Hotel> hotels = new ArrayList<>();
        String query = "SELECT * FROM hotels WHERE location = ?";

        try (Connection conn =getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, destination);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Hotel hotel = new Hotel();
                hotel.setHotelID(rs.getInt("hotel_id"));
                hotel.setName(rs.getString("name"));
                hotel.setLocation(rs.getString("destination"));
                hotel.setPrice_per_night(rs.getDouble("price_per_night"));

                hotels.add(hotel);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return hotels;
    }
    public static List<Transport> getTransportByDestination(String destination) {
        List<Transport> transports = new ArrayList<>();
        String query = "SELECT * FROM transport WHERE location = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, destination);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transport transport = new Transport();
                transport.setTransportID(rs.getInt("transport_id"));
                transport.setType(rs.getString("type"));
                transport.setLocation(rs.getString("location"));
                transport.setPrice(rs.getDouble("price"));
              

                transports.add(transport);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return transports;
    }
    public static void addBooking(Booking booking) throws SQLException {
        String query = "INSERT INTO bookings (user_email, destination, flight, departure_date, hotel, transportation, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, booking.getUserEmail());
            stmt.setString(2, booking.getDestination());
            stmt.setString(3, booking.getFlight());
            stmt.setString(4, booking.getDepartDate());
            stmt.setString(5, booking.getHotel());
            stmt.setString(6, booking.getTransportation());
            stmt.setString(7, booking.getStatus().name());
            stmt.executeUpdate();
        }
    }

    public static void saveBooking(String userEmail, String destination, String flight, String departDate, String hotel, String transportation) {
        String query = "INSERT INTO bookings (user_email, destination, flight, departure_date, hotel, transportation) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userEmail);
            stmt.setString(2, destination);
            stmt.setString(3, flight);
            stmt.setString(4, departDate);
            stmt.setString(5, hotel);
            stmt.setString(6, transportation);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getHotelIdByDetails(String name, String location) throws SQLException {
        String sql = "SELECT hotel_id FROM hotels WHERE name = ? AND location = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, location);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("hotel_id");
                } else {
                    return -1;
                }
            }
        }
    }
    public static int getTransportIdByDetails(String type, String location) throws SQLException {
        String sql = "SELECT transport_id FROM transportation WHERE type = ? AND location = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, type);
            stmt.setString(2, location);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("transport_id");
                } else {
                    return -1;
                }
            }
        }
    }
    public static void closeConnection(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
