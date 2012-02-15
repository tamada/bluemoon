package com.github.bluemoon.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Utility class for dom parsing.
 * The instance of this class is never constructed.
 * 
 * @author Haruaki Tamada
 */
public class DomUtils{
    /**
     * This constructor is never called.
     */
    private DomUtils(){
    }

    public static Element readRootElement(InputStream in) throws IOException, ParserConfigurationException, SAXException{
        DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbfactory.newDocumentBuilder();
        Document doc = builder.parse(in);
        return doc.getDocumentElement();
    }

    public static String getContentOfElement(Element elem){
        NodeList list = elem.getChildNodes();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < list.getLength(); i++){
            Node node = list.item(i);
            if(node.getNodeType() == Node.TEXT_NODE){
                sb.append(node.getNodeValue());
            }
        }
        return new String(sb).trim();
    }

    public static String getContentOfElement(Element elem, String name){
        if(name.contains("/")){
            int index = name.indexOf("/");
            String n = name.substring(0, index);
            return getContentOfElement(getChildElement(elem, n), name.substring(index + 1));
        }
        if(elem != null){
            NodeList list = elem.getChildNodes();
            for(int i = 0; i < list.getLength(); i++){
                Node node = list.item(i);
                if(node.getNodeName().equals(name)){
                    return getContentOfElement((Element)node);
                }
            }
        }
        return null;
    }

    public static Element getChildElement(Element elem, String name){
        if(name.contains("/")){
            int index = name.indexOf("/");
            String n = name.substring(0, index);
            return getChildElement(getChildElement(elem, n), name.substring(index + 1));
        }

        if(elem != null){
            NodeList child = elem.getChildNodes();
            for(int i = 0; i < child.getLength(); i++){
                Node node = child.item(i);
                if(node.getNodeName().equals(name)){
                    return (Element)node;
                }
            }
        }
        return null;
    }

    public static Element[] getChildElements(Element elem, String name){
        if(name.contains("/")){
            int index = name.indexOf("/");
            String n = name.substring(0, index);
            return getChildElements(getChildElement(elem, n), name.substring(index + 1));
        }
        List<Element> list = new ArrayList<Element>();
        if(elem != null){
            NodeList children = elem.getChildNodes();
            for(int i = 0; i < children.getLength(); i++){
                Node node = children.item(i);
                if(node.getNodeName().equals(name)){
                    list.add((Element)node);
                }
            }
        }
        return list.toArray(new Element[list.size()]);
    }
}
