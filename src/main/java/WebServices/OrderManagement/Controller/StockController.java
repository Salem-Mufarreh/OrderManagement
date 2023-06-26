package WebServices.OrderManagement.Controller;

import WebServices.OrderManagement.Entity.ProductEntity;
import WebServices.OrderManagement.Entity.StockEntity;
import WebServices.OrderManagement.Services.ProductService;
import WebServices.OrderManagement.Services.StockService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockController {
    private final StockService _StockService;
    private final ProductService _ProductService;

    public StockController(StockService stockService, ProductService productService) {
        _StockService = stockService;
        _ProductService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity GetStock(@PathVariable Long id){
        try{
            StockEntity stock = _StockService.GetStockById(id);
            return ResponseEntity.ok(stock);
        }catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }
    @GetMapping("/")
    public ResponseEntity GetAll(){
        try {
            List<StockEntity> list = _StockService.GetAllStocks();
            return ResponseEntity.ok(list);
        }
        catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }

    @PostMapping("/")
    public ResponseEntity Create(@Valid @RequestBody StockEntity stock){
        try {
            return ResponseEntity.ok(_StockService.CreateStock(stock));
        }
        catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity UpdateStock(@PathVariable Long id,@Valid @RequestBody StockEntity stock){
        try {
            StockEntity saved = _StockService.UpdateStock(id,stock);
            return ResponseEntity.ok(saved);
        }
        catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }
    @PutMapping("/{id}/product/{productId}")
    public ResponseEntity AddStockProduct(@PathVariable Long id, @PathVariable Long productId){
        try {
            ProductEntity product = _ProductService.GetProductById(productId);
            StockEntity stock = _StockService.GetStockById(id);
            stock.setStockProduct(product);
            return ResponseEntity.ok(stock);
        }catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity Delete(@PathVariable Long id){
        try {
            _StockService.DeleteStock(id);
            return ResponseEntity.ok("Stock Was Deleted");
        }catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }

}
