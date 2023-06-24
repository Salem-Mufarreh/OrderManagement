package WebServices.OrderManagement.Services;

import WebServices.OrderManagement.Entity.StockEntity;

import java.util.List;

public interface StockService {
    StockEntity CreateStock(StockEntity stock);
    StockEntity GetStockById(Long id);
    List<StockEntity> GetAllStocks();
    StockEntity UpdateStock(Long id, StockEntity stock);
    void DeleteStock (Long id);
}
