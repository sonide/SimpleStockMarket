/**
 * 
 */
package simplestockmarket.analytic;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simplestockmarket.data.Side;
import simplestockmarket.exception.StockException;
import simplestockmarket.manager.StockManager;
import simplestockmarket.manager.TradeManager;
import simplestockmarket.model.Stock;
import simplestockmarket.util.TestUtil;

/**
 * @author sonid
 *
 */
public class StockAnalyticServiceTest {

	private static final double DELTA = 0.001;
	private StockManager stkMgr=new StockManager();;
	private TradeManager trdMgr= new TradeManager();
    private StockAnalyticService sas=new StockAnalyticService(trdMgr,stkMgr);
	
	@Before
	public void setup(){
	 Stock common=	TestUtil.getCommonTestStock();
	 Stock preferred= TestUtil.getPreferredTestStock();
	  stkMgr.storeStock(common.getSymbol(), common);
	  stkMgr.storeStock(preferred.getSymbol(), preferred);
	}
	
	@After
	public void tearDown(){
		stkMgr.clearCache();
		trdMgr.clearCache();
	}
	
	//DividendYield
	
	@Test
	public void testGetDividendYield_zeroLastDivided(){
		Stock stock=stkMgr.getStock(TestUtil.getCommonTestStock().getSymbol());
		stock.setLastDividend(BigDecimal.ZERO);
		
		assertEquals(BigDecimal.ZERO.doubleValue(), sas.getDividendYield(stock, BigDecimal.valueOf(10.0)).doubleValue(), DELTA);
	}
	
	@Test
	public void testGetDividendYield_PositiveLastDivided(){
		Stock stock=stkMgr.getStock(TestUtil.getCommonTestStock().getSymbol());
		stock.setLastDividend(BigDecimal.TEN);
		
		assertEquals(1.0, sas.getDividendYield(stock, BigDecimal.valueOf(10.0)).doubleValue(), DELTA);
	}
	
	@Test
	public void testGetDividendYield_ZeroFixedDivided_Preferred(){
		Stock stock=stkMgr.getStock(TestUtil.getPreferredTestStock().getSymbol());
		stock.setFixedDividendPct(BigDecimal.ZERO);
		
		assertEquals(BigDecimal.ZERO.doubleValue(), sas.getDividendYield(stock, BigDecimal.valueOf(10.0)).doubleValue(), DELTA);
	}
	
	@Test
	public void testGetDividendYield_PositiveFixedDivided_Preferred(){
		Stock stock=stkMgr.getStock(TestUtil.getPreferredTestStock().getSymbol());
		stock.setFixedDividendPct(BigDecimal.TEN);
		
		assertEquals(0.1, sas.getDividendYield(stock, BigDecimal.valueOf(10.0)).doubleValue(), DELTA);
	}
	
	// PE_Ratio 
	
	@Test
	public void testGetPE_Ratio_zeroLastDivided(){
		Stock stock=stkMgr.getStock(TestUtil.getCommonTestStock().getSymbol());
		stock.setLastDividend(BigDecimal.ZERO);
		
		assertEquals(BigDecimal.ZERO.doubleValue(), sas.getPERatio(stock, BigDecimal.valueOf(10.0)).doubleValue(), DELTA);
	}
	
	@Test
	public void testGetPE_Ratio_PositiveLastDivided(){
		Stock stock=stkMgr.getStock(TestUtil.getCommonTestStock().getSymbol());
		stock.setLastDividend(BigDecimal.TEN);
		
		assertEquals(1.0, sas.getPERatio(stock, BigDecimal.valueOf(10.0)).doubleValue(), DELTA);
	}
	
	@Test
	public void testGetPE_Ratio_ZeroFixedDivided_Preferred(){
		Stock stock=stkMgr.getStock(TestUtil.getPreferredTestStock().getSymbol());
		stock.setFixedDividendPct(BigDecimal.ZERO);
		
		assertEquals(BigDecimal.ZERO.doubleValue(), sas.getPERatio(stock, BigDecimal.valueOf(10.0)).doubleValue(), DELTA);
	}
	
	@Test
	public void testGetPE_Ratio_NegativeFixedDivided_Preferred(){
		Stock stock=stkMgr.getStock(TestUtil.getPreferredTestStock().getSymbol());
		stock.setFixedDividendPct(BigDecimal.valueOf(-10.0));
		
		assertEquals(0.0, sas.getPERatio(stock, BigDecimal.valueOf(10.0)).doubleValue(), DELTA);
	}
	
	
	@Test
	public void testGetPE_Ratio_PositiveFixedDivided_Preferred(){
		Stock stock=stkMgr.getStock(TestUtil.getPreferredTestStock().getSymbol());
		stock.setFixedDividendPct(BigDecimal.TEN);
		
		assertEquals(10.0, sas.getPERatio(stock, BigDecimal.valueOf(10.0)).doubleValue(), DELTA);
	}
	
	
	//15minVWAP
	
	@Test
	public void testGet15MinutesVWAP() {
		Stock stock = TestUtil.getCommonTestStock();
		try {
			for (int i = 1; i <= 5; i++) {
				trdMgr.storeTrade(stock, 10 * i, Side.BUY, BigDecimal.valueOf(i));
				Thread.sleep(10);
				trdMgr.storeTrade(stock, 10 * i, Side.SELL, BigDecimal.valueOf(i));
				Thread.sleep(10);
			}
		} catch (InterruptedException e) {
		}
		
		assertEquals(3.6667, sas.get15MinutesVWAP(stock).doubleValue(), DELTA);
		
	}
	
	@Test
	public void testGet15MinutesVWAP_No_Trades() {
		Stock stock = TestUtil.getCommonTestStock();
		assertEquals(0.0, sas.get15MinutesVWAP(stock).doubleValue(), DELTA);
	}
	
	@Test
	public void testGet15MinutesVWAP_No_TradesForTheStock() {
		
		Stock stock = TestUtil.getCommonTestStock();
		trdMgr.storeTrade(stock, 10l, Side.BUY, BigDecimal.valueOf(100));
			
		assertEquals(0.0, sas.get15MinutesVWAP(TestUtil.getPreferredTestStock()).doubleValue(), DELTA);
	}
	
	
	@Test
	public void testGet15MinutesVWAP_Trades_WithZeroQty() {
		Stock stock = TestUtil.getCommonTestStock();
		try {
			for (int i = 1; i <= 5; i++) {
				trdMgr.storeTrade(stock, 10 * i, Side.BUY, BigDecimal.valueOf(i));
				Thread.sleep(10);
				trdMgr.storeTrade(stock, 10 * i, Side.SELL, BigDecimal.valueOf(i));
				Thread.sleep(10);
			}
		} catch (InterruptedException e) {
		}
		
		trdMgr.storeTrade(stock, 0, Side.BUY, BigDecimal.valueOf(10));
		assertEquals(3.6667, sas.get15MinutesVWAP(stock).doubleValue(), DELTA);
	}
	//getGBCEAllStockIndex
	
	@Test
	public  void testGetGBCEAllStockIndex() throws StockException {
		TestUtil.loadSampleData(stkMgr);
		try {
			for(Stock stock: stkMgr.getAllStocks()){
				for (int i = 1; i <= 3; i++) {
					trdMgr.storeTrade(stock, 10 * i, Side.BUY, BigDecimal.valueOf(i));
					Thread.sleep(10);
					trdMgr.storeTrade(stock, 10 * i, Side.SELL, BigDecimal.valueOf(i));
					Thread.sleep(10);
				}
			}
		} catch (InterruptedException e) {
		}
		//As all the trades last price is 3
		assertEquals(3.0, sas.getGBCEAllStockIndex().doubleValue(), DELTA);
		
	}
	
	@Test(expected = StockException.class)
	public  void testGetGBCEAllStockIndex_EmptyStocksCache()  {
		stkMgr.clearCache();
		BigDecimal gbceIndex=sas.getGBCEAllStockIndex();
	}
	
	
	//Geometric Mean
	@Test
	public void testGetGeometricMean_with_negative_infinity(){
		List<Double> numbers=Arrays.asList(1.2, Double.NEGATIVE_INFINITY);
		assertEquals(0.0, sas.getGeometricMean(numbers),0.0001);
	}
	@Test
	public void testGetGeometricMean_with_three_prices(){
		List<Double> numbers=Arrays.asList(1.0, 2.0,3.0);
		assertEquals(1.8171, sas.getGeometricMean(numbers),DELTA);
	}
	
	@Test
	public void testGetGeometricMean_with_four_prices(){
		List<Double> numbers=Arrays.asList(1.2,3.4,5.6,7.8);
		assertEquals(3.6537, sas.getGeometricMean(numbers),DELTA);
	}
	
	
	@Test
	public void testGetGeometricMean_with_Positive_infinity(){
		List<Double> numbers=Arrays.asList(1.2, Double.POSITIVE_INFINITY);
		assertEquals(0.0, sas.getGeometricMean(numbers),DELTA);
	}
	
	@Test
	public void testGetGeometricMean_with_1000_prices(){
		List<Double> numbers=new ArrayList<>(1000);
		for(double i=1;i<1000;i++){
			numbers.add(i);
		}
		assertEquals(369.1119, sas.getGeometricMean(numbers),DELTA);
	}
	
	@Test
	public void testGetGeometricMean_with_Zero(){
		List<Double> numbers=Arrays.asList(1.2, 0.0);
		assertEquals(0.0, sas.getGeometricMean(numbers),DELTA);
	}
	
}
