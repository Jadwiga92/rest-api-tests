import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;

public class CoingekoCheckStatusCodeTest {

    @DataProvider
    public Object[][] testCasesForCoingeko() {
        return new Object[][]{
                {"bitcoin", 200, "Get current data for bitcoin"},
                {"sth", 404, "The coin does not exist"},
        };
    }

    @Test(dataProvider = "testCasesForCoingeko")
    public void checkStatusCode(String currency, int expectedStatusCode, String description) {
        Response response = get("https://api.coingecko.com/api/v3/coins/" + currency);
        System.out.println("Test case: " + description);
        int actualStatusCode = response.statusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode);
    }
}
