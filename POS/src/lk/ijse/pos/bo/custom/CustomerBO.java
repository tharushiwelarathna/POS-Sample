package lk.ijse.pos.bo.custom;

import lk.ijse.pos.model.Customer;

import java.util.ArrayList;

public interface CustomerBO {
    boolean addCustomer(Customer customer) throws Exception;

    boolean deleteCustomer(String id) throws Exception;

    boolean updateCustomer(Customer customer) throws Exception;

    Customer searchCustomer(String id) throws Exception;

    ArrayList<Customer> getAllCustomers() throws Exception;
}
