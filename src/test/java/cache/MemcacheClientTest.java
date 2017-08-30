package cache;

import base.BaseSpringTest;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by ZHUKE on 2017/8/30.
 */
public class MemcacheClientTest extends BaseSpringTest {

    @Autowired
    private MemcachedClient memcachedClient;

    @Test
    public void testAddCache() {
        String key = "name";
        String value = "zhuke";
        memcachedClient.set(key, 100, value);
        Assert.assertEquals(value, memcachedClient.get(key));
    }
}
