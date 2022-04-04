package carsharing.dao;

import carsharing.entity.Customer;

import java.util.List;

public interface CustomerDao {
    void addCustomer(Customer customer);
    List<Customer> getAllCustomers();
    void updateCustomerRentedCarId(Customer customer);
    Customer getCustomerById(int id);
}
