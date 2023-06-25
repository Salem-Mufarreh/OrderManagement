package WebServices.OrderManagement.Controller;

import WebServices.OrderManagement.Entity.CustomerEntity;
import WebServices.OrderManagement.Exceptions.ResourceNotFound;
import WebServices.OrderManagement.Services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService _CustomerService;

    public CustomerController(CustomerService customerService) {
        _CustomerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity GetCustomer(@PathVariable Long id){
        if (id != null){
            CustomerEntity customer = _CustomerService.GetCustomerById(id);
            if (customer != null){
                return ResponseEntity.ok(customer);
            }
            else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/")
    public ResponseEntity CreateCustomer(@RequestBody @Valid CustomerEntity customer){
        if (customer != null){
            try {
                CustomerEntity newCustomer = _CustomerService.CreateCustomer(customer);
                if (newCustomer != null) {
                    return ResponseEntity.ok(newCustomer);
                } else {
                    return ResponseEntity.notFound().build();
                }
            }catch (ResponseStatusException ex){
                return new ResponseEntity(ex.getBody(),HttpStatus.BAD_REQUEST);
            }
        }
        return ResponseEntity.ofNullable(customer);
    }
    @GetMapping("/")
    public ResponseEntity GetAllCustomers(){
        try {
            List<CustomerEntity> list = _CustomerService.GetAllCustomers();
            return ResponseEntity.ok(list);
        }
        catch (ResponseStatusException ex){

            return new ResponseEntity(ex.getBody(),(HttpStatusCode) ex.getStatusCode());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity UpdateCustomer(@PathVariable Long id, @RequestBody CustomerEntity customer){
        try {
            CustomerEntity updatedCustomer = _CustomerService.UpdateCustomer(id, customer);
            return ResponseEntity.ok(updatedCustomer);
        }
        catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity DeleteCustomer(@PathVariable Long id){
        try {
            _CustomerService.deleteCustomer(id);
            return ResponseEntity.ok("Deleted successfully");
        }catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }
}
