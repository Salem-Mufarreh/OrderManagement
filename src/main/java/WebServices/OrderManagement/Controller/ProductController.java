package WebServices.OrderManagement.Controller;

import WebServices.OrderManagement.Entity.ProductEntity;
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
/**
 * This class is the controller for the Product Management web service.
 *
 * The class provides methods for creating, retrieving, updating, and deleting Products.
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService _ProductService;

    public ProductController(ProductService productService) {
        _ProductService = productService;
    }

    /**
     * This method retrieve a list of all products.
     *
     * @apiNote the api gets the all products.
     * @exception ResponseStatusException return an exception to conflict the product table is empty.
     * @return A list of all products.
     */
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

    /**
     * This method retrieve a Product by id.
     *
     * @param id the id of the product.
     * @apiNote the api gets the product information.
     * @exception ResponseStatusException return an exception to not found.
     * @return Product information.
     */
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

    /**
     * This method creates a Product.
     *
     * @param product the product information.
     * @apiNote creates a new product in the database.
     * @exception ResponseStatusException return an exception to any error that it catches mostly conflict.
     * @return the new product information.
     */
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

    /**
     * This method Updates a product information.
     *
     * @param id the id of the product.
     * @param product the product information.
     * @apiNote the api gets the order and updates it.
     * @exception ResponseStatusException return an exception to not found.
     * @return Product information.
     */
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

    /**
     * This method deletes a product.
     *
     * @param id the id of the product.
     * @apiNote the api gets the product and deletes it.
     * @exception ResponseStatusException return an exception to not found.
     * @return delete confirmation message.
     */
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
