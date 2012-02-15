package com.github.bluemoon;

import org.junit.Assert;
import org.junit.Test;

/**
 * check version
 * 1.0-alpha
 * 1.0-rc1
 * 1.0-rc2
 * 1.0
 * 1.0.1
 * 1.1
 * 1.10
 * 1.20
 * 2.0
 * 
 * @author Haruaki Tamada
 */
public class VersionTest{
    @Test
    public void versionTest() throws Exception{
        Version version = new Version("1.2.3");

        Assert.assertEquals(new Version("1.2.3"), version);
        Assert.assertEquals(new Version("1-2-3"), version);
    }

    @Test
    public void versionCompareTest() throws Exception{
        Version version = new Version("1.2.3");

        Assert.assertEquals(0, version.compareTo(new Version("1.2.3")));
        Assert.assertTrue(version.compareTo(new Version("1.3")) < 0);
        Assert.assertTrue(version.compareTo(new Version("1.2.4")) < 0);
        Assert.assertTrue(version.compareTo(new Version("1.2.13")) < 0);

        Assert.assertTrue(version.compareTo(new Version("1.1")) > 0);
        Assert.assertTrue(version.compareTo(new Version("1.1.10")) > 0);
        Assert.assertTrue(version.compareTo(new Version("1.2.1")) > 0);
    }

    @Test
    public void versionCompareTest2() throws Exception{
        Version version = new Version("1.2.3-beta");

        Assert.assertEquals(0, version.compareTo(new Version("1.2.3-beta")));
        Assert.assertTrue(version.compareTo(new Version("1.2.3-alpha")) > 0);
        Assert.assertTrue(version.compareTo(new Version("1.2.3.1-beta")) < 0);
    }

    @Test
    public void testBasic() throws Exception{
        Version version = new Version("1.0.0-SNAPSHOT");

        Assert.assertTrue(version.isSnapshot());
        Assert.assertEquals(version, new Version("1.0.0-SNAPSHOT"));
        Assert.assertTrue(version.isBeforeThan(new Version("1.0.0")));
        Assert.assertTrue(version.isAfterThan(new Version("1.0")));

        Assert.assertEquals("1.0.0-SNAPSHOT", version.toString());
    }

    @Test
    public void testVersionOrder() throws Exception{
        Version rc = new Version("1.0.0-rc");
        Version snapshot = new Version("1.0.0-SNAPSHOT");
        Version alpha = new Version("1.0.0-alpha");
        Version version = new Version("1.0.0");

        Assert.assertTrue(version.isAfterThan(snapshot));
        Assert.assertTrue(version.isAfterThan(alpha));
        Assert.assertTrue(version.isAfterThan(rc));
        Assert.assertFalse(version.isAfterThan(version));
        Assert.assertFalse(version.isBeforeThan(version));

        Assert.assertFalse(rc.isAfterThan(rc));
        Assert.assertFalse(rc.isBeforeThan(rc));
        Assert.assertTrue(rc.isAfterThan(snapshot));
        Assert.assertTrue(rc.isAfterThan(alpha));
        Assert.assertTrue(rc.isBeforeThan(version));

        Assert.assertFalse(snapshot.isAfterThan(snapshot));
        Assert.assertFalse(snapshot.isBeforeThan(snapshot));
        Assert.assertTrue(snapshot.isBeforeThan(rc));
        Assert.assertTrue(snapshot.isBeforeThan(alpha));
        Assert.assertTrue(snapshot.isBeforeThan(version));

        Assert.assertFalse(alpha.isAfterThan(alpha));
        Assert.assertFalse(alpha.isBeforeThan(alpha));
        Assert.assertTrue(alpha.isAfterThan(snapshot));
        Assert.assertTrue(alpha.isBeforeThan(rc));
        Assert.assertTrue(alpha.isBeforeThan(version));
    }

    @Test
    public void testReleaseCandidateCheck() throws Exception{
        Assert.assertTrue(new Version("1.0.0-rc").isReleaseCandidate());
        Assert.assertTrue(new Version("1.0.0-RC").isReleaseCandidate());
        Assert.assertTrue(new Version("1.0.0-rc10").isReleaseCandidate());
        Assert.assertTrue(new Version("1.0.0-rc2").isReleaseCandidate());
        Assert.assertTrue(new Version("1.0.0-rc_2").isReleaseCandidate());
        Assert.assertTrue(new Version("1.0.0rc-2").isReleaseCandidate());
        Assert.assertFalse(new Version("1.0.0").isReleaseCandidate());
    }

    @Test
    public void testBasic2() throws Exception{
        Version version = new Version("1.0.0-snapshot");

        Assert.assertTrue(version.isSnapshot());
        Assert.assertEquals(version, new Version("1.0.0-snapshot"));
        Assert.assertEquals(version.hashCode(), new Version("1.0.0-snapshot").hashCode());
        Assert.assertTrue(version.isBeforeThan(new Version("1.0.0")));
        Assert.assertTrue(version.isAfterThan(new Version("1.0")));

        Assert.assertEquals("1.0.0-snapshot", version.toString());
    }
}
