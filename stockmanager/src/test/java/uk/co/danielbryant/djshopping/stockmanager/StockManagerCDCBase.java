package uk.co.danielbryant.djshopping.stockmanager;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import uk.co.danielbryant.djshopping.stockmanager.exceptions.StockNotFoundException;
import uk.co.danielbryant.djshopping.stockmanager.model.Stock;
import uk.co.danielbryant.djshopping.stockmanager.resources.StockResource;
import uk.co.danielbryant.djshopping.stockmanager.services.StockService;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.mockito.MockitoAnnotations.initMocks;

public class StockManagerCDCBase {

    @Before
    public void setup() {
        initMocks(this);
        RestAssuredMockMvc.standaloneSetup(new StockResource(new FakeStockService()));
    }

    private class FakeStockService extends StockService {
        @Override
        public Stock getStock(String productId) throws StockNotFoundException {
            return newStock(productId, 10);
        }

        @Override
        public List<Stock> getStocks() {
            return IntStream.rangeClosed(1, 100).mapToObj(this::newStock).collect(toList());
        }

        private Stock newStock(int i) {
            return newStock(Integer.toString(i), i * 10);
        }

        private Stock newStock(String productId, int amountAvailable) {
            return new Stock(productId, "sku-" + productId, amountAvailable);
        }
    }
}
