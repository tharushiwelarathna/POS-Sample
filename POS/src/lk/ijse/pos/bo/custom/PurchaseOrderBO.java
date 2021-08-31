package lk.ijse.pos.bo.custom;

import lk.ijse.pos.bo.SuperBO;
import lk.ijse.pos.model.OrderDetails;
import lk.ijse.pos.model.Orders;

import java.util.ArrayList;

public interface PurchaseOrderBO extends SuperBO {
    boolean purchaseOrder(Orders order, ArrayList<OrderDetails> orderDetails) throws Exception;

}
