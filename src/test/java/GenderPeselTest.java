import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;

public class GenderPeselTest {

    @DataProvider
    public Object[][] testCasesForGender() {
        return new Object[][]{
                {"99020305309", "Female", "Female's pesel - number 0"},
                {"56020318124", "Female", "Female's pesel - number 2"},
                {"85020337044", "Female", "Female's pesel - number 4"},
                {"07320925462", "Female", "Female's pesel - number 6"},
                {"90060929685", "Female", "Female's pesel - number 8"},
                {"90060942615", "Male", "Male's pesel - number 1"},
                {"71101654034", "Male", "Male's pesel - number 3"},
                {"94120199359", "Male", "Male's pesel - number 5"},
                {"99920196171", "Male", "Male's pesel - number 7"},
                {"92043052997", "Male", "Male's pesel - number 9"},
        };
    }

    @Test(dataProvider = "testCasesForGender")
    public void genderPeselTest(String pesel, String expectedGender, String description) {
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=" + pesel);
        System.out.println("Test case: " + description + ";" + " pesel: " + pesel);
        String actualGender = response.path("gender");
        Assert.assertEquals(actualGender, expectedGender);
    }
}
