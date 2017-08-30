package security;

import base.BaseSpringTest;
import com.zhuke.shiro.MyToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.testng.annotations.Test;

/**
 * Created by ZHUKE on 2017/8/28.
 */
public class ShiroTest extends BaseSpringTest {

    @Test
    public void testLogin() {
        Subject currentUser = SecurityUtils.getSubject();

        currentUser.login(new MyToken("zhuke", "zhuke"));

        boolean hasRole = currentUser.hasRole("123");
        System.out.println(hasRole);

        boolean permitted = currentUser.isPermitted("123");
        System.out.println(permitted);

        Session session = currentUser.getSession();

    }
}
