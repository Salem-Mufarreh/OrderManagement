package WebServices.OrderManagement.Controller;

import WebServices.OrderManagement.Entity.ProductEntity;
import WebServices.OrderManagement.Entity.ProductOrderEntity;
import WebServices.OrderManagement.Services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService _ProductService;

    public ProductController(ProductService productService) {
        _ProductService = productService;
    }

    @GetMapping("/")
    public ResponseEntity GetAllProducts(){
        try {
            List<ProductEntity> list = _ProductService.GetAllProducts();
            return ResponseEntity.ok(list);
        }catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity GetProduct(@PathVariable Long id){
        try {
            ProductEntity product = _ProductService.GetProductById(id);
            return ResponseEntity.ok(product);
        }catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }

    @PostMapping("/")
    public ResponseEntity CreateProduct(@RequestBody @Valid ProductEntity product){
        try {

            ProductEntity savedProduct = _ProductService.CreateProduct(product);
            return ResponseEntity.ok(savedProduct);
        }catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity UpdateProduct(@PathVariable Long id, @RequestBody @Valid ProductEntity product){
        try {
            ProductEntity newProduct = _ProductService.UpdateProduct(id,product);
            return ResponseEntity.ok(newProduct);
        }catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity DeleteProduct(@PathVariable Long id){
        try {
            _ProductService.DeleteProduct(id);
            return ResponseEntity.ok("product was deleted");
        }catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }
}
