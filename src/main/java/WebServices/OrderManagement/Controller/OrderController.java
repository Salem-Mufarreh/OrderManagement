package WebServices.OrderManagement.Controller;

import WebServices.OrderManagement.Entity.CustomerEntity;
import WebServices.OrderManagement.Entity.OrderEntity;
import WebServices.OrderManagement.Services.CustomerService;
import WebServices.OrderManagement.Services.OrderService;
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
 * This class is the controller for the Order Management web service.
 *
 * The class provides methods for creating, retrieving, updating, and deleting Orders.
 */

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService _OrderService;
    private final CustomerService _CustomerService;
    public OrderController(OrderService orderService, CustomerService customerService) {
        _OrderService = orderService;
        _CustomerService = customerService;
    }

    /**
     * This method returns order by id.
     *
     * @param id the id of the order.
     * @return Order information.
     */
    @GetMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")},
            description = "Get Orders By id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "order was found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"id\": 1, \"customer\": { \"id\": 1, \"firstName\": \"Salem\", \"lastName\": \"Mufarreh\", \"bornAt\": \"2001-02-21\" }, \"orderedAt\": \"2023-07-24T03:00:00\", \"productOrders\": [ { \"quantity\": 1, \"price\": 35, \"vat\": 12, \"id\": 0, \"product\": { \"id\": 1, \"name\": \"HP\", \"reference\": \"Hp11\", \"price\": 20, \"vat\": 15, \"stockAble\": true } } ] }"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "404",ref = "notFoundApi")
            }
    )
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
    /**
     * This method returns a list of all available orders.
     *
     * @return a list of orders information.
     */
    @GetMapping("/")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")},
            description = "Get All Orders",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Display Orders",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "[ { \"id\": 1, \"customer\": { \"id\": 1, \"firstName\": \"Salem\", \"lastName\": \"Mufarreh\", \"bornAt\": \"2001-02-21\" }, \"orderedAt\": \"2023-06-24T03:00:00\", \"productOrders\": [] } ]"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "404",ref = "notFoundApi")
            }
    )
    public ResponseEntity GetAllOrders(){
        try {
            List<OrderEntity> list = _OrderService.GetAllOrders();
            return ResponseEntity.ok(list);
        }catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }

    /**
     * This method creates an order.
     *
     * @param customerId the id of the customer.
     * @param order the order information.
     * @apiNote the api gets the customer and creates an order for him and return the order information.
     * @exception ResponseStatusException return an exception to not found.
     * @return order information.
     */
    @PostMapping("/{customerId}")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")},
            description = "Create Order",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Create New Order",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"id\": 9, \"customer\": { \"id\": 1, \"firstName\": \"Salem\", \"lastName\": \"Mufarreh\", \"bornAt\": \"2001-02-21\" }, \"orderedAt\": \"2023-07-24T03:00:00\" }"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "404",ref = "notFoundApi"),
                    @ApiResponse(responseCode = "400",ref = "emptyFields")

            }
    )
    public ResponseEntity CreateOrder(@RequestBody @Valid OrderEntity order, @PathVariable Long customerId){
        try {
            CustomerEntity customer = _CustomerService.GetCustomerById(customerId);
            order.setCustomer(customer);
            //order.setCustomer(customer);
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

    /**
     * This method creates an order.
     *
     * @param id the id of the customer.
     * @param  order the order information.
     * @apiNote the api gets the customer and updates the order information.
     * @exception ResponseStatusException return an exception to not found for customer or Order.
     * @return order information.
     */
    @PutMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")},
            description = "Delete Product",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Order was Updated",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"id\": 1, \"customer\": { \"id\": 1, \"firstName\": \"Salem\", \"lastName\": \"Mufarreh\", \"bornAt\": \"2001-02-21\" }, \"orderedAt\": \"2023-07-25T03:00:00\", \"productOrders\": [ { \"quantity\": 1, \"price\": 35, \"vat\": 12, \"id\": 0, \"product\": { \"id\": 1, \"name\": \"HP\", \"reference\": \"Hp11\", \"price\": 20, \"vat\": 15, \"stockAble\": true } } ] }"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "404",ref = "notFoundApi")
            }
    )
    public ResponseEntity UpdateOrder(@PathVariable Long id, @RequestBody @Valid OrderEntity order){
        try {
            OrderEntity updated = _OrderService.UpdateOrder(id,order);
            return ResponseEntity.ok(updated);
        }
        catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }

    /**
     * This method creates an order.
     *
     * @param id the id of the Order.
     * @apiNote the api gets the order and deletes it.
     * @exception ResponseStatusException return an exception to not found.
     * @return delete confirmation message.
     */
    @DeleteMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")},
            description = "Delete Order",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Order was deleted",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "Order was deleted"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "404",ref = "notFoundApi")
            }
    )
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
