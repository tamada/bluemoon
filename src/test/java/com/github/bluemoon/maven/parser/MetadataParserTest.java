package com.github.bluemoon.maven.parser;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import com.github.bluemoon.maven.Metadata;
import com.github.bluemoon.maven.RepositoryItemMismatchException;
import com.github.bluemoon.maven.ResourceType;

/**
 * 
 * @author Haruaki Tamada
 */
public class MetadataParserTest{
    @Test
    public void testBasic() throws Exception{
        Metadata meta = new Metadata("gid1", "product1");
        MetadataParser parser = new MetadataParser(meta);
        parser.parse(getClass().getResourceAsStream(
            "/repo1/gid1/product1/maven-metadata.xml")
        );

        Assert.assertEquals("maven-metadata.xml", meta.getFileName());
        Assert.assertEquals("gid1/product1", meta.getRelativePath());
        Assert.assertEquals(ResourceType.XML, meta.getType());

        Assert.assertEquals("product1", meta.getArtifactId());
        Assert.assertEquals("gid1", meta.getGroupId());
        Assert.assertEquals("20120118074248", meta.getLastUpdated());
        Assert.assertEquals("1.0.0", meta.getLatestVersion());
        Assert.assertEquals(4, meta.getVersionCount());

        Iterator<String> versionsIterator = meta.iterator();
        Assert.assertEquals("1.0.0-alpha", versionsIterator.next());
        Assert.assertEquals("1.0.0-beta", versionsIterator.next());
        Assert.assertEquals("1.0.0-rc", versionsIterator.next());
        Assert.assertEquals("1.0.0", versionsIterator.next());
        Assert.assertFalse(versionsIterator.hasNext());

        Assert.assertTrue(meta.contains("1.0.0-alpha"));
        Assert.assertTrue(meta.contains("1.0.0-beta"));
        Assert.assertTrue(meta.contains("1.0.0-rc"));
        Assert.assertTrue(meta.contains("1.0.0"));
        Assert.assertFalse(meta.contains("1.1.0"));
    }

    @Test(expected=RepositoryItemMismatchException.class)
    public void testMismatchArtifact() throws Exception{
        Metadata meta = new Metadata("gid1", "NOproduct1");
        MetadataParser parser = new MetadataParser(meta);
        parser.parse(getClass().getResourceAsStream(
            "/repo1/gid1/product1/maven-metadata.xml")
        );
    }

    @Test(expected=RepositoryItemMismatchException.class)
    public void testMismatchGroupId() throws Exception{
        Metadata meta = new Metadata("gid2", "product1");
        MetadataParser parser = new MetadataParser(meta);
        parser.parse(getClass().getResourceAsStream(
            "/repo1/gid1/product1/maven-metadata.xml")
        );
    }
}
