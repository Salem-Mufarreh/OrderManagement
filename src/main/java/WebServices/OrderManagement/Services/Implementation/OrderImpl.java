package WebServices.OrderManagement.Services.Implementation;

import WebServices.OrderManagement.Entity.OrderEntity;
import WebServices.OrderManagement.Repositories.OrderRepo;
import WebServices.OrderManagement.Services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class OrderImpl implements OrderService {
    private final OrderRepo _OrderRepo;

    public OrderImpl(OrderRepo orderRepo) {
        _OrderRepo = orderRepo;
    }

    @Override
    public OrderEntity CreateOrder(OrderEntity order) {
        if(checkIfEntityParametersNullOrEmpty(order)){
            OrderEntity newOrder = _OrderRepo.save(order);
            return newOrder;
        }
        return null;
    }

    @Override
    public OrderEntity GetOrderById(Long id) {
        OrderEntity order = _OrderRepo.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Order Was Not Found"));
        if (order != null){
            return order;
        }
        return null;
    }

    @Override
    public List<OrderEntity> GetAllOrders() {
        List<OrderEntity> list = _OrderRepo.findAll();
        if (list != null){
            return list;
        }
        return null;
    }

    @Override
    public OrderEntity UpdateOrder(Long id, OrderEntity order) {
        OrderEntity old = GetOrderById(id);
        if(old != null){
            old.setOrderedAt(order.getOrderedAt());
            old.setCustomer(order.getCustomer());
            order = _OrderRepo.save(old);
            return order;
        }
        return null;
    }

    @Override
    public void DeleteOrder(Long id) {
        OrderEntity order = GetOrderById(id);
        if (order != null){
            _OrderRepo.delete(order);
        }
    }
    public boolean checkIfEntityParametersNullOrEmpty(OrderEntity entity) {
        if (entity == null) {
            return false;
        }

        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(entity);

                if (value == null) {
                    return false;
                }

                if (value instanceof String && ((String) value).isEmpty()) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

}
