package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.CrudUtil;
import lk.ijse.pos.dao.custom.ItemDAO;
import lk.ijse.pos.entity.Item;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean updateQtyOnHand(String code, int qtyOnHand) throws Exception {
        String sql = "UPDATE Item SET qtyOnHand=? WHERE id=?";
        return CrudUtil.executeUpdate(sql,qtyOnHand,code);
    }

    @Override
    public boolean add(Item item) throws Exception {
        String sql = "INSERT INTO Item VALUES (?,?,?,?)";
        return CrudUtil.executeUpdate(sql,item.getCode(),item.getDescription(),item.getUnitPrice(),item.getQtyOnHand());

    }

    @Override
    public boolean update(Item item) throws Exception {
        String sql = "UPDATE Item SET description=?, unitprice=?, qtyonhand=? WHERE id=?";
        return CrudUtil.executeUpdate(sql,item.getDescription(),item.getUnitPrice(),item.getQtyOnHand(),item.getCode());
    }

    @Override
    public boolean delete(String id) throws Exception {
        String sql = "DELETE FROM Item WHERE id=?";
        return CrudUtil.executeUpdate(sql,id);
    }

    @Override
    public Item search(String id) throws Exception {
        String sql = "SELECT * FROM Item where id=?";
        ResultSet rst = CrudUtil.executeQuery(sql,id);

        if (rst.next()){
            return new Item(rst.getString(1),rst.getString(2),rst.getBigDecimal(3),rst.getInt(4));
        }
        return null;
    }

    @Override
    public ArrayList<Item> getAll() throws Exception {
        String sql = "SELECT * FROM Item";
        ResultSet rst = CrudUtil.executeQuery(sql);
        ArrayList<Item> items = new ArrayList<>();
        while (rst.next()){
            Item item = new Item(rst.getString(1),rst.getString(2),rst.getBigDecimal(3),rst.getInt(4));
            items.add(item);
        }

        return items;
    }
    /*
    @Override
    public boolean addItem(Item item) throws Exception {
//        Connection connection = DBConnection.getInstance().getConnection();
//        PreparedStatement pstm = connection.prepareStatement("INSERT INTO Item VALUES (?,?,?,?)");
//        pstm.setObject(1, item.getCode());
//        pstm.setObject(2, item.getDescription());
//        pstm.setObject(3, item.getUnitPrice());
//        pstm.setObject(4, item.getQtyOnHand());
//        return (pstm.executeUpdate() > 0);
        return CrudUtil.executeUpdate("INSERT INTO Item VALUES(?,?,?,?)",item.getCode(),item.getDescription(),item.getUnitPrice(),item.getQtyOnHand());

    }
    @Override
    public boolean deleteItem(String code) throws Exception {
//        Connection connection = DBConnection.getInstance().getConnection();
//        PreparedStatement pstm = connection.prepareStatement("DELETE FROM Item WHERE code=?");
//        pstm.setObject(1, code);
//        return (pstm.executeUpdate() > 0);
        String sql = "DELETE FROM Item WHERE id=?";
        return CrudUtil.executeUpdate(sql,code);
    }
    @Override
    public boolean updateQtyOnHand(String code, int qtyOnHand) throws Exception {
//        Connection connection = DBConnection.getInstance().getConnection();
//        PreparedStatement pstm = connection.prepareStatement("UPDATE Item SET qtyOnHand=? WHERE id=?");
//        pstm.setObject(1, qtyOnHand);
//        pstm.setObject(2, code);
//
//        return pstm.executeUpdate()>0;
        String sql = "UPDATE Item SET qtyOnHand=? WHERE id=?";
        return CrudUtil.executeUpdate(sql,qtyOnHand,code);

    }

    @Override
    public boolean updateItem(Item item) throws Exception {
//        Connection connection = DBConnection.getInstance().getConnection();
//        PreparedStatement pstm = connection.prepareStatement("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?");
//        pstm.setObject(1, item.getDescription());
//        pstm.setObject(2, item.getUnitPrice());
//        pstm.setObject(3, item.getQtyOnHand());
//        pstm.setObject(4, item.getCode());
//        return (pstm.executeUpdate() > 0);
        String sql = "UPDATE Item SET description=?, unitprice=?, qtyonhand=? WHERE id=?";
        return CrudUtil.executeUpdate(sql,item.getDescription(),item.getUnitPrice(),item.getQtyOnHand(),item.getCode());
    }

    @Override
    public Item searchItem(String code) throws Exception {
//        Connection connection = DBConnection.getInstance().getConnection();
//        PreparedStatement stm = connection.prepareStatement("SELECT * FROM Item where code=?");
//        stm.setObject(1, code);
//        ResultSet rst = stm.executeQuery();
//        if (rst.next()) {
//            return new Item(rst.getString(1),
//                    rst.getString(2),
//                    rst.getBigDecimal(3),
//                    rst.getInt(4));
//        }
//        return null;
        String sql = "SELECT * FROM Item where id=?";
        ResultSet rst = CrudUtil.executeQuery(sql,code);

        if (rst.next()){
            return new Item(rst.getString(1),rst.getString(2),rst.getBigDecimal(3),rst.getInt(4));
        }
        return null;
    }
    @Override
    public ArrayList<Item> getAllItems() throws Exception {
//        Connection connection = DBConnection.getInstance().getConnection();
//        Statement stm = connection.createStatement();
//        ResultSet rst = stm.executeQuery("SELECT * FROM Item");
//        ArrayList<Item> allItems = new ArrayList<>();
//        while (rst.next()) {
//            Item item = new Item(rst.getString(1),
//                    rst.getString(2),
//                    rst.getBigDecimal(3),
//                    rst.getInt(4));
//            allItems.add(item);
//        }
//        return allItems;
        String sql = "SELECT * FROM Item";
        ResultSet rst = CrudUtil.executeQuery(sql);
        ArrayList<Item> items = new ArrayList<>();
        while (rst.next()){
            Item item = new Item(rst.getString(1),rst.getString(2),rst.getBigDecimal(3),rst.getInt(4));
            items.add(item);
        }

        return items;
    }

     */
}
