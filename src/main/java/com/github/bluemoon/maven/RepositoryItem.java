package com.github.bluemoon.maven;

/**
 * This interface is represents an item in Maven2 repository.
 * 
 * @author Haruaki Tamada
 */
public interface RepositoryItem{
    /**
     * returns artifactId of this item.
     */
    public String getArtifactId();

    /**
     * returns groupId of this item.
     */
    public String getGroupId();

    /**
     * returns type of this item.
     */
    public ResourceType getType();

    /**
     * returns relative path of this item from root of repository.
     */
    public String getRelativePath();

    /**
     * returns file name of this item.
     */
    public String getFileName();
}
