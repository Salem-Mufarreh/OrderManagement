package WebServices.OrderManagement.Controller;

import WebServices.OrderManagement.Entity.OrderEntity;
import WebServices.OrderManagement.Entity.ProductEntity;
import WebServices.OrderManagement.Entity.ProductOrderEntity;
import WebServices.OrderManagement.Services.CustomerService;
import WebServices.OrderManagement.Services.OrderService;
import WebServices.OrderManagement.Services.ProductOrderService;
import WebServices.OrderManagement.Services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/transaction")
public class ProductOrderController {
    private final ProductOrderService _ProductOrderService;
    private final ProductService _ProductService;
    private final OrderService _OrderService;
    public ProductOrderController(ProductOrderService productOrderService,  ProductService productService, OrderService orderService) {
        _ProductOrderService = productOrderService;
        _ProductService = productService;
        _OrderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity GetTransaction(@PathVariable Long id){
        try {
            ProductOrderEntity productOrder = _ProductOrderService.GetOrderById(id);
            return ResponseEntity.ok(productOrder);
        }catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }
    @GetMapping("/")
    public ResponseEntity GetAllTransactions(){
        try {
            List<ProductOrderEntity> list = _ProductOrderService.GetAllProductOrders();
            return ResponseEntity.ok(list);
        }catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }
    @PostMapping("/order/{orderId}/product/{productId}")
    public ResponseEntity CreateTransaction(@RequestBody @Valid ProductOrderEntity productOrder,
                                            @PathVariable Long orderId, @PathVariable Long productId){

        try {
            ProductEntity product = _ProductService.GetProductById(productId);
            OrderEntity order = _OrderService.GetOrderById(orderId);
            productOrder.setOrder(order);
            productOrder.setProduct(product);
            ProductOrderEntity saved = _ProductOrderService.CreateProductOrder(productOrder);
            return ResponseEntity.ok(saved);
        }catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity UpdateTransaction(@PathVariable Long id, @RequestBody @Valid ProductOrderEntity productOrder){
        try {
            ProductOrderEntity saved = _ProductOrderService.UpdateOrder(id,productOrder);
            return ResponseEntity.ok(saved);
        }catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity Delete(@PathVariable Long id){
        try {
            ProductOrderEntity productOrder = _ProductOrderService.GetOrderById(id);

            _ProductOrderService.DeleteOrder(id);
            return ResponseEntity.ok("Product_Order was deleted");
        }catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }
}
