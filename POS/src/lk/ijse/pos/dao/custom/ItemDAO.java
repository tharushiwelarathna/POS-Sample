package lk.ijse.pos.dao.custom;

import lk.ijse.pos.dao.SuperDAO;
import lk.ijse.pos.model.Item;

import java.util.ArrayList;

public interface ItemDAO extends SuperDAO<Item,String> {
    public boolean updateQtyOnHand(String code, int qtyOnHand) throws Exception;
}
