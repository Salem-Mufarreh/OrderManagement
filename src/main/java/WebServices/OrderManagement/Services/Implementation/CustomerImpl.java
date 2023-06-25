package WebServices.OrderManagement.Services.Implementation;

import WebServices.OrderManagement.Entity.CustomerEntity;
import WebServices.OrderManagement.Repositories.CustomerRepo;
import WebServices.OrderManagement.Services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class CustomerImpl implements CustomerService {
    private final CustomerRepo _customerRepo;

    public CustomerImpl(CustomerRepo customerRepo) {
        _customerRepo = customerRepo;
    }

    @Override
    public CustomerEntity CreateCustomer(CustomerEntity customer) {
        CustomerEntity newEntity = new CustomerEntity();
        if(checkIfEntityParametersNullOrEmpty(customer)){
           newEntity = _customerRepo.save(customer);
           return newEntity;
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"empty field");
    }

    @Override
    public CustomerEntity GetCustomerById(Long id) {
        CustomerEntity entity = new CustomerEntity();
        entity = _customerRepo.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer is not found"));
        if(entity != null){
            return entity;
        }
       return null;
    }

    @Override
    public List<CustomerEntity> GetAllCustomers() {
        try {
            List<CustomerEntity> entities = _customerRepo.findAll();
            return entities;
        }
        catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.CONFLICT,ex.getMessage());

        }
    }

    @Override
    public CustomerEntity UpdateCustomer(Long id, CustomerEntity customer) {
        CustomerEntity old = GetCustomerById(id);
        if (old != null){
            old.setBornAt(customer.getBornAt());
            old.setFirstName(customer.getFirstName());
            old.setLastName(customer.getLastName());

             customer = _customerRepo.save(old);
             return customer;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer id was not found");
    }

    @Override
    public void deleteCustomer(Long id) {
        if (id != null){
            CustomerEntity customer = GetCustomerById(id);
            if (customer != null){
                _customerRepo.delete(customer);
            }

        }
    }

    public boolean checkIfEntityParametersNullOrEmpty(CustomerEntity entity) {
        if (entity == null) {
            return false;
        }

        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(entity);

                if (value == null) {
                    return false;
                }

                if (value instanceof String && ((String) value).isBlank()) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}
