package lk.ijse.pos.bo;

import lk.ijse.pos.dao.custom.CustomerDAO;
import lk.ijse.pos.dao.custom.ItemDAO;
import lk.ijse.pos.dao.custom.OrderDAO;
import lk.ijse.pos.dao.custom.OrderDetailDAO;
import lk.ijse.pos.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.pos.dao.custom.impl.ItemDAOImpl;
import lk.ijse.pos.dao.custom.impl.OrderDAOImpl;
import lk.ijse.pos.dao.custom.impl.OrderDetailDAOImpl;
import lk.ijse.pos.model.Orders;

import java.util.ArrayList;


public class PurchaseOrderBOImpl {

    private  CustomerDAO customerDAO = new CustomerDAOImpl();
    private  ItemDAO itemDAO = new ItemDAOImpl();
    private  OrderDAO orderDAO = new OrderDAOImpl();
    private OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();


    public boolean purchaseOrder(Orders orders, ArrayList<OrderDetailDAO> orderDetails){


    }
}
