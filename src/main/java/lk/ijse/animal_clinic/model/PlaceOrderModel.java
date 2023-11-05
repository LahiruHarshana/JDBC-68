package lk.ijse.animal_clinic.model;


import lk.ijse.animal_clinic.db.DbConnection;
import lk.ijse.animal_clinic.dto.PlaceOrderDto;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderModel {
    private final OrderModel orderModel = new OrderModel();
    private final ItemModel itemModel = new ItemModel();
    private final OrderDetailModel orderDetailModel = new OrderDetailModel();

    public boolean placeOrder(PlaceOrderDto pDto) throws SQLException {
        boolean result = false;
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSaved = OrderModel.saveOrder(pDto.getOrderId(), pDto.getCusId(), pDto.getDate());
            if (isOrderSaved) {
                boolean isUpdated = itemModel.updateItem(pDto.getTmList());
                if(isUpdated) {
                    boolean isOrderDetailSaved = orderDetailModel.saveOrderDetail(pDto.getOrderId(), pDto.getTmList());
                    if(isOrderDetailSaved) {
                        connection.commit();
                        result = true;
                    }
                }
            }
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return result;
    }
}
