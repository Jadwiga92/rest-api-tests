import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;

public class DateOfBirthTest {

    @DataProvider
    public Object[][] testCasesForDateOfBirth() {
        return new Object[][]{
                //testy dla 1800
                {"00810916137", true, "Month 81, 9.01.1800"},
                {"00921226790", true, "Month 92, 12.12.1800"},
                {"00800916136", false, "Month 80 - doesn't exist"},
                {"00931226791", false, "Month 93 - doesn't exist"},

                //testy dla 1899
                {"99810260100", true, "Month 81, 2.01.1899"},
                {"99922798975", true, "Month 92, 27.12.1899"},
                {"99800260109", false, "Month 80 - doesn't exist"},
                {"99932798976", false, "Month 93 - doesn't exist"},

                //testy dla 1900
                {"00011687584", true, "Month 01, 16.01.1900"},
                {"00121647531", true, "Month 12, 16.12.1900"},
                {"00001687583", false, "Month 00 - doesn't exist"},
                {"00131647532", false, "Month 13 - doesn't exist"},

                //testy dla 1999
                {"99012393684", true, "Month 01, 23.01.1900"},
                {"99121333384", true, "Month 12, 13.12.1900"},
                {"99002393683", false, "Month 00 - doesn't exist"},
                {"99131333385", false, "Month 13 - doesn't exist"},

                // testy dla 2000
                {"00212966798", true, "Month 21, 29.01.2000"},
                {"00320314283", true, "Month 32, 3.12.2000"},
                {"00202966797", false, "Month 20 - doesn't exist"},
                {"00330314284", false, "Month 33 - doesn't exist"},

                // testy dla 2099
                {"99211507057", true, "Month 21, 15.01.2099"},
                {"99321491857", true, "Month 32, 14.12.2099"},
                {"99201507056", false, "Month 20 - doesn't exist"},
                {"99331491858", false, "Month 33 - doesn't exist"},

                // testy dla 2100
                {"00410937965", true, "Month 41, 9.01.2100"},
                {"00520551787", true, "Month 52, 5.12.2100"},
                {"00400937964", false, "Month 40 - doesn't exist"},
                {"00530551788", false, "Month 53 - doesn't exist"},

                // testy dla 2199
                {"99412766602", true, "Month 41, 27.01.2199"},
                {"99521886372", true, "Month 52, 18.12.2199"},
                {"99402766601", false, "Month 40 - doesn't exist"},
                {"99531886373", false, "Month 53 - doesn't exist"},

                // testy dla 2200
                {"00611975533", true, "Month 61, 19.01.2200"},
                {"00722032422", true, "Month 72, 20.12.2200"},
                {"00601975532", false, "Month 60 - doesn't exist"},
                {"00732032423", false, "Month 73 - doesn't exist"},

                //testy dla 2299
                {"99612834994", true, "Month 61, 28.01.2299"},
                {"99722804630", true, "Month 72, 28.12.2299"},
                {"99602834993", false, "Month 60 - doesn't exist"},
                {"99732804631", false, "Month 73 - doesn't exist"},
        };
    }

    @Test(dataProvider = "testCasesForDateOfBirth")
    public void dateOfBirth(String pesel, boolean expectedIsValid, String description) {
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=" + pesel);
        System.out.println("Test case: " + description);
        boolean actualisValid = response.path("isValid");
        Assert.assertEquals(actualisValid, expectedIsValid);
    }
}
