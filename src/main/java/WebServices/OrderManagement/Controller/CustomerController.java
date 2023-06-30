package WebServices.OrderManagement.Controller;

import WebServices.OrderManagement.Entity.CustomerEntity;
import WebServices.OrderManagement.Services.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService _CustomerService;

    public CustomerController(CustomerService customerService) {
        _CustomerService = customerService;
    }

    @GetMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")},
            description = "Get customer by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "customer was found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"id\": 1, \"firstName\": \"Salem\", \"lastName\": \"Mufarreh\", \"bornAt\": \"2001-02-21\" }"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "404",ref = "notFoundApi")
            }
    )
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
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")},
            description = "Create Customer",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Customers was created",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"id\": 2, \"firstName\": \"test1\", \"lastName\": \"test2\", \"bornAt\": \"2001-02-21\" }"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "404",ref = "notFoundApi")
            }
    )
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
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")},
            description = "Get all customers",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "list all of customers",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "[ { \"id\": 1, \"firstName\": \"Salem\", \"lastName\": \"Mufarreh\", \"bornAt\": \"2001-02-21\" }, { \"id\": 2, \"firstName\": \"test1\", \"lastName\": \"test2\", \"bornAt\": \"2001-02-21\" }, { \"id\": 3, \"firstName\": \"test1\", \"lastName\": \" \", \"bornAt\": \"2001-02-21\" }, { \"id\": 4, \"firstName\": \"test1\", \"lastName\": \" \", \"bornAt\": \"2001-02-21\" } ]"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "404",ref = "notFoundApi")
            }
    )
    public ResponseEntity GetAllCustomers(){
        try {
            List<CustomerEntity> list = _CustomerService.GetAllCustomers();
            return ResponseEntity.ok(list);
        }
        catch (ResponseStatusException ex){

            return new ResponseEntity(ex.getBody(), ex.getStatusCode());
        }
    }
    @PutMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")},
            description = "Update customer information",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "customer information was updated",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"id\": 4, \"firstName\": \"test1\", \"lastName\": \"tobeDeleted\", \"bornAt\": \"2001-02-21\" }"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "404",ref = "notFoundApi")
            }
    )
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
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")},
            description = "delete customer",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "customer was deleted",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "customer was deleted"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "404",ref = "notFoundApi")
            }
    )
    public ResponseEntity DeleteCustomer(@PathVariable Long id){
        try {
            _CustomerService.deleteCustomer(id);
            return ResponseEntity.ok("Deleted successfully");
        }catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }
}
