package lk.ijse.pos.bo;

import lk.ijse.pos.controller.OrderFormController;
import lk.ijse.pos.dao.custom.CustomerDAO;
import lk.ijse.pos.dao.custom.ItemDAO;
import lk.ijse.pos.dao.custom.OrderDAO;
import lk.ijse.pos.dao.custom.OrderDetailDAO;
import lk.ijse.pos.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.pos.dao.custom.impl.ItemDAOImpl;
import lk.ijse.pos.dao.custom.impl.OrderDAOImpl;
import lk.ijse.pos.dao.custom.impl.OrderDetailDAOImpl;
import lk.ijse.pos.db.DBConnection;
import lk.ijse.pos.model.Item;
import lk.ijse.pos.model.OrderDetails;
import lk.ijse.pos.model.Orders;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PurchaseOrderBOImpl {

    private  CustomerDAO customerDAO = new CustomerDAOImpl();
    private  ItemDAO itemDAO = new ItemDAOImpl();
    private  OrderDAO orderDAO = new OrderDAOImpl();
    private OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();


    public boolean purchaseOrder(Orders orders, ArrayList<OrderDetails> orderDetails) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        try {

            connection.setAutoCommit(false);
            boolean b1 = orderDAO.add(orders);
            if (!b1) {
                connection.rollback();
                return false;
            }

            for (OrderDetails orderDetail: orderDetails) {
                boolean b2 = orderDetailDAO.add(orderDetail);
                if (!b2) {
                    connection.rollback();
                    return false;
                }


                int qtyOnHand = 0;

                Item item = itemDAO.search(orderDetail.getItemCode());

                if (item != null) {
                    qtyOnHand = item.getQtyOnHand();
                }

                boolean b = itemDAO.updateQtyOnHand(orderDetail.getItemCode(), qtyOnHand - orderDetail.getQty());

                if (!b) {
                    connection.rollback();
                    return false;
                }
            }

            connection.commit();
            return true;

        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                throw new Exception(ex1);
            }
            throw new Exception(ex);
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                throw new Exception(ex);
            }
        }

        }



}
