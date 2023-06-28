package WebServices.OrderManagement.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

@OpenAPIDefinition
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI baseOpenAPI(){
        ApiResponse notFoundApi = new ApiResponse().content(new Content()
                .addMediaType(MediaType.APPLICATION_JSON_VALUE,
                new io.swagger.v3.oas.models.media.MediaType()
                        .addExamples("default",new Example()
                        .value("{ \"type\": \"about:blank\", \"title\": \"Not Found\", \"status\": 404, \"detail\": \" Not Found\", \"instance\": \"Path\" }")))
        );
        ApiResponse emptyFields = new ApiResponse().content(new Content()
                .addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType()
                                .addExamples("default",new Example()
                                        .value("{ \"type\": \"about:blank\", \"title\": \"Bad Request\", \"status\": 400, \"detail\": \"empty field\", \"instance\": \"{path}\" }")))
        );
        Components components = new Components();
        components.addResponses("notFoundApi",notFoundApi);
        components.addResponses("emptyFields",emptyFields);

        return new OpenAPI().components(components).info(new Info().title("Order Management System APIs docs").version("1.0.0").description("spring doc"));
    }


}
