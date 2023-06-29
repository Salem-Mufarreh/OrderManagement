package WebServices.OrderManagement.Controller;

import WebServices.OrderManagement.Entity.OrderEntity;
import WebServices.OrderManagement.Entity.ProductEntity;
import WebServices.OrderManagement.Entity.ProductOrderEntity;
import WebServices.OrderManagement.Services.CustomerService;
import WebServices.OrderManagement.Services.OrderService;
import WebServices.OrderManagement.Services.ProductOrderService;
import WebServices.OrderManagement.Services.ProductService;
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
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")},
            description = "Get Product_Order by Id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product_Order was found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"quantity\": 1, \"price\": 35, \"vat\": 12, \"id\": 0, \"product\": { \"id\": 1, \"name\": \"HP\", \"reference\": \"Hp11\", \"price\": 20, \"vat\": 15, \"stockAble\": true, \"stockEntries\": [ { \"id\": 1, \"quantity\": 2, \"updatedAt\": \"2023-06-24T03:00:00\" } ] } }"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "404",ref = "notFoundApi")
            }
    )
    public ResponseEntity GetTransaction(@PathVariable Long id){
        try {
            ProductOrderEntity productOrder = _ProductOrderService.GetOrderById(id);
            return ResponseEntity.ok(productOrder);
        }catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }
    @GetMapping("/")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")},
            description = "Get All product_Orders",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "[ { \"quantity\": 1, \"price\": 35, \"vat\": 12, \"id\": 0, \"product\": { \"id\": 1, \"name\": \"HP\", \"reference\": \"Hp11\", \"price\": 20, \"vat\": 15, \"stockAble\": true, \"stockEntries\": [ { \"id\": 1, \"quantity\": 2, \"updatedAt\": \"2023-06-24T03:00:00\" } ] } }, { \"quantity\": 1, \"price\": 35, \"vat\": 12, \"id\": 0, \"product\": { \"id\": 1, \"name\": \"HP\", \"reference\": \"Hp11\", \"price\": 20, \"vat\": 15, \"stockAble\": true, \"stockEntries\": [ { \"id\": 1, \"quantity\": 2, \"updatedAt\": \"2023-06-24T03:00:00\" } ] } } ]"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "404",ref = "notFoundApi")
            }
    )
    public ResponseEntity GetAllTransactions(){
        try {
            List<ProductOrderEntity> list = _ProductOrderService.GetAllProductOrders();
            return ResponseEntity.ok(list);
        }catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }
    @PostMapping("/order/{orderId}/product/{productId}")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")},
            description = "Create Product_Order",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Create Product_Order",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"id\": 0, \"quantity\": 1, \"price\": 35, \"vat\": 12, \"product\": { \"id\": 1, \"name\": \"HP\", \"reference\": \"Hp11\", \"price\": 20, \"vat\": 15, \"stockAble\": true }, \"order\": { \"id\": 1, \"customer\": { \"id\": 1, \"firstName\": \"Salem\", \"lastName\": \"Mufarreh\", \"bornAt\": \"2001-02-21\" }, \"orderedAt\": \"2023-07-25T03:00:00\" } }"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "404",ref = "notFoundApi"),
                    @ApiResponse(ref = "emptyFields",responseCode = "400")
            }
    )
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
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")},
            description = "Update Product_Order",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "product_order was updated",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"id\": 0, \"quantity\": 2, \"price\": 35, \"vat\": 12, \"product\": { \"id\": 1, \"name\": \"HP\", \"reference\": \"Hp11\", \"price\": 20, \"vat\": 15, \"stockAble\": true }, \"order\": { \"id\": 1, \"customer\": { \"id\": 1, \"firstName\": \"Salem\", \"lastName\": \"Mufarreh\", \"bornAt\": \"2001-02-21\" }, \"orderedAt\": \"2023-07-25T03:00:00\" } }"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "404",ref = "notFoundApi")
            }
    )
    public ResponseEntity UpdateTransaction(@PathVariable Long id, @RequestBody @Valid ProductOrderEntity productOrder){
        try {
            ProductOrderEntity saved = _ProductOrderService.UpdateOrder(id,productOrder);
            return ResponseEntity.ok(saved);
        }catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }
    @DeleteMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")},
            description = "Delete Product_order",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "product_order was deleted",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "product was deleted"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "404",ref = "notFoundApi")
            }
    )
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
