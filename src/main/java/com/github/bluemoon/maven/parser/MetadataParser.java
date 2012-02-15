package com.github.bluemoon.maven.parser;

import org.w3c.dom.Element;

import com.github.bluemoon.maven.Metadata;
import com.github.bluemoon.maven.RepositoryIOException;
import com.github.bluemoon.utils.DomUtils;

/**
 * 
 * @author Haruaki Tamada
 */
public class MetadataParser extends RepositoryItemParser{
    private Metadata metadata;

    public MetadataParser(Metadata metadata){
        super(metadata);
        this.metadata = metadata;
    }

    protected void parseElement(Element root) throws RepositoryIOException{
        Element versioningElem = DomUtils.getChildElement(root, "versioning");
        if(versioningElem != null){
            Element[] versions = DomUtils.getChildElements(
                versioningElem, "versions/version"
            );
            for(int i = 0; i < versions.length; i++){
                metadata.addVersion(DomUtils.getContentOfElement(versions[i]));
            }
            Element release = DomUtils.getChildElement(
                versioningElem, "release"
            );
            if(release != null){
                metadata.setLatestVersion(DomUtils.getContentOfElement(release));
            }

            Element lastUpdatedElement = DomUtils.getChildElement(
                versioningElem, "lastUpdated"
            );
            if(lastUpdatedElement != null){
                metadata.setLastUpdated(
                    DomUtils.getContentOfElement(lastUpdatedElement)
                );
            }
        }
    }
}
