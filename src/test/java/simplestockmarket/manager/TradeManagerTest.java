/**
 * 
 */
package simplestockmarket.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import simplestockmarket.data.Side;
import simplestockmarket.model.Stock;
import simplestockmarket.model.Trade;
import simplestockmarket.util.TestUtil;

/**
 * @author sonid
 *
 */
public class TradeManagerTest {
	private TradeManager trdMgr= new TradeManager();
	
	@Before
	public void setup(){
		trdMgr.clearCache();
	}
	
	@Test
	public void testStoreTrade(){
		Stock stock=TestUtil.getCommonTestStock();
		long tradeId=trdMgr.storeTrade(stock, 10l, Side.SELL, BigDecimal.valueOf(100));
	
		Trade trade = trdMgr.getTrade(tradeId);
		
		assertNotNull(trade);
		assertEquals(10l, trade.getQuantity());
		assertEquals(Side.SELL, trade.getSide());
		assertEquals(BigDecimal.valueOf(100), trade.getPrice());
		assertEquals(stock, trade.getStock());
	}
	
	
	@Test
	public void testStoreTrade_unknownTradeId(){
		Stock stock=TestUtil.getCommonTestStock();
		trdMgr.storeTrade(stock, 10l, Side.SELL, BigDecimal.valueOf(100));
		Trade trade = trdMgr.getTrade(1l);
		
		assertNull(trade);
		
	}
	
	@Test
	public void testGetRecentTrade(){
		Stock stock=TestUtil.getCommonTestStock();
		long tradeId=trdMgr.storeTrade(stock, 10l, Side.SELL, BigDecimal.valueOf(100));
	
		Collection<Trade> trades = trdMgr.getRecentTrades(tradeId-500l);
		
		assertNotNull(trades);
		assertFalse(trades.isEmpty());
		assertTrue(trades.contains(trdMgr.getTrade(tradeId)));
		
	}
	
	@Test
	public void testGetRecentTrade_emptycache(){
		//Don't register any trades
		Collection<Trade> trades = trdMgr.getRecentTrades(500l);
		assertNotNull(trades);
		assertTrue(trades.isEmpty());
	}
}
