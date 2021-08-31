package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.custom.CustomerBO;
import lk.ijse.pos.dao.custom.CustomerDAO;
import lk.ijse.pos.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.pos.model.Customer;

import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    //property injection
   private CustomerDAO customerDAO=new CustomerDAOImpl();

    @Override
   public boolean addCustomer(Customer customer) throws Exception{
       return customerDAO.add(customer);
   }
    @Override
    public boolean updateCustomer(Customer customer) throws Exception {
        return customerDAO.update(customer);
    }
    @Override
    public boolean deleteCustomer(String id) throws Exception {
        return customerDAO.delete(id);
    }

    @Override
    public Customer searchCustomer(String id) throws Exception {
        return customerDAO.search(id);
    }
    @Override
    public ArrayList<Customer> getAllCustomers() throws Exception {
        return customerDAO.getAll();
    }


}
