package WebServices.OrderManagement.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
/**
 * This class is used to configure the OpenAPI / swagger v3.
 *
 * The class defines two responses:
 *
 * * `404 Not Found`: This response is returned when the requested resource is not found.
 * * `200 OK - Empty Body`: This response is returned when the requested resource is empty.
 *
 * The class also defines the security schema for swagger, which is of type `bearerAuth`.
 */

@OpenAPIDefinition
@Configuration
@io.swagger.v3.oas.annotations.security.SecurityScheme(type = SecuritySchemeType.HTTP, name = "bearerAuth", scheme = "bearer", bearerFormat = "JWT")
public class SwaggerConfig {
    @Bean
    public OpenAPI baseOpenAPI(){
        /** 404 not found **/
        ApiResponse notFoundApi = new ApiResponse().content(new Content()
                .addMediaType(MediaType.APPLICATION_JSON_VALUE,
                new io.swagger.v3.oas.models.media.MediaType()
                        .addExamples("default",new Example()
                        .value("{ \"type\": \"about:blank\", \"title\": \"Not Found\", \"status\": 404, \"detail\": \" Not Found\", \"instance\": \"Path\" }")))
        );
        /** 400 bad request **/
        ApiResponse emptyFields = new ApiResponse().content(new Content()
                .addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType()
                                .addExamples("default",new Example()
                                        .value("{ \"type\": \"about:blank\", \"title\": \"Bad Request\", \"status\": 400, \"detail\": \"empty field\", \"instance\": \"{path}\" }")))
        );
        Components components = new Components();
        components.addResponses("notFoundApi",notFoundApi);
        components.addResponses("emptyFields",emptyFields);

        return new OpenAPI().components(components).info(new Info().title("Order Management System APIs docs").version("1.0.0").description("spring doc\n the authentication code is below please copy and paste it to get access to the apis\n eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huQGV4YW1wbGUuY29tIiwiaWF0IjoxNjg4MDY3OTMxLCJleHAiOjE2ODgxNTQzMzF9.vJb8ZFE40uny34n3oRw7gyzTEQscPvG3RMJZJpyGsvA"));
    }
    @Bean
    public SecurityScheme jwtToken() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("Authorization")
                .in(SecurityScheme.In.HEADER);
    }





}
