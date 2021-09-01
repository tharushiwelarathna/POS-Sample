package lk.ijse.pos.bo.custom;

import lk.ijse.pos.bo.SuperBO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.entity.Item;

import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    boolean addItem(ItemDTO item) throws Exception;

    boolean deleteItem(String code) throws Exception;

    boolean updateItem(ItemDTO item) throws Exception;

    ItemDTO searchItem(String code) throws Exception;

    ArrayList<ItemDTO> getAllItems() throws Exception;

    boolean updateItemQtyOnHand(String code, int qtyOnHand)throws Exception;


}
