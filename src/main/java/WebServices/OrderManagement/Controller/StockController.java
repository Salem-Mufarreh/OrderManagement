package WebServices.OrderManagement.Controller;

import WebServices.OrderManagement.Entity.ProductEntity;
import WebServices.OrderManagement.Entity.StockEntity;
import WebServices.OrderManagement.Services.ProductService;
import WebServices.OrderManagement.Services.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(
            description = "Get Product Stock",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product Stock was found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"id\": 1, \"StockProduct\": { \"id\": 1, \"name\": \"HP\", \"reference\": \"Hp11\", \"price\": 20, \"vat\": 15, \"stockAble\": true }, \"quantity\": 2, \"updatedAt\": \"2023-06-24T03:00:00\", \"stockProduct\": { \"id\": 1, \"name\": \"HP\", \"reference\": \"Hp11\", \"price\": 20, \"vat\": 15, \"stockAble\": true } }"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "404",ref = "notFoundApi")
            }
    )
    public ResponseEntity GetStock(@PathVariable Long id){
        try{
            StockEntity stock = _StockService.GetStockById(id);
            return ResponseEntity.ok(stock);
        }catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }
    @GetMapping("/")
    @Operation(
            description = "Get All Stocks Available",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of Stocks",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "[ { \"id\": 1, \"StockProduct\": { \"id\": 1, \"name\": \"HP\", \"reference\": \"Hp11\", \"price\": 20, \"vat\": 15, \"stockAble\": true }, \"quantity\": 2, \"updatedAt\": \"2023-06-24T03:00:00\", \"stockProduct\": { \"id\": 1, \"name\": \"HP\", \"reference\": \"Hp11\", \"price\": 20, \"vat\": 15, \"stockAble\": true } } ]"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "404",ref = "notFoundApi")
            }
    )
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
    @Operation(
            description = "Create new Stock",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "stock was created",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"id\": 2, \"StockProduct\": { \"id\": 1, \"name\": \"HP\", \"reference\": \"Hp11\", \"price\": 20, \"vat\": 15, \"stockAble\": true }, \"quantity\": 2, \"updatedAt\": \"2023-06-24T03:00:00\", \"stockProduct\": { \"id\": 1, \"name\": \"HP\", \"reference\": \"Hp11\", \"price\": 20, \"vat\": 15, \"stockAble\": true } }"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "400",ref = "emptyFields")
            }
    )
    public ResponseEntity Create(@Valid @RequestBody StockEntity stock){
        try {
            return ResponseEntity.ok(_StockService.CreateStock(stock));
        }
        catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }
    @PutMapping("/{id}")
    @Operation(
            description = "Update Stock for product",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "stock was updated",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"id\": 1, \"StockProduct\": { \"id\": 3, \"name\": \"Lenovo\", \"reference\": \"LL11\", \"price\": 22, \"vat\": 11, \"stockAble\": true }, \"quantity\": 2, \"updatedAt\": \"2023-06-24T03:00:00\", \"stockProduct\": { \"id\": 3, \"name\": \"Lenovo\", \"reference\": \"LL11\", \"price\": 22, \"vat\": 11, \"stockAble\": true } }"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "404",ref = "notFoundApi")
            }
    )
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
    @Operation(
            description = "Delete stock",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "stock was deleted",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "stock was deleted"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "404",ref = "notFoundApi")
            }
    )
    public ResponseEntity Delete(@PathVariable Long id){
        try {
            _StockService.DeleteStock(id);
            return ResponseEntity.ok("Stock Was Deleted");
        }catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }

}
