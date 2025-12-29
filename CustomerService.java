package service;

import dao.CustomerDAO;
import model.Customer;
import java.util.List;

public class CustomerService {
    private CustomerDAO customerDAO;
    
    public CustomerService() {
        this.customerDAO = new CustomerDAO();
    }
    
    public boolean addCustomer(Customer customer) {
        return customerDAO.create(customer);
    }
    
    public Customer getCustomer(int id) {
        return customerDAO.read1(id);
    }
    
    public List<Customer> getAllCustomers() {
        return customerDAO.readAll();
    }
    
    public boolean updateCustomer(Customer customer) {
        return customerDAO.update(customer);
    }
    
    public boolean deleteCustomer(int id) {
        return customerDAO.delete(id);
    }
    
    public List<Customer> searchCustomers(String keyword) {
        return customerDAO.searchByName(keyword);
    }
}
