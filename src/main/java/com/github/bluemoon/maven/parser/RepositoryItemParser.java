package com.github.bluemoon.maven.parser;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.github.bluemoon.maven.RepositoryIOException;
import com.github.bluemoon.maven.RepositoryItem;
import com.github.bluemoon.maven.RepositoryItemMismatchException;
import com.github.bluemoon.utils.DomUtils;

/**
 * 
 * @author Haruaki Tamada
 */
public abstract class RepositoryItemParser{
    private RepositoryItem item;

    public RepositoryItemParser(RepositoryItem item){
        this.item = item;
    }

    public void parse(InputStream in) throws RepositoryIOException{
        try{
            Element root = DomUtils.readRootElement(in);

            checkRepositoryItem(root);

            parseElement(root);
        } catch(RepositoryIOException e){
            throw e;
        } catch(SAXException e){
            throw new RepositoryIOException(e);
        } catch(IOException e){
            throw new RepositoryIOException(e);
        } catch(ParserConfigurationException e){
            throw new RepositoryIOException(e);
        }
    }

    private void checkRepositoryItem(Element root) throws RepositoryIOException{
        String artifactId = DomUtils.getContentOfElement(root, "artifactId");
        String groupId = DomUtils.getContentOfElement(root, "groupId");

        if(!item.getArtifactId().equals(artifactId)){
            throw new RepositoryItemMismatchException(String.format("artifactId is expected <%s>, but <%s>", item.getArtifactId(), artifactId));
        }
        if(!item.getGroupId().equals(groupId)){
            throw new RepositoryItemMismatchException(String.format("groupId is expected <%s>, but <%s>", item.getGroupId(), groupId));
        }
    }

    protected abstract void parseElement(Element root) throws RepositoryIOException;
}
