import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;

public class InapropriateCharactersTest {

    @DataProvider
    public Object[][] testCasesForPeselValidator() {
        return new Object[][]{
                {"0832019264g", false, "Pesel contains one character"},
                {"TghUwKŁąwqh", false, "Pesel consists of chars"},
                {"4@01168725-", false, "Pesel consists special characters"}
        };
    }

    @Test(dataProvider = "testCasesForPeselValidator")
    public void pesel(String pesel, boolean isValid, String description) {
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=" + pesel);
        System.out.println("Test case: " + description + ";" + " pesel: " + pesel);
        boolean actualisValid = response.path("isValid");
        Assert.assertEquals(actualisValid, isValid);
    }
}
