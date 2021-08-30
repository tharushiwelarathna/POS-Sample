package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.CrudUtil;
import lk.ijse.pos.dao.custom.OrderDAO;
import lk.ijse.pos.db.DBConnection;
import lk.ijse.pos.model.Orders;



public class OrderDAOImpl implements OrderDAO {
    @Override
    public boolean addOrder(Orders orders) throws Exception {
//        Connection connection = DBConnection.getInstance().getConnection();
//        PreparedStatement pstm = connection.prepareStatement("INSERT INTO Orders VALUES (?,?,?)");
//
//        pstm.setObject(1, orders.getId());
//        pstm.setObject(2, orders.getDate());
//        pstm.setObject(3, orders.getCustomerId());
//
//        return pstm.executeUpdate()>0;
        String sql = "INSERT INTO Orders VALUES (?,?,?)";
        return CrudUtil.executeUpdate(sql,orders.getId(),orders.getDate(),orders.getCustomerId());

    }
}
