package lk.ijse.pos.dao.custom;

import lk.ijse.pos.dao.CrudDAO;
import lk.ijse.pos.model.Item;

import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<Item, String> {
    public boolean updateQtyOnHand(String code, int qtyOnHand) throws Exception;
}
