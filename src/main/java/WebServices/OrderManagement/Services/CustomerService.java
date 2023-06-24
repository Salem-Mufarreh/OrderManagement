package WebServices.OrderManagement.Services;

import WebServices.OrderManagement.Entity.CustomerEntity;

import java.util.List;

public interface CustomerService {
    CustomerEntity CreateCustomer(CustomerEntity customer);
    CustomerEntity GetCustomerById(Long id);
    List<CustomerEntity> GetAllCustomers();
    CustomerEntity UpdateCustomer(Long id, CustomerEntity customer);
    void deleteCustomer(Long id);
}
