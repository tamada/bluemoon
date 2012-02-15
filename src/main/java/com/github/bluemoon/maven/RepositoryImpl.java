package com.github.bluemoon.maven;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import net.sf.rydeen.lightning.maven.Artifact;
import net.sf.rydeen.lightning.maven.Metadata;
import net.sf.rydeen.lightning.maven.MetadataParser;

public class RepositoryImpl implements Repository{
    private URL location;
    private String name;
    private String pathSeparator = "";

    public RepositoryImpl(String name, URL location){
        this.name = name;
        this.location = location;
        if(!location.toString().endsWith("/")){
            pathSeparator = "/";
        }
    }

    @Override
    public Artifact findArtifact(String groupId, String artifactId){
        if(contains(groupId, artifactId)){
            return findArtifact(groupId, artifactId, getLatestVersion(groupId, artifactId));
        }
        throw new RepositoryItemNotFoundException(String.format("%s:%s not found", groupId, artifactId));
    }

    @Override
    public Artifact findArtifact(String groupId, String artifactId, String version){
        if(version == null){
            return findArtifact(groupId, artifactId);
        }
        return null;
    }

    @Override
    public boolean contains(Artifact artifact) throws IOException{
        URL url = new URL(
            location + pathSeparator + artifact.getRelativePath() + "/" + artifact.getFileName()
        );

        try{
            return url.getContent() != null;
        } catch(IOException e){
            return false;
        }        
    }

    @Override
    public boolean contains(String groupId, String artifactId) throws IOException{
        URL url = new URL(location + pathSeparator + groupId.replace('.', '/') + "/" + artifactId);
        try{
            return url.getContent() != null;
        } catch(IOException e){
            return false;
        }
    }

    @Override
    public boolean contains(String groupId, String artifactId, String version) throws IOException{
        if(version == null){
            return contains(groupId, artifactId);
        }
        return contains(new Artifact(groupId, artifactId, version));
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public URL getLocation(){
        return location;
    }

    @Override
    public InputStream fetch(RepositoryItem item) throws IOException{
        // TODO Auto-generated method stub
        return null;
    }

    private String getLatestVersion(String groupId, String artifactId) throws IOException{
        Metadata metadata = new Metadata(groupId, artifactId);
        InputStream in = fetch(metadata);
        new MetadataParser(metadata).parse(in);
        return metadata.getLatestVersion();
    }
}
