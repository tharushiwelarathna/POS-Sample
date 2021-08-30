package lk.ijse.pos.dao.custom.impl;


import lk.ijse.pos.dao.CrudUtil;
import lk.ijse.pos.dao.custom.OrderDetailDAO;
import lk.ijse.pos.model.OrderDetails;

;import java.util.ArrayList;


public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public boolean add(OrderDetails orderDetails) throws Exception {
        String sql = "INSERT INTO OrderDetail VALUES (?,?,?,?)";
        return CrudUtil.executeUpdate(sql,orderDetails.getOrderId(),orderDetails.getItemCode(),orderDetails.getQty(),orderDetails.getUnitPrice());
    }

    @Override
    public boolean update(OrderDetails orderDetails) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String s) throws Exception {
        return false;
    }

    @Override
    public OrderDetails search(String s) throws Exception {
        return null;
    }

    @Override
    public ArrayList<OrderDetails> getAll() throws Exception {
        return null;
    }
    /*
    @Override
    public boolean addOrderDetails(OrderDetails orderDetails) throws Exception {
//        Connection connection = DBConnection.getInstance().getConnection();
//        String sql = "INSERT INTO OrderDetail VALUES (?,?,?,?)";
//        PreparedStatement pstm = connection.prepareStatement(sql);
//
//        pstm.setObject(1, orderDetails.getOrderId());
//        pstm.setObject(2, orderDetails.getItemCode());
//        pstm.setObject(3, orderDetails.getQty());
//        pstm.setObject(4, orderDetails.getUnitPrice());
//
//        return pstm.executeUpdate()>0;
        String sql = "INSERT INTO OrderDetail VALUES (?,?,?,?)";
        return CrudUtil.executeUpdate(sql,orderDetails.getOrderId(),orderDetails.getItemCode(),orderDetails.getQty(),orderDetails.getUnitPrice());
    }

     */
}
