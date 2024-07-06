import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;

public class StatusCodeTest {
    @DataProvider
    public Object[][] testCasesForStatusCodes() {
        return new Object[][]{
                {"", 400, "Status code 400 when missing pesel"},
                {"99051909826", 200, "Status code 200 for valid pesel"},
                {"61512155511", 200, "Status code 200  for invalid pesel"},
        };
    }

    @Test(dataProvider = "testCasesForStatusCodes")
    public void statusCodes(String pesel, int expectedStatusCode, String description) {
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=" + pesel);
        System.out.println("Test case: " + description + ";" + " pesel: " + pesel);
        int actualStatusCode = response.statusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode);
    }
}
