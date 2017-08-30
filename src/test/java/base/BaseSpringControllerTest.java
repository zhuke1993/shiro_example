package base; /**
 * Created by ZHUKE on 2017/4/25.
 */

import com.zhuke.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = Application.class)
public class BaseSpringControllerTest extends AbstractTestNGSpringContextTests {
    @Autowired
    public WebApplicationContext wac;

    public MockMvc mockMvc;

    public MockHttpSession mockAdminLoginSession;

    @BeforeMethod
    public void setUp() throws Exception {
    }
}
