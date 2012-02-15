package com.github.bluemoon.maven;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author Haruaki Tamada
 */
public class MetadataTest{
    @Test
    public void testBasic() throws Exception{
        Metadata meta = new Metadata("gid1", "product1");

        Assert.assertEquals(ResourceType.XML, meta.getType());
        Assert.assertEquals("gid1/product1", meta.getRelativePath());
        Assert.assertEquals("maven-metadata.xml", meta.getFileName());

        meta.addVersion("2.1.0");
        meta.addVersion("1.0.0"); 

        Assert.assertEquals("2.1.0", meta.getLatestVersion());
        Assert.assertEquals(2, meta.getVersionCount());

        meta.addVersion("2.2.0-SNAPSHOT");
        Assert.assertEquals("2.1.0", meta.getLatestVersion());
        Assert.assertEquals(3, meta.getVersionCount());
    }
}

