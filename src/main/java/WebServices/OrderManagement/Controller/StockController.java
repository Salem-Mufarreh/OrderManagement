package WebServices.OrderManagement.Controller;

import WebServices.OrderManagement.Entity.StockEntity;
import WebServices.OrderManagement.Services.ProductService;
import WebServices.OrderManagement.Services.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
/**
 * This class is the controller for the Stock Management web service.
 *
 * The class provides methods for creating, retrieving, updating, and deleting Stocks.
 */

@RestController
@RequestMapping("/api/stock")
public class StockController {
    private final StockService _StockService;
    private final ProductService _ProductService;

    public StockController(StockService stockService, ProductService productService) {
        _StockService = stockService;
        _ProductService = productService;
    }

    /**
     * This method retrieves stock information.
     *
     * @param id the id of the product.
     * @apiNote the api gets the stock information.
     * @exception ResponseStatusException return an exception to not found.
     * @return stock information.
     */
    @GetMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")},
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

    /**
     * This method retrieves A list of stocks.
     *
     * @apiNote the api get all available stocks.
     * @exception ResponseStatusException return an exception to conflict the list is empty.
     * @return A list of Socks information.
     */
    @GetMapping("/")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")},
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

    /**
     * This method creates a new stock.
     *
     * @param stock the stock information.
     * @apiNote the api creates a new stock information.
     * @exception ResponseStatusException return an exception.
     * @return the newly created stock.
     */
    @PostMapping("/")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")},
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

    /**
     * This method Updates stock information.
     *
     * @param id the id of the product.
     * @param stock the product information.
     * @apiNote the api gets the stock and updates it.
     * @exception ResponseStatusException return an exception to not found.
     * @return stock information.
     */
    @PutMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")},
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

    /**
     * This method deletes stock information.
     *
     * @param id the id of the product.
     * @apiNote the api gets the stock and deletes it.
     * @exception ResponseStatusException return an exception to not found.
     * @return delete confirmation message.
     */
    @DeleteMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")},
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
