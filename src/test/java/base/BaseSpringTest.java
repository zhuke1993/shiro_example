package base;

import com.zhuke.Application;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * Created by ZHUKE on 2017/4/25.
 */
@SpringBootTest(classes = Application.class,webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BaseSpringTest extends AbstractTestNGSpringContextTests {
}
