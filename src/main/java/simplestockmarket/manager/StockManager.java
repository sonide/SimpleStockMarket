package simplestockmarket.manager;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import simplestockmarket.model.Stock;

public class StockManager {
	private Map<String, Stock> stockCache = new ConcurrentHashMap<>();

	public void storeStock(String symbol, Stock stock) {
		if(null!=symbol){
			stockCache.put(symbol, stock);
		}
	}

	public Stock getStock(String symbol) {
		return null!=symbol?stockCache.get(symbol):null;
	}

	public boolean isCacheEmpty() {
		return stockCache.isEmpty();
	}
	
	public Collection<Stock> getAllStocks(){
		return Collections.unmodifiableCollection(stockCache.values());
	}
	
	public void clearCache() {
		stockCache.clear();
	}
}
