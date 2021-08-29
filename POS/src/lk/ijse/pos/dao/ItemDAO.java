package lk.ijse.pos.dao;

import lk.ijse.pos.model.Item;

import java.util.ArrayList;

public interface ItemDAO {
    public boolean addItem(Item item) throws Exception;
    public boolean updateItem(Item item) throws Exception;
    public boolean deleteItem(String id) throws Exception;
    public Item searchItem(String id) throws Exception;
    public boolean updateQtyOnHand(String code, int qtyOnHand) throws Exception;
    public ArrayList<Item> getAllItems() throws Exception;
}
