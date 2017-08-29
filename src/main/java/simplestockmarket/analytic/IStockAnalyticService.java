/**
 * 
 */
package simplestockmarket.analytic;

import java.math.BigDecimal;

import simplestockmarket.model.Stock;

/**
 * @author sonid
 *
 */
public interface IStockAnalyticService {

	public BigDecimal getDividendYield(Stock stock, BigDecimal price);
	
	public BigDecimal getPERatio(Stock stock, BigDecimal price);
	
	public BigDecimal get15MinutesVWAP(Stock stock);
	
	public BigDecimal getGBCEAllStockIndex(); 
}
