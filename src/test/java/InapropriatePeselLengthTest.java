import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.get;

public class InapropriatePeselLengthTest {

    @DataProvider
    public Object[][] testCasesForInappropriateLength() {
        return new Object[][]{
                {"644723433021", false, "Pesel contains 12 numbers" },
                {"7842084302", false, "Pesel contains 10 numbers" },
                {"0", false, "Pesel contains 0 as the only number" },
                {"00000000000", false, "Pesel contains 11 zeroes" },
        };
    }

    @Test(dataProvider = "testCasesForInappropriateLength")
    public void inappropriateLength(String pesel, boolean expectedIsValid, String description ){
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=" + pesel);
        System.out.println("Test case: " + description +";"+ " pesel: " + pesel);
        boolean actualisValid = response.path("isValid");
        Assert.assertEquals(actualisValid, expectedIsValid);
    }
}
