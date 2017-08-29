/**
 * 
 */
package simplestockmarket.model;

import java.math.BigDecimal;

import simplestockmarket.data.StockType;

/**
 * Representation of a single Stock 
 * 
 * All attributes are assumed mutable during the lifecycle of a stock except its symbol.
 * 
 * 
 * @author sonid
 *
 */
public final class Stock {
	private final String symbol;
	private BigDecimal parValue;
	private BigDecimal lastDividend;
	private BigDecimal fixedDividendPct;
	private StockType stockType;
	private BigDecimal price;
	
	public Stock(String symbol, StockType stockType, BigDecimal lastDividend,
			BigDecimal fixedDividendPct, BigDecimal parValue, BigDecimal initialPrice){
		this.symbol=symbol;
		this.parValue=parValue;
		this.lastDividend=lastDividend;
		this.fixedDividendPct=fixedDividendPct;
		this.stockType=stockType;
		this.price=initialPrice;
	}

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @return the parValue
	 */
	public BigDecimal getParValue() {
		return parValue;
	}


	/**
	 * @param parValue the parValue to set
	 */
	public void setParValue(BigDecimal parValue) {
		this.parValue = parValue;
	}


	/**
	 * @return the lastDividend
	 */
	public BigDecimal getLastDividend() {
		return lastDividend;
	}


	/**
	 * @param lastDividend the lastDividend to set
	 */
	public void setLastDividend(BigDecimal lastDividend) {
		this.lastDividend = lastDividend;
	}


	/**
	 * @return the fixedDividendPct
	 */
	public BigDecimal getFixedDividendPct() {
		return fixedDividendPct;
	}


	/**
	 * @param fixedDividendPct the fixedDividendPct to set
	 */
	public void setFixedDividendPct(BigDecimal fixedDividendPct) {
		this.fixedDividendPct = fixedDividendPct;
	}


	/**
	 * @return the stockType
	 */
	public StockType getStockType() {
		return stockType;
	}


	/**
	 * @param stockType the stockType to set
	 */
	public void setStockType(StockType stockType) {
		this.stockType = stockType;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Stock [symbol=" + symbol + ", parValue=" + parValue + ", lastDividend=" + lastDividend
				+ ", fixedDividendPct=" + fixedDividendPct + ", stockType=" + stockType + ", price=" + price + "]";
	}
	

	
}
