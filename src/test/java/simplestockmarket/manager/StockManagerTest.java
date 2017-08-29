/**
 * 
 */
package simplestockmarket.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import simplestockmarket.model.Stock;
import simplestockmarket.util.TestUtil;

/**
 * @author sonid
 *
 */
public class StockManagerTest {

	private StockManager stkMgr= new StockManager();
	@Before
	public void setup(){
		stkMgr.clearCache();
		
	}
	
	@Test
	public void testStoreStock(){
		Stock stk= TestUtil.getCommonTestStock();
		stkMgr.storeStock(stk.getSymbol(), stk);
		assertEquals(stk, stkMgr.getStock(stk.getSymbol()));
		assertFalse(stkMgr.isCacheEmpty());
	}
	
	@Test
	public void testStoreStock_null(){
		Stock stk= TestUtil.getCommonTestStock();
		stkMgr.storeStock(null, stk);
		assertEquals(null, stkMgr.getStock(stk.getSymbol()));
		assertTrue(stkMgr.isCacheEmpty());
	}
	
	@Test
	public void testGetStock_null(){
		Stock stk= TestUtil.getCommonTestStock();
		stkMgr.storeStock(stk.getSymbol(), stk);
		assertEquals(null, stkMgr.getStock(null));
	}
	
	
	
}
