package lk.ijse.pos.dao;

import lk.ijse.pos.db.DBConnection;
import lk.ijse.pos.model.Customer;
import lk.ijse.pos.model.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl {
    public boolean addOrder(Orders orders) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO Orders VALUES (?,?,?)");

        pstm.setObject(1, orders.getId());
        pstm.setObject(2, orders.getDate());
        pstm.setObject(3, orders.getCustomerId());

        return pstm.executeUpdate()>0;

    }

    public boolean updateOrder(Orders orders) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("UPDATE Orders SET date=?, customerId=? WHERE id=?");

        pstm.setObject(1, orders.getDate());
        pstm.setObject(2, orders.getCustomerId());
        pstm.setObject(3, orders.getId());
        return pstm.executeUpdate()>0;

    }

    public boolean deleteOrder(String id) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("DELETE FROM Orders WHERE id=?");
        pstm.setObject(1, id);
        return pstm.executeUpdate()>0;


    }

    public Customer searchOrder(String id) throws Exception {
        String sql = "SELECT * FROM `Orders` WHERE id=?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);
        ResultSet rst = pstm.executeQuery();
        if (rst.next()) {
            return new Customer(rst.getString(1),rst.getDate(2),rst.getString(3));
        }
        return null;





    }
    public ArrayList<Orders> getAllOrders() throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM Orders");
        ArrayList<Orders> orders = new ArrayList<>();
        while (rst.next()) {
            Orders order = new Orders(rst.getString(1), rst.getDate(2), rst.getString(3));
            orders.add(order);
        }
        return orders;




    }
}
