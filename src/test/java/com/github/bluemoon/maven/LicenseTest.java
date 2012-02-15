package com.github.bluemoon.maven;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 *
 * @author Haruaki Tamada
 */
public class LicenseTest{
    private License license;

    @Before
    public void setUp(){
        license = new License(
            "The Apache License Version 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0"
        );
    }

    @Test
    public void testBasic(){
        Assert.assertEquals("The Apache License Version 2.0", license.getName());
        Assert.assertEquals(
            "http://www.apache.org/licenses/LICENSE-2.0",
            license.getUrl()
        );

        Assert.assertEquals(License.APACHE_LICENSE_2_0, license);
    }
}
