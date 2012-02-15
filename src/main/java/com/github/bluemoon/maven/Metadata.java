package com.github.bluemoon.maven;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.bluemoon.Version;

/**
 * This class represents maven-metadata.xml.
 * 
 * @author Haruaki Tamada
 */
public class Metadata extends AbstractRepositoryItem implements Iterable<String>{
    private static final long serialVersionUID = -7859414256240646632L;

    private List<String> versions = new ArrayList<String>();
    private String lastUpdated;
    private String latest;
    private transient Version latestVersion;

    /**
     * Constructor.
     * {@link #Metadata(String, String)
     * <code>this(artifact.getGroupId(), artifact.getArtifactId())</code>}
     */
    public Metadata(Artifact artifact){
        this(artifact.getGroupId(), artifact.getArtifactId());
    }

    /**
     * Basic constructor.
     */
    public Metadata(String groupId, String artifactId){
        super(groupId, artifactId);
    }

    /**
     * Self constructor.
     */
    public Metadata(Metadata metadata){
        this(metadata.getGroupId(), metadata.getArtifactId());

        lastUpdated = metadata.getLastUpdated();
        latest = metadata.getLatestVersion();
        if(latest != null){
            latestVersion = new Version(latest);
        }
        for(String v: metadata){
            versions.add(v);
        }
    }

    /**
     * returns {@link ResourceType#XML <code>ResourceType.XML</code>}.
     */
    public ResourceType getType(){
        return ResourceType.XML;
    }

    /**
     * returns latest version.
     */
    public String getLatestVersion(){
        return latest;
    }

    /**
     * returns last updated.
     */
    public String getLastUpdated(){
        return lastUpdated;
    }

    /**
     * returns the count of versions.
     */
    public int getVersionCount(){
        return versions.size();
    }

    /**
     * returns the iterator of versions.
     */
    public Iterator<String> iterator(){
        return versions.iterator();
    }

    /**
     * returns true if this object has given version.
     */
    public boolean contains(String version){
        return versions.contains(version);
    }

    /**
     * returns &quot;maven-metadata.xml&quot;
     */
    public String getFileName(){
        return "maven-metadata.xml";
    }

    /**
     * returns relative path of this metadata.
     */
    public String getRelativePath(){
        return MavenPathUtils.getPath(getGroupId(), getArtifactId());
    }

    public void setLastUpdated(String lastUpdated){
        this.lastUpdated = lastUpdated;
    }

    public void addVersion(String version){
        versions.add(version);

        if(latest == null){
            latest = version;
        }
        Version v = new Version(version);
        if(!v.isSnapshot()){
            latestVersion = new Version(latest);
        }
        if(latestVersion != null && latestVersion.compareTo(v) < 0 && !v.isSnapshot()){
            latest = version;
        }
    }

    public void setLatestVersion(String version){
        latestVersion = new Version(version);
    }

    private void readObject(ObjectInputStream in) 
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        if(latest != null){
            latestVersion = new Version(latest);
        }
    }
}
