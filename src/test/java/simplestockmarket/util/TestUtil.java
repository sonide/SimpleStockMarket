/**
 * 
 */
package simplestockmarket.util;

import java.math.BigDecimal;

import simplestockmarket.data.StockType;
import simplestockmarket.manager.StockManager;
import simplestockmarket.model.Stock;

/**
 * @author sonid
 *
 */
public class TestUtil {

	public static void loadSampleData(StockManager stkMgr){
		stkMgr.storeStock("TEA", new Stock("TEA", StockType.COMMON, BigDecimal.ZERO, BigDecimal.ZERO, new BigDecimal(100.0),new BigDecimal(100.0)));
		stkMgr.storeStock("POP", new Stock("POP", StockType.COMMON, new BigDecimal(8.0), BigDecimal.ZERO, new BigDecimal(100.0),new BigDecimal(80.0)));
		stkMgr.storeStock("ALE", new Stock("ALE", StockType.COMMON, new BigDecimal(23.0), BigDecimal.ZERO, new BigDecimal(60.0),new BigDecimal(100.0)));
        stkMgr.storeStock("GIN", new Stock("GIN", StockType.PREFERRED, new BigDecimal(8.0), new BigDecimal(0.2), new BigDecimal(100.0),new BigDecimal(100.0)));
        stkMgr.storeStock("JOE", new Stock("JOE", StockType.COMMON, new BigDecimal(13.0), BigDecimal.ZERO, new BigDecimal(250.0),new BigDecimal(100.0)));
    }
	
	/**
	 * Gets a random {@link BigDecimal} between 0 to 100,00.
	 * 
	 * @return a {@link BigDecimal}
	 */
	public static BigDecimal getRandomBigDecimal() {

		return new BigDecimal(Math.random() * 10_000);
	}
	
	/**
	 * Gets a default {@link Stock} for tests.
	 * @return a default {@link Stock}
	 */
    public static Stock getCommonTestStock(){
    	return new Stock("TEST_COMMON", StockType.COMMON, BigDecimal.ONE, BigDecimal.ZERO,BigDecimal.TEN,new BigDecimal(2.0));
    			
    }
    
    /**
	 * Gets a default {@link Stock} for tests.
	 * @return a default {@link Stock}
	 */
    public static Stock getPreferredTestStock(){
    	return new Stock("TEST_PREFERRED", StockType.PREFERRED, BigDecimal.ONE, BigDecimal.TEN,BigDecimal.TEN,new BigDecimal(5.0));
    			
    }
	
}
