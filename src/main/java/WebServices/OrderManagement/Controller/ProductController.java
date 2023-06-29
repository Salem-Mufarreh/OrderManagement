package WebServices.OrderManagement.Controller;

import WebServices.OrderManagement.Entity.ProductEntity;
import WebServices.OrderManagement.Entity.ProductOrderEntity;
import WebServices.OrderManagement.Services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")},
            description = "Get Transaction by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "product was found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "[ { \"id\": 1, \"name\": \"HP\", \"reference\": \"Hp11\", \"price\": 20, \"vat\": 15, \"stockAble\": true } ]"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            ref = "notFoundApi"
                    )
            }
    )
    public ResponseEntity GetAllProducts(){
        try {
            List<ProductEntity> list = _ProductService.GetAllProducts();
            return ResponseEntity.ok(list);
        }catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }
    @GetMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")},
            description = "Get product by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "product was found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"id\": 1, \"name\": \"HP\", \"reference\": \"Hp11\", \"price\": 20, \"vat\": 15, \"stockAble\": true }"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                           ref = "notFoundApi"
                    )
            }
    )
    public ResponseEntity GetProduct(@PathVariable Long id){
        try {
            ProductEntity product = _ProductService.GetProductById(id);
            return ResponseEntity.ok(product);
        }catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }

    @PostMapping("/")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")},
            description = "Create new product ",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "product was found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"id\": 2, \"name\": \"Lenovo\", \"reference\": \"LL11\", \"price\": 22, \"vat\": 11, \"stockAble\": true }"
                                            )
                                    }
                            )
                    )
            }
    )
    public ResponseEntity CreateProduct(@RequestBody @Valid ProductEntity product){
        try {

            ProductEntity savedProduct = _ProductService.CreateProduct(product);
            return ResponseEntity.ok(savedProduct);
        }catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }

    @PutMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")},
            description = "update product",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "product was updated",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"id\": 2, \"name\": \"Lenovo\", \"reference\": \"LL11\", \"price\": 22, \"vat\": 11, \"stockAble\": true }"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "400",ref = "notFoundApi")
            }
    )
    public ResponseEntity UpdateProduct(@PathVariable Long id, @RequestBody @Valid ProductEntity product){
        try {
            ProductEntity newProduct = _ProductService.UpdateProduct(id,product);
            return ResponseEntity.ok(newProduct);
        }catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")},
            description = "Delete Product",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "product was deleted",
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
    public ResponseEntity DeleteProduct(@PathVariable Long id){
        try {
            _ProductService.DeleteProduct(id);
            return ResponseEntity.ok("product was deleted");
        }catch (ResponseStatusException ex){
            return new ResponseEntity(ex.getBody(),ex.getStatusCode());
        }
    }
}
