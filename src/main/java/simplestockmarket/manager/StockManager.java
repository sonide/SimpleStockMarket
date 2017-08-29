package simplestockmarket.manager;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import simplestockmarket.model.Stock;

public class StockManager {
	private Map<String, Stock> stockCache = new ConcurrentHashMap<>();

	public boolean storeStock(String symbol, Stock stock) {
		return stockCache.put(symbol, stock) != null;
	}

	public Stock getStock(String symbol) {
		return stockCache.get(symbol);
	}

	public boolean isCacheEmpty() {
		return stockCache.isEmpty();
	}
	
	public Map<String, Stock> getCache(){
		return Collections.unmodifiableMap(stockCache);
	}
	
	public Collection<Stock> getAllStocks(){
		return Collections.unmodifiableCollection(stockCache.values());
	}
	
	public void clearCache() {
		stockCache.clear();
	}
}
