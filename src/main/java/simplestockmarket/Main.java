/**
 * 
 */
package simplestockmarket;

import java.math.BigDecimal;
import java.util.Random;

import simplestockmarket.analytic.StockAnalyticService;
import simplestockmarket.data.Side;
import simplestockmarket.data.StockType;
import simplestockmarket.manager.StockManager;
import simplestockmarket.manager.TradeManager;
import simplestockmarket.model.Stock;

/**
 * Simple Main Class to run some test Data and explore the available functions
 * 
 * @author sonid
 *
 */
public class Main {

	public static void loadSampleData(StockManager stkMgr){
		stkMgr.storeStock("TEA", new Stock("TEA", StockType.COMMON, BigDecimal.ZERO, BigDecimal.ZERO,BigDecimal.valueOf(100),BigDecimal.valueOf(100.0)));
		stkMgr.storeStock("POP", new Stock("POP", StockType.COMMON, BigDecimal.valueOf(8.0), BigDecimal.ZERO, BigDecimal.valueOf(100.0),BigDecimal.valueOf(80.0)));
		stkMgr.storeStock("ALE", new Stock("ALE", StockType.COMMON, BigDecimal.valueOf(23.0), BigDecimal.ZERO, BigDecimal.valueOf(60.0),BigDecimal.valueOf(100.0)));
        stkMgr.storeStock("GIN", new Stock("GIN", StockType.PREFERRED, BigDecimal.valueOf(8.0), BigDecimal.valueOf(0.2), BigDecimal.valueOf(100.0),BigDecimal.valueOf(100.0)));
        stkMgr.storeStock("JOE", new Stock("JOE", StockType.COMMON, BigDecimal.valueOf(13.0), BigDecimal.ZERO, BigDecimal.valueOf(250.0),BigDecimal.valueOf(100.0)));
    }

	public static void main(String[] args) throws InterruptedException {
			StockManager stkMgr = new StockManager();
			TradeManager trdMgr = new TradeManager();
			StockAnalyticService sas = new StockAnalyticService(trdMgr, stkMgr);
			Main.loadSampleData(stkMgr);
		
			for (Stock stock :stkMgr.getAllStocks()) {
				stkMgr.storeStock(stock.getSymbol(), stock);

				for (int i = 2; i <= 50; i++) {
					Random r = new Random();
					

					trdMgr.storeTrade(stock, 10l*i, Side.BUY, BigDecimal.valueOf(i * r.nextDouble()));
					Thread.sleep(10);
					trdMgr.storeTrade(stock, 10l*i, Side.SELL, BigDecimal.valueOf(i * r.nextDouble()));
					Thread.sleep(10);
				}
				System.out.println(stock.getSymbol() + " price: " + stock.getPrice().setScale(5, BigDecimal.ROUND_HALF_EVEN));
				System.out.println(stock.getSymbol() + " Divided Yield: " + sas.getDividendYield(stock, stock.getPrice()));
				System.out.println(stock.getSymbol() + " P/E Ratio: " + sas.getPERatio(stock, stock.getPrice()));
				System.out.println(stock.getSymbol() + " volumeWeightedStockPrice: " + sas.get15MinutesVWAP(stock));
			}
			BigDecimal gbceAllShareIndex = sas.getGBCEAllStockIndex();
			System.out.println("GBCE All Share Index:" + gbceAllShareIndex);
	}
}