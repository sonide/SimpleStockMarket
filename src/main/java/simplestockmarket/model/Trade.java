package simplestockmarket.model;

import java.math.BigDecimal;

import simplestockmarket.data.Side;
/**
 * Representation of a Trade Object. 
 * All the attributes are assumed immutable after registering a trade
 * 
 * @author sonid
 *
 */
public final class Trade {
	
	private final Stock stock;
	private final long timestamp; 
	private final long quantity;
	private final Side side;
	private final BigDecimal price;
	
	public Trade(Stock stock,long timestamp, long qty, Side side, BigDecimal price) {
		this.stock=stock;
		this.side=side;
		this.timestamp=timestamp;
		this.quantity=qty; 
		this.price=price;
	}

	/**
	 * @return the stock
	 */
	public Stock getStock() {
		return stock;
	}

	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @return the quantity
	 */
	public long getQuantity() {
		return quantity;
	}

	/**
	 * @return the side
	 */
	public Side getSide() {
		return side;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Trade [stock=" + stock + ", timestamp=" + timestamp + ", quantity=" + quantity + ", side=" + side
				+ ", price=" + price + "]";
	}

}
