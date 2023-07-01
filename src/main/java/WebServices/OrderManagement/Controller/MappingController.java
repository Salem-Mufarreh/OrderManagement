package WebServices.OrderManagement.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashMap;
import java.util.Map;
/**
 * This class is the controller for the Mapping Management web service.
 *
 * The class provides methods for retrieving mappings.
 * It was used for testing purposes for swagger.
 */
@RestController
@RequestMapping("/mapping")
@ComponentScan
public class MappingController {

    private final RequestMappingHandlerMapping handlerMapping;

    @Autowired
    public MappingController(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    /**
     * This method returns all the mapping for the system.
     *
     * @return mapping for all links and apis.
     */
    @GetMapping("/mappings")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")},
            description = "Get All mapping links found in this project it was used for testing",
            responses = {
                @ApiResponse(
                        description = "Get all the mapping",
                        responseCode = "200",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                value = "{ \"[/v3/api-docs]\": \"org.springdoc.webmvc.api.OpenApiWebMvcResource#openapiJson(HttpServletRequest, String, Locale)\", \"[/v3/api-docs/swagger-config]\": \"org.springdoc.webmvc.ui.SwaggerConfigResource#openapiJson(HttpServletRequest)\", \"[/v3/api-docs.yaml]\": \"org.springdoc.webmvc.api.OpenApiWebMvcResource#openapiYaml(HttpServletRequest, String, Locale)\", \"[/swagger-ui.html]\": \"org.springdoc.webmvc.ui.SwaggerWelcomeWebMvc#redirectToUi(HttpServletRequest)\", \"[/error]\": \"org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController#errorHtml(HttpServletRequest, HttpServletResponse)\", \"[/api/customer/]\": \"WebServices.OrderManagement.Controller.CustomerController#GetAllCustomers()\", \"[/api/stock/{id}]\": \"WebServices.OrderManagement.Controller.StockController#GetStock(Long)\", \"[/api/user/signup]\": \"WebServices.OrderManagement.Controller.UserController#signup(UserEntity)\", \"[/api/transaction/order/{orderId}/product/{productId}]\": \"WebServices.OrderManagement.Controller.ProductOrderController#CreateTransaction(ProductOrderEntity, Long, Long)\", \"[/api/order/{customerId}]\": \"WebServices.OrderManagement.Controller.OrderController#CreateOrder(OrderEntity, Long)\", \"[/api/product/{id}]\": \"WebServices.OrderManagement.Controller.ProductController#DeleteProduct(Long)\", \"[/api/transaction/{id}]\": \"WebServices.OrderManagement.Controller.ProductOrderController#Delete(Long)\", \"[/api/customer/{id}]\": \"WebServices.OrderManagement.Controller.CustomerController#UpdateCustomer(Long, CustomerEntity)\", \"[/api/transaction/]\": \"WebServices.OrderManagement.Controller.ProductOrderController#GetAllTransactions()\", \"[/api/stock/]\": \"WebServices.OrderManagement.Controller.StockController#GetAll()\", \"[/mapping/mappings]\": \"WebServices.OrderManagement.Controller.MappingController#getMappings()\", \"[/api/product/]\": \"WebServices.OrderManagement.Controller.ProductController#CreateProduct(ProductEntity)\", \"[/api/user/login]\": \"WebServices.OrderManagement.Controller.UserController#login(UserEntity)\", \"[/api/order/{id}]\": \"WebServices.OrderManagement.Controller.OrderController#UpdateOrder(Long, OrderEntity)\", \"[/api/order/]\": \"WebServices.OrderManagement.Controller.OrderController#GetAllOrders()\" }"
                                        )
                                }
                        )
                )
            }
    )
    public Map<String, String> getMappings() {
        Map<String, String> mappings = new HashMap<>();

        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        for (RequestMappingInfo requestMappingInfo : handlerMethods.keySet()) {
            if (requestMappingInfo.getPatternsCondition() != null) {
                mappings.put(requestMappingInfo.getPatternsCondition().toString(), handlerMethods.get(requestMappingInfo).toString());
            }
        }

        return mappings;
    }
}
