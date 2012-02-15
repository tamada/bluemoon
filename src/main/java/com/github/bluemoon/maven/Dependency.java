package com.github.bluemoon.maven;

public class Dependency{
    private Artifact artifact;
    private DependencyScope scope;

    public Dependency(Artifact artifact, DependencyScope scope){
        this.artifact = artifact;
        this.scope = scope;
    }

    public Dependency(Artifact artifact){
        this(artifact, DependencyScope.COMPILE);
    }

    public Artifact getArtifact(){
        return artifact;
    }

    public DependencyScope getScope(){
        return scope;
    }
}
