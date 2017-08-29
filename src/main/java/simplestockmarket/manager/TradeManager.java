/**
 * 
 */
package simplestockmarket.manager;


import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import simplestockmarket.data.Side;
import simplestockmarket.model.Stock;
import simplestockmarket.model.Trade;

/**
 * This class will keep record of all the {@link Trade} 
 * 
 * @author sonid
 *
 */
public class TradeManager {
	/***
	 * Current Limitation : We can have 1 trade per TimeStamp : This done for simpler way to access to get last 15 mins trades
	 * 
	 * 
	 * Choice of ConcurrentSkipList is to allow time series storage of elements
	 * which will be faster for analytic at the cost of slow inserts 
	 */
	private ConcurrentNavigableMap<Long, Trade> tradeCache= new ConcurrentSkipListMap<>();
	
	
	/**
	 *  Stores the {@link Trade} in its cache
	 * @param stock
	 * @param qty
	 * @param side
	 * @param price
	 * @return The timestamp of the Trade just Stored.
	 */
	public long storeTrade(Stock stock,long qty, Side side, BigDecimal price){
		long timestamp=System.currentTimeMillis();
		
		//Update Stock Price for the registered trade. 
		stock.setPrice(price);
		
		tradeCache.put(timestamp, new Trade(stock, timestamp, qty ,side,price));
		return timestamp;
	}
	
	/**
	 * Returns the {@link Trade} for the timestamp else null;
	 * @param timestamp
	 * @return trade 
	 */
	public Trade getTrade(long timestamp){
		return tradeCache.get(timestamp);
	}
	
	/**
	 * Return a collection of {@link Trade} which were registered after the timestamp provided
	 * @param timestamp
	 * @return
	 */
	public Collection<Trade> getRecentTrades(Long timestamp){
		return Collections.unmodifiableCollection(tradeCache.tailMap(timestamp).values());
	}
	
	
	
	/**
	 *Empty the cache for Trade.  
	 */
	public void clearCache(){
		tradeCache.clear();
	}
}

