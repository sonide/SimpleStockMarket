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
		stkMgr.storeStock("TEA", new Stock("TEA", StockType.COMMON, BigDecimal.ZERO, BigDecimal.ZERO, new BigDecimal(100.0),new BigDecimal(100.0)));
		stkMgr.storeStock("POP", new Stock("POP", StockType.COMMON, new BigDecimal(8.0), BigDecimal.ZERO, new BigDecimal(100.0),new BigDecimal(80.0)));
		stkMgr.storeStock("ALE", new Stock("ALE", StockType.COMMON, new BigDecimal(23.0), BigDecimal.ZERO, new BigDecimal(60.0),new BigDecimal(100.0)));
        stkMgr.storeStock("GIN", new Stock("GIN", StockType.PREFERRED, new BigDecimal(8.0), new BigDecimal(0.2), new BigDecimal(100.0),new BigDecimal(100.0)));
        stkMgr.storeStock("JOE", new Stock("JOE", StockType.COMMON, new BigDecimal(13.0), BigDecimal.ZERO, new BigDecimal(250.0),new BigDecimal(100.0)));
    }

	public static void main(String[] args) {
		try {
			StockManager stkMgr = new StockManager();
			TradeManager trdMgr = new TradeManager();
			StockAnalyticService sas = new StockAnalyticService(trdMgr, stkMgr);
			Main.loadSampleData(stkMgr);
		
			for (Stock stock :stkMgr.getAllStocks()) {
				stkMgr.storeStock(stock.getSymbol(), stock);

				for (int i = 2; i <= 50; i++) {
					Random r = new Random();
					

					trdMgr.storeTrade(stock, 10 * i, Side.BUY, new BigDecimal(i * r.nextDouble()));
					Thread.sleep(10);
					trdMgr.storeTrade(stock, 10 * i, Side.SELL, new BigDecimal(i * r.nextDouble()));
					Thread.sleep(10);
				}
				System.out.println(stock.getSymbol() + " price: " + stock.getPrice().setScale(5, BigDecimal.ROUND_HALF_EVEN));
				System.out.println(stock.getSymbol() + " Divided Yield: " + sas.getDividendYield(stock, stock.getPrice()));
				System.out.println(stock.getSymbol() + " P/E Ratio: " + sas.getPE_Ratio(stock, stock.getPrice()));
				System.out.println(stock.getSymbol() + " volumeWeightedStockPrice: " + sas.get15MinutesVWAP(stock));
			}
			BigDecimal GBCEallShareIndex = sas.getGBCEAllStockIndex();
			System.out.println("GBCE All Share Index:" + GBCEallShareIndex);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}