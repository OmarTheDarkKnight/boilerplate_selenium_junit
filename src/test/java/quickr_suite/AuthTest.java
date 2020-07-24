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
    public String password;
    public String expectedResult;

    public AuthTest(String userEmail, String password, String expectedResult) {
        this.userEmail = userEmail;
        this.password = password;
        this.expectedResult = expectedResult;
    }

    @Before
    public void init() {
        // check whether to skip this test class or run it
        if(!TestUtil.runTest("AuthTest"))
            Assume.assumeTrue(false); // skip the test
    }

    @Parameters
    public static Collection<Object[]> getData() {
        return Arrays.asList(TestUtil.readData("Login"));
    }

    @Test
    public void loginTest() {

    }
}
