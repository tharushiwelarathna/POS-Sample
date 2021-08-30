package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.CrudUtil;
import lk.ijse.pos.dao.custom.CustomerDAO;
import lk.ijse.pos.model.Customer;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    /*
    @Override
    public boolean addCustomer(Customer customer) throws Exception {
//        Connection connection = DBConnection.getInstance().getConnection();
//        PreparedStatement pstm = connection.prepareStatement("INSERT INTO Customer VALUES (?,?,?)");
//        pstm.setObject(1, customer.getcID());
//        pstm.setObject(2, customer.getName());
//        pstm.setObject(3, customer.getAddress());
//        //pstm.setObject(4, 0);
//        return pstm.executeUpdate()>0;

        return CrudUtil.executeUpdate
                ("INSERT INTO Customer VALUES(?,?,?)",
                        customer.getcID(),customer.getName(),
                        customer.getAddress());

    }
    @Override
    public boolean updateCustomer(Customer customer) throws Exception {
//        Connection connection = DBConnection.getInstance().getConnection();
//
//        PreparedStatement pstm = connection.prepareStatement("UPDATE Customer SET name=?, address=? WHERE id=?");
//        pstm.setObject(1, customer.getName());
//        pstm.setObject(2, customer.getAddress());
//        pstm.setObject(3, customer.getcID());
//        return pstm.executeUpdate()>0;
        String sql = "UPDATE Customer SET name=?, address=? WHERE id=?";
        return CrudUtil.executeUpdate(sql,customer.getName(),customer.getAddress(),customer.getcID());
    }
    @Override
    public boolean deleteCustomer(String id) throws Exception {
//        Connection connection = DBConnection.getInstance().getConnection();
//        PreparedStatement pstm = connection.prepareStatement("DELETE FROM Customer WHERE id=?");
//        pstm.setObject(1, id);
//        return pstm.executeUpdate()>0;

        return CrudUtil.executeUpdate("DELETE FROM Customer WHERE id=?",id);


    }

    @Override
    public Customer searchCustomer(String id) throws Exception {
//        String sql = "select * from Customer where id=?";
//        Connection connection = DBConnection.getInstance().getConnection();
//        PreparedStatement pstm = connection.prepareStatement(sql);
//        pstm.setObject(1, id);
//        ResultSet rst = pstm.executeQuery();
//        if (rst.next()) {
//            return new Customer(rst.getString(1), rst.getString(2), rst.getString(3));
//        }
//        return null;

        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM Customer WHERE id=?",id);
        if (resultSet.next()) {
            return new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3));

        } else {
            return null;
        }

    }

     */
  /*  @Override
    public ArrayList<Customer> getAllCustomers() throws Exception {
//        Connection connection = DBConnection.getInstance().getConnection();
//        Statement stm = connection.createStatement();
//        ResultSet rst = stm.executeQuery("SELECT * FROM Customer");
//        ArrayList<Customer> alCustomers = new ArrayList<>();
//        while (rst.next()) {
//            Customer customer = new Customer(rst.getString(1), rst.getString(2), rst.getString(3));
//            alCustomers.add(customer);
//        }
//        return alCustomers;


        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM Customer");
        ArrayList<Customer> customerList = new ArrayList<>();
        while (resultSet.next()) {
            customerList.add(
                    new Customer(
                            resultSet.getString(1), resultSet.getString(2),
                            resultSet.getString(3))
            );
        }
        return customerList;
    }*/


    @Override
    public boolean add(Customer customer) throws Exception {
        String sql = "INSERT INTO Customer VALUES (?,?,?)";
        return CrudUtil.executeUpdate(sql,customer.getcID(),customer.getName(),customer.getAddress());
    }

    @Override
    public boolean update(Customer customer) throws Exception {
        String sql="UPDATE Customer SET name=?, address=? WHERE id=?";
        return CrudUtil.executeUpdate(sql,customer.getName(),customer.getAddress(),customer.getcID());
    }

    @Override
    public boolean delete(String id) throws Exception {
        String sql = "DELETE FROM Customer WHERE id=?";
        return CrudUtil.executeUpdate(sql,id);
    }

    @Override
    public Customer search(String id) throws Exception {
        String sql = "SELECT * FROM Customer where id=?";
        ResultSet rst = CrudUtil.executeQuery(sql,id);
        if (rst.next()){
            return new Customer(rst.getString(1),rst.getString(2),rst.getString(3));
        }

        return null;
    }

    @Override
    public ArrayList<Customer> getAll() throws Exception {
        String sql = "SELECT * FROM Customer";
        ResultSet rst = CrudUtil.executeQuery(sql);
        ArrayList<Customer> customers = new ArrayList<>();
        while (rst.next()){
            Customer customer = new Customer(rst.getString(1),
                    rst.getString(2),rst.getString(3));
            customers.add(customer);
        }

        return customers;
    }
}
