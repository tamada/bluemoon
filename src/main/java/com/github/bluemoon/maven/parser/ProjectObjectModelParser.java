package com.github.bluemoon.maven.parser;

import org.w3c.dom.Element;

import com.github.bluemoon.maven.Artifact;
import com.github.bluemoon.maven.Dependency;
import com.github.bluemoon.maven.DependencyScope;
import com.github.bluemoon.maven.License;
import com.github.bluemoon.maven.ProjectObjectModel;
import com.github.bluemoon.maven.RepositoryIOException;
import com.github.bluemoon.maven.RepositoryItemMismatchException;
import com.github.bluemoon.utils.DomUtils;

/**
 * This class parses pom.xml.
 * 
 * @author Haruaki Tamada
 */
public class ProjectObjectModelParser extends RepositoryItemParser{
    private ProjectObjectModel pom;

    /**
     * Basic constructor.
     */
    public ProjectObjectModelParser(ProjectObjectModel pom){
        super(pom);
        this.pom = pom;
    }

    protected void parseElement(Element root) throws RepositoryIOException{
        String version = DomUtils.getContentOfElement(root, "version");
        if(version != null && !pom.getVersion().equals(version)){
            throw new RepositoryItemMismatchException(String.format("expected version <%s>, but <%s>", pom.getVersion(), version));
        }

        Element parentTag = DomUtils.getChildElement(root, "parent");
        if(parentTag != null){
            String groupId = DomUtils.getContentOfElement(parentTag, "groupId");
            String artifactId = DomUtils.getContentOfElement(parentTag, "artifactId");
            String parentVersion = DomUtils.getContentOfElement(parentTag, "version");
            if(groupId != null && artifactId != null && parentVersion != null){
                pom.setParent(new ProjectObjectModel(new Artifact(groupId, artifactId, parentVersion)));
            }
        }

        Element dependencies = DomUtils.getChildElement(root, "dependencies");
        if(dependencies != null){
            Element[] dependency = DomUtils.getChildElements(dependencies, "dependency");

            for(int i = 0; i < dependency.length; i++){
                String artifactId = DomUtils.getContentOfElement(dependency[i], "artifactId");
                String groupId = DomUtils.getContentOfElement(dependency[i], "groupId");
                String dependencyVersion = DomUtils.getContentOfElement(dependency[i], "version");
                String scope = DomUtils.getContentOfElement(dependency[i], "scope");
                String optional = DomUtils.getContentOfElement(dependency[i], "optional");

                Artifact dependencyArtifact = new Artifact(groupId, artifactId, dependencyVersion);
                DependencyScope dependencyScope = DependencyScope.COMPILE;
                if(scope != null){
                    dependencyScope = DependencyScope.valueOf(scope.toUpperCase());
                }
                Dependency dep = new Dependency(dependencyArtifact, dependencyScope);

                if(optional == null || !optional.equalsIgnoreCase("true")){
                    pom.addDependency(dep);
                }
            }
        }
        Element licenses = DomUtils.getChildElement(root, "licenses");
        if(licenses != null){
            Element[] elements = DomUtils.getChildElements(licenses, "license");
            for(int i = 0; i < elements.length; i++){
                String name = DomUtils.getContentOfElement(elements[i], "name");
                String url = DomUtils.getContentOfElement(elements[i], "url");
                License license = new License(name, url);
                pom.addLicense(license);
            }
        }
    }
}
