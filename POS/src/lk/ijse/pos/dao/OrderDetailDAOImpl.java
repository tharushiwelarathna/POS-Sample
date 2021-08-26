package lk.ijse.pos.dao;

import lk.ijse.pos.db.DBConnection;
import lk.ijse.pos.model.Item;
import lk.ijse.pos.model.OrderDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class OrderDetailDAOImpl {
    public boolean addOrderDetail(OrderDetails orderDetails) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO OrderDetail VALUES (?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, orderDetails.getOrderId());
        pstm.setObject(2, orderDetails.getItemCode());
        pstm.setObject(3, orderDetails.getQty());
        pstm.setObject(4, orderDetails.getUnitPrice());

        return pstm.executeUpdate()>0;

    }

    public boolean updateOrderDetail(OrderDetails orderDetails) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("UPDATE OrderDetails SET qty=?, unitPrice=? WHERE orderId=? AND itemCode=?");
        pstm.setObject(1, orderDetails.getQty());
        pstm.setObject(2, orderDetails.getUnitPrice());
        pstm.setObject(3, orderDetails.getOrderId());
        pstm.setObject(4, orderDetails.getItemCode());
        return (pstm.executeUpdate() > 0);



    }

    public boolean deleteOrderDetail(String id) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("DELETE FROM OrderDetails WHERE orderId=? AND itemCode=?");
        pstm.setObject(1, id);
        return (pstm.executeUpdate() > 0);





    }

    public OrderDetails searchOrderDetail(String id) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM OrderDetails  WHERE orderId=? AND itemCode=?");
        stm.setObject(1, id);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new OrderDetails(rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getBigDecimal(4));

        }
        return null;


    }
    public ArrayList<OrderDetails> getAllOrderDetails() throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM OrderDetails");
        ArrayList<OrderDetails> allOrders = new ArrayList<>();
        while (rst.next()) {
            OrderDetails order = new OrderDetails(rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getBigDecimal(4));

            allOrders.add(order);
        }
        return allOrders ;

    }
}
