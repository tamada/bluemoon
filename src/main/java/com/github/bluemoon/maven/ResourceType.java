package com.github.bluemoon.maven;

/**
 * This enum represents resource type in Maven2 repository.
 * 
 * @author Haruaki Tamada
 */
public enum ResourceType{
    XML("xml"),

    XML_MD5("xml.md5"),

    XML_SHA1("xml.sha1"),

    POM("pom"),

    POM_MD5("pom.md5"),

    POM_SHA1("pom.sha1"),

    JAR("jar"),

    JAR_MD5("jar.md5"),

    JAR_SHA1("jar.sha1");

    private String extension;

    private ResourceType(String extension) {
        this.extension = extension;
    }

    /**
     * returns extension of this type.
     */
    public String getExtension() {
        return extension;
    }
}
