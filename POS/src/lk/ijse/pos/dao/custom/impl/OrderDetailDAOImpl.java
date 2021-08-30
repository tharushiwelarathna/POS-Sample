package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.CrudUtil;
import lk.ijse.pos.dao.custom.OrderDetailDAO;
import lk.ijse.pos.db.DBConnection;
import lk.ijse.pos.model.OrderDetails;

;


public class OrderDetailDAOImpl implements OrderDetailDAO {
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
}
