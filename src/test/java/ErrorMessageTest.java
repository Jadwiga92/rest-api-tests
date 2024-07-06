import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;

public class ErrorMessageTest {

    @DataProvider
    public Object[][] testCasesForErrorMessages() {
        return new Object[][]{
                {"318724839471", "Invalid length. Pesel should have exactly 11 digits.", "Pesel consists of 12 digits"},
                {"3725021488", "Invalid length. Pesel should have exactly 11 digits.", "Pesel consists of 10 digits"},
                {"990237862a7", "Invalid characters. Pesel should be a number.", "Invalid character - letter"},
                {"_5491881961", "Invalid characters. Pesel should be a number.", "Invalid character - special sign"},
                {"87033686904", "Invalid day.", "Invalid day - The day does not exist"},
                {"23222901122", "Invalid day.", "Invalid day - 29.02.2023"},
                {"42890631794", "Check sum is invalid. Check last digit.", "Invalid check sum"},
        };
    }

    @Test(dataProvider = "testCasesForErrorMessages")
    public void errorMessages(String pesel, String expectedErrorMessage, String description) {
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=" + pesel);
        System.out.println("Test case: " + description + ";" + " pesel: " + pesel);
        String actualErrorMessage = response.path("errors[0].errorMessage");
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @Test
    public void checkInvalidMonthErrorMessage() {
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=99130393049");
        String actualResponseErrorMessage = response.path("errors[1].errorMessage");
        String actualPesel = response.path("pesel");
        System.out.println("Test case: Check invalid month; pesel: 99130393049");
        Assert.assertEquals(actualResponseErrorMessage, "Invalid month.", "Check invalid month");
        Assert.assertEquals(actualPesel, "99130393049");
    }

    @Test
    public void checkInvalidYearErrorCode() {
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=99130393049");
        String actualResponseErrorMessage = response.path("errors[0].errorMessage");
        String actualPesel = response.path("pesel");
        System.out.println("Test case: Check invalid year; pesel: 99130393049");
        Assert.assertEquals(actualResponseErrorMessage, "Invalid year.", "Check invalid year");
        Assert.assertEquals(actualPesel, "99130393049");
    }
}
