package lk.ijse.animal_clinic.model;


import lk.ijse.animal_clinic.db.DbConnection;

import java.sql.*;
import java.time.LocalDate;

public class OrderModel {
    public static boolean saveOrder(String orderId, String cusId, LocalDate date) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO orders VALUES(?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, orderId);
        pstm.setString(2, cusId);
        pstm.setDate(3, Date.valueOf(date));

        return pstm.executeUpdate() > 0;
    }

    public String generateNextOrderId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentOrderId = null;

        if (resultSet.next()) {
            currentOrderId = resultSet.getString(1);
            return splitOrderId(currentOrderId);
        }
        return splitOrderId(null);
    }

    private String splitOrderId(String currentOrderId) {
        if (currentOrderId != null) {
            String[] split = currentOrderId.split("O");
            int id = Integer.parseInt(split[1]);
            id++;

            String formattedId = String.format("%03d", id);

            return "O" + formattedId;
        }
        return "O001";
    }
}
