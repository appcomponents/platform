package org.appcomponents.platform.test;

import org.appcomponents.platform.test.loader.PlatformContextLoader;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author Martin Janys
 */
@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest("server.port:0")
@WebAppConfiguration
@ContextConfiguration(loader = PlatformContextLoader.class, classes = EmptyConfiguration.class)
public abstract class AbstractPlatformIntegrationTest {

    @Value("${local.server.port}")
    protected int port;

}
