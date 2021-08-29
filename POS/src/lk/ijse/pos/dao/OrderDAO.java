package lk.ijse.pos.dao;

import lk.ijse.pos.model.Orders;

public interface OrderDAO {
    public boolean addOrder(Orders orders) throws Exception;

}
