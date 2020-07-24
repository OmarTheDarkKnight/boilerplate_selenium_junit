package quickr_suite;

import base.BaseTest;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import util.TestUtil;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class AuthTest extends BaseTest {
    public String userEmail;

    public AuthTest(String userEmail) {
        this.userEmail = userEmail;
    }

    @Before
    public void init() {
        // check whether to skip this test class or run it
        if(!TestUtil.runTest("AuthTest"))
            Assume.assumeTrue(false); // skip the test
        loadPropertyFile("quickr_test.properties");
    }

    @Parameters
    public static Collection<Object[]> getData() {
        return Arrays.asList(TestUtil.readData("Login"));
    }

    @Test
    public void loginTest() throws InterruptedException {
        launchBrowser("Chrome");
        driver.get(getProp("url"));

        getObject("email_name").sendKeys(userEmail);
        String n1 = getObject("number1_xpath").getText();
        String n2 = getObject("number2_css").getText();

        int num1 = Integer.parseInt(n1);
        int num2 = Integer.parseInt(n2);
        int sum = num1+num2;

        getObject("sum_result_id").sendKeys(String.valueOf(sum));
//        driver.quit();
    }
}
