package com.github.bluemoon.maven;

public class Artifact extends AbstractRepositoryItem{
    private static final long serialVersionUID = 478330678855861236L;

    private String version;

    public Artifact(String groupId, String artifactId, String version){
        super(groupId, artifactId);
        this.version = version;
    }

    public String getVersion(){
        return version;
    }

    @Override
    public ResourceType getType(){
        return ResourceType.JAR;
    }

    @Override
    public String getFileName(){
        return getArtifactId() + "-" + getVersion() + "." + getType().getExtension();
    }

    @Override
    public String getRelativePath(){
        return MavenPathUtils.getPath(getGroupId(), getArtifactId(), getVersion());
    }
}
