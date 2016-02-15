package org.appcomponents.platform;

import org.appcomponents.platform.api.Platform;
import org.appcomponents.platform.test.AbstractPlatformIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Martin Janys
 */
public abstract class PlatformIntegrationTest extends AbstractPlatformIntegrationTest {

    @Autowired
    protected Platform platform;

}
