package com.github.bluemoon.maven;

import java.io.Serializable;


/**
 * Abstract class for {@link RepositoryItem <code>RepositoryItem</code>}.
 * 
 * @author Haruaki Tamada
 */
public abstract class AbstractRepositoryItem implements RepositoryItem, Serializable{
    private static final long serialVersionUID = -4523635142917496886L;

    private String artifactId;
    private String groupId;

    /**
     * Basic constructor.
     */
    public AbstractRepositoryItem(String groupId, String artifactId){
        this.artifactId = artifactId;
        this.groupId = groupId;
    }

    public final String getArtifactId(){
        return artifactId;
    }

    public final String getGroupId(){
        return groupId;
    }

    public abstract ResourceType getType();

    public abstract String getFileName();

    public abstract String getRelativePath();
}
