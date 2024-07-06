
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;

public class ErrorCodesTest {

    @DataProvider
    public Object[][] testCasesForErrorCodes() {
        return new Object[][]{
                {"318724839471", "INVL", "Pesel consists of 12 digits"},
                {"3725021488", "INVL", "Pesel consists of 10 digits"},
                {"990237862a7", "NBRQ", "Invalid character - letter"},
                {"_5491881961", "NBRQ", "Invalid character - special sign"},
                {"87033686904", "INVD", "Invalid day - the day does not exist"},
                {"23222901122", "INVD", "Invalid day - 29.02.2023"},
                {"42890631794", "INVC", "Invalid check sum"},
        };
    }

    @Test(dataProvider = "testCasesForErrorCodes")
    public void errorCodes(String pesel, String expectedErrorCode, String description) {
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=" + pesel);
        System.out.println("Test case: " + description + ";" + " pesel: " + pesel);
        String actualErrorCode = response.path("errors[0].errorCode");
        Assert.assertEquals(actualErrorCode, expectedErrorCode);
    }

    @Test
    public void checkInvalidMonthErrorCode() {
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=99130393049");
        String actualResponseErrorCode = response.path("errors[1].errorCode");
        String actualPesel = response.path("pesel");
        System.out.println("Test case: Check invalid month; pesel: 99130393049");
        Assert.assertEquals(actualResponseErrorCode, "INVM", "Check invalid month");
        Assert.assertEquals(actualPesel, "99130393049");
    }

    @Test
    public void checkInvalidYearErrorCode() {
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=99130393049");
        String actualResponseErrorCode = response.path("errors[0].errorCode");
        String actualPesel = response.path("pesel");
        System.out.println("Test case: Check invalid year; pesel: 99130393049");
        Assert.assertEquals(actualResponseErrorCode, "INVY", "Check invalid year");
        Assert.assertEquals(actualPesel, "99130393049");
    }
}


