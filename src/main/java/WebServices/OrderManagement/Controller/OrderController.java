package WebServices.OrderManagement.Controller;

import WebServices.OrderManagement.Entity.CustomerEntity;
import WebServices.OrderManagement.Entity.OrderEntity;
import WebServices.OrderManagement.Services.CustomerService;
import WebServices.OrderManagement.Services.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService _OrderService;
    private final CustomerService _CustomerService;
    public OrderController(OrderService orderService, CustomerService customerService) {
        _OrderService = orderService;
        _CustomerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity GetOrder(@PathVariable Long id){
        try {
            OrderEntity order = _OrderService.GetOrderById(id);
            if (order != null){
                return ResponseEntity.ok(order);
            }
            else {
                return ResponseEntity.notFound().build();
            }
        }catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }
    @GetMapping("/")
    public ResponseEntity GetAllOrders(){
        try {
            List<OrderEntity> list = _OrderService.GetAllOrders();
            return ResponseEntity.ok(list);
        }catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }

    @PostMapping("/{customerId}")
    public ResponseEntity CreateOrder(@RequestBody @Valid OrderEntity order, @PathVariable Long customerId){
        try {
            CustomerEntity customer = _CustomerService.GetCustomerById(customerId);
            order.setCustomer(customer);
            OrderEntity newOrder = _OrderService.CreateOrder(order);
            if (newOrder != null){
                return ResponseEntity.ok(newOrder);
            }
            else{
                return ResponseEntity.badRequest().build();
            }
        }catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity UpdateOrder(@PathVariable Long id, @RequestBody @Valid OrderEntity order){
        try {
            OrderEntity updated = _OrderService.UpdateOrder(id,order);
            return ResponseEntity.ok(updated);
        }
        catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity DeleteOrder(@PathVariable Long id){
        try {
            _OrderService.DeleteOrder(id);
            return ResponseEntity.ok("Order Was Deleted");
        }
        catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }
}
