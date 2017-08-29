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
		stkMgr.storeStock("TEA", new Stock("TEA", StockType.COMMON, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.valueOf(100.0),BigDecimal.valueOf(100.0)));
		stkMgr.storeStock("POP", new Stock("POP", StockType.COMMON, BigDecimal.valueOf(8.0), BigDecimal.ZERO, BigDecimal.valueOf(100.0),BigDecimal.valueOf(80.0)));
		stkMgr.storeStock("ALE", new Stock("ALE", StockType.COMMON, BigDecimal.valueOf(23.0), BigDecimal.ZERO, BigDecimal.valueOf(60.0),BigDecimal.valueOf(100.0)));
        stkMgr.storeStock("GIN", new Stock("GIN", StockType.PREFERRED, BigDecimal.valueOf(8.0), BigDecimal.valueOf(0.2), BigDecimal.valueOf(100.0),BigDecimal.valueOf(100.0)));
        stkMgr.storeStock("JOE", new Stock("JOE", StockType.COMMON, BigDecimal.valueOf(13.0), BigDecimal.ZERO, BigDecimal.valueOf(250.0),BigDecimal.valueOf(100.0)));
    }
	
	/**
	 * Gets a random {@link BigDecimal} between 0 to 10,000.
	 * 
	 * @return a {@link BigDecimal}
	 */
	public static BigDecimal getRandomBigDecimal() {

		return BigDecimal.valueOf(Math.random() * 10_000);
	}
	
	/**
	 * Gets a default {@link Stock} for tests.
	 * @return a default {@link Stock}
	 */
    public static Stock getCommonTestStock(){
    	return new Stock("TEST_COMMON", StockType.COMMON, BigDecimal.ONE, BigDecimal.ZERO,BigDecimal.TEN,BigDecimal.valueOf(2.0));
    			
    }
    
    /**
	 * Gets a default {@link Stock} for tests.
	 * @return a default {@link Stock}
	 */
    public static Stock getPreferredTestStock(){
    	return new Stock("TEST_PREFERRED", StockType.PREFERRED, BigDecimal.ONE, BigDecimal.TEN,BigDecimal.TEN,BigDecimal.valueOf(5.0));
    			
    }
	
}
