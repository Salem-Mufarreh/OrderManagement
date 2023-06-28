package WebServices.OrderManagement.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mapping")
@ComponentScan
public class MappingController {

    private final RequestMappingHandlerMapping handlerMapping;

    @Autowired
    public MappingController(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @RequestMapping("/mappings")
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
