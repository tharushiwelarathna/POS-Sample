package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.custom.CustomerBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.custom.CustomerDAO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.entity.Customer;

import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    //property injection
  // private CustomerDAO customerDAO=new CustomerDAOImpl();
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
   public boolean addCustomer(CustomerDTO customer) throws Exception{
        return customerDAO .add(new Customer(customer.getcID(),customer.getName(),customer.getAddress()));

    }
    @Override
    public boolean updateCustomer(CustomerDTO customer) throws Exception {
        return customerDAO.update(new Customer(customer.getcID(),customer.getName(),customer.getAddress()));

    }



    @Override
    public boolean deleteCustomer(String id) throws Exception {
        return customerDAO.delete(id);
    }



    @Override
    public CustomerDTO searchCustomer(String id) throws Exception {
        Customer search=customerDAO.search(id);
        return new CustomerDTO(search.getcID(),search.getName(),search.getAddress());

    }
    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws Exception {
        ArrayList<Customer>all= customerDAO.getAll();
        ArrayList<CustomerDTO>allCust= new ArrayList<>();
        for (Customer c:all){
            CustomerDTO dto=new CustomerDTO(c.getcID(),c.getName(),c.getAddress());
            allCust.add(dto);
        }
        return allCust;
    }


}
