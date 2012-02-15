package com.github.bluemoon.maven;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represents POM of Maven repository.
 * 
 * 
 * 
 * @author Haruaki Tamada
 */
public class ProjectObjectModel extends AbstractRepositoryItem implements Iterable<Artifact>{
    private static final long serialVersionUID = -6483216367473616237L;

    private Artifact artifact;
    private List<Dependency> dependencies = new ArrayList<Dependency>();
    private List<License> licenses = new ArrayList<License>();
    private boolean read = false;
    private Repository repository;
    private ProjectObjectModel parent;

    ProjectObjectModel(Artifact artifact){
        super(artifact.getGroupId(), artifact.getArtifactId());
        this.artifact = artifact;
    }

    public void addDependency(Dependency dependency){
        dependencies.add(dependency);
    }

    public void addLicense(License license){
        licenses.add(license);
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof ProjectObjectModel){
            ProjectObjectModel pom = (ProjectObjectModel)o;
            boolean flag = read == pom.isAlreadyRead()
                && getGroupId().equals(pom.getGroupId())
                && getArtifactId().equals(pom.getArtifactId());

            if(getVersion() != null){
                flag = flag && getVersion().equals(pom.getVersion());
            }
            else{
                flag = flag && pom.getVersion() == null;
            }
            if(repository != null){
                flag = flag && repository.equals(pom.repository);
            }
            else{
                flag = flag && pom.repository == null;
            }
            if(getParent() != null){
                flag = flag && getParent().equals(pom.getParent());
            }
            else{
                flag = flag && pom.getParent() == null;
            }
            return flag && Arrays.equals(getDependencies(), pom.getDependencies())
            && Arrays.equals(getLicenses(), pom.getLicenses());
        }
        return false;
    }

    public synchronized Artifact[] getDependencies(){
        // TODO
    }

    public int getDependencyCount(){
        return getDependencies().length;
    }

    @Override
    public String getFileName(){
        return getArtifactId() + "-" + artifact.getVersion() +
            "." + getType().getExtension();
    }

    public License getLicense(){
        return getLicense(0);
    }

    public License getLicense(int index){
        return getLicenses()[index];
    }

    public int getLicenseCount(){
        int count = licenses.size();
        if(count == 0 && getParent() != null){
            count = getParent().getLicenseCount();
        }
        return count;
    }

    public synchronized License[] getLicenses(){
        License[] licenseArray = licenses.toArray(new License[licenses.size()]);
        if(licenseArray.length == 0 && getParent() != null){
            licenseArray = getParent().getLicenses();
        }
        return licenseArray;
    }

    public ProjectObjectModel getParent(){
        return parent;
    }

    @Override
    public String getRelativePath(){
        return MavenPathUtils.getPath(artifact);
    }

    @Override
    public ResourceType getType(){
        return ResourceType.POM;
    }

    public URL getUrl() throws MalformedURLException{
        return new URL(
            repository.getLocation() + "/" +
            artifact.getRelativePath() + "/" + artifact.getFileName()
        );
    }

    public String getVersion(){
        return artifact.getVersion();
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + getGroupId().hashCode();
        result = prime * result + getArtifactId().hashCode();
        result = prime * result + ((getVersion() == null)? 0: getVersion().hashCode());
        result = prime * result + ((repository == null)? 0: repository.hashCode());
        result = prime * result + Arrays.hashCode(getLicenses());
        result = prime * result + Arrays.hashCode(getDependencies());
        return result;
    }

    public boolean hasLicense(){
        boolean flag = !licenses.isEmpty();
        if(!flag && getParent() != null){
            flag = getParent().hasLicense();
        }
        return flag;
    }

    public boolean isAlreadyRead(){
        return read;
    }

    public Iterator<Artifact> iterator(){
        return getDependenciesList(artifact).iterator();
    }

    public void setParent(ProjectObjectModel parent){
        this.parent = parent;
    }

    public void setRepository(Repository repository){
        this.repository = repository;
    }

    void parseDependencies(Repository repository) throws IOException{
        // TODO

    }

    void setAlreadyRead(boolean read){
        this.read = read;
    }

    private List<Artifact> getDependenciesList(Artifact artifact){
        // TODO
    }
}
