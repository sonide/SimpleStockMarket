package simplestockmarket.analytic;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import simplestockmarket.exception.StockException;
import simplestockmarket.manager.StockManager;
import simplestockmarket.manager.TradeManager;
import simplestockmarket.model.Stock;
import simplestockmarket.model.Trade;

/**
 * @author sonid
 *
 */
public class StockAnalyticService {
	/**
	 * Assumption: 
	 * The precision of 5 is used in the calculation.
	 */
	private static final int PRECISION = 5;
    private static final MathContext MATH_CTX = new MathContext(5, RoundingMode.HALF_EVEN);
    
    private static final int MINUTES_15 = 1000*60*15;

    private final TradeManager tradeCache;
	private final StockManager stockCache;
	
	public StockAnalyticService(TradeManager tradeCahe,StockManager stockCache){
		this.stockCache=stockCache;
		this.tradeCache=tradeCahe;
	}
	
	
	/**
	 * This method computes the Dividend Yield for a {@link Stock} with the given Price
	 * It returns BigDecimal.ZERO if either Price or dividend is zero
	 * @param stock
	 * @param price
	 * @return
	 */
	public BigDecimal getDividendYield(Stock stock, BigDecimal price) {
		BigDecimal dividend = this.getDividend(stock);
		if (BigDecimal.ZERO.compareTo(price) >= 0 || BigDecimal.ZERO.compareTo(dividend) >= 0) {
			return BigDecimal.ZERO;
		}else {
			return dividend.divide(price,MATH_CTX).setScale(PRECISION, BigDecimal.ROUND_HALF_EVEN);
		}
	}
	
	/**
	 * This method computes the PE Ratio for a {@link Stock} with the given Price
	 * It returns BigDecimal.ZERO if either Price or dividend is zero
	 * @param stock
	 * @param price
	 * @return
	 */
	public BigDecimal getPERatio(Stock stock, BigDecimal price){
		BigDecimal dividend= this.getDividend(stock);
		
		if(BigDecimal.ZERO.compareTo(dividend)>=0||BigDecimal.ZERO.compareTo(price) >= 0){
			return BigDecimal.ZERO;
			// Subjective as at this point PE_Ratio is not valid by calculating it using Dividend ==0
			// Can throw a specific exception to denote invalid Dividend.
		}else{
			return price.divide(dividend,MATH_CTX).setScale(PRECISION, BigDecimal.ROUND_HALF_EVEN); 
		}
	}
	
	private BigDecimal getDividend(Stock stock){
		switch(stock.getStockType()) {
		case COMMON:
			return stock.getLastDividend().setScale(PRECISION, BigDecimal.ROUND_HALF_EVEN);
		case PREFERRED:
			return stock.getFixedDividendPct().divide(BigDecimal.valueOf(100.0),MATH_CTX)
					.multiply(stock.getParValue()).setScale(PRECISION, BigDecimal.ROUND_HALF_EVEN);
		default:
			return BigDecimal.ZERO;
		}
	}
	
	
	/**
	 * This retrieves the trades executed for a given stock in the last 15 minutes
	 * and calculates the Volume Weighted Average Price for the same
	 * 
	 * This returns zero if NO Trades have happened in last 15 minutes
	 * @param stock
	 * @return
	 */
	public BigDecimal get15MinutesVWAP(Stock stock){
		long currentTime=System.currentTimeMillis();
		long time15Minutesback=currentTime - MINUTES_15;
		
		Collection<Trade> trades=tradeCache.getRecentTrades(time15Minutesback);
		
		return trades.stream()
				.filter(trd->trd.getStock().getSymbol().equals(stock.getSymbol()))
				.map(trd -> new BigDecimal[]{trd.getPrice().multiply(BigDecimal.valueOf(trd.getQuantity())), BigDecimal.valueOf(trd.getQuantity())})
				.reduce((x, y) -> new BigDecimal[]{x[0].add(y[0]), x[1].add(y[1])})
		        .map(x-> x[0].divide(x[1],MATH_CTX).setScale(PRECISION, BigDecimal.ROUND_HALF_EVEN)).orElse(BigDecimal.ZERO);
		
	}
	
	/**
	 * @return
	 */
	public BigDecimal getGBCEAllStockIndex() {
		if (this.stockCache.isCacheEmpty()) {
			throw new StockException("Stock Cache Empty. Can't generate Index");
		}
		
		List<Double> stockPriceList=stockCache.getAllStocks()
				.stream()
				.map(stk -> stk.getPrice().doubleValue())
				.collect(Collectors.toList());
		
		return BigDecimal.valueOf(this.getGeometricMean(stockPriceList)).setScale(PRECISION, BigDecimal.ROUND_HALF_EVEN);
 	}
	
	/**
	 * Calculates the GeometricMean via the exp( 1/n (sum of logs) ) calculation.
	 * If any number is less than zero the GM is ZERO
	 *  
	 * */
	public double getGeometricMean(List<Double> numbers){
		double accumulate = 0;
		for(double price:numbers){
			if(price<= 0.0 ||price >= Double.POSITIVE_INFINITY){
				return 0.0;
			}
			accumulate=accumulate+Math.log(price);
		}
        accumulate= BigDecimal.valueOf(accumulate).divide(BigDecimal.valueOf(numbers.size()), MATH_CTX).doubleValue();
        
        return Math.exp(accumulate);
	}
	
}
