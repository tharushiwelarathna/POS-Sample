package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.custom.PurchaseOrderBO;
import lk.ijse.pos.controller.OrderFormController;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.custom.ItemDAO;
import lk.ijse.pos.dao.custom.OrderDAO;
import lk.ijse.pos.dao.custom.OrderDetailDAO;
import lk.ijse.pos.db.DBConnection;
import lk.ijse.pos.dto.OrderDetailsDTO;
import lk.ijse.pos.dto.OrdersDTO;
import lk.ijse.pos.entity.Item;
import lk.ijse.pos.entity.OrderDetails;
import lk.ijse.pos.entity.Orders;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PurchaseOrderBOImpl implements PurchaseOrderBO {


//    private  ItemDAO itemDAO = new ItemDAOImpl();
//    private  OrderDAO orderDAO = new OrderDAOImpl();
//    private OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();

    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private final OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAIL);

    @Override
    public boolean purchaseOrder(OrdersDTO ordersDTO) throws Exception {
        Connection connection= DBConnection.getInstance().getConnection();

        try {

            connection.setAutoCommit(false);

            Orders order=new Orders(ordersDTO.getId(),ordersDTO.getDate(),ordersDTO.getCustomerId());

            boolean b1 = orderDAO.add(order);
            if (!b1) {
                connection.rollback();
                return false;
            }

            for (OrderDetailsDTO orderDetail : ordersDTO.getOrderDetails()) {
                OrderDetails orderDetails=new OrderDetails(orderDetail.getOrderId(),orderDetail.getItemCode(),orderDetail.getQty(),orderDetail.getUnitPrice());
                boolean b2 = orderDetailDAO.add(orderDetails);
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

        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(OrderFormController.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(OrderFormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(OrderFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }

        }



}
