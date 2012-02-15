package com.github.bluemoon.maven;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Maven2のリポジトリを表すインターフェース．
 * 
 * @author Haruaki Tamada
 */
public interface Repository{
    public Artifact findArtifact(String groupId, String artifactId) throws IOException;

    public Artifact findArtifact(String groupId, String artifactId, String version) throws IOException;

    public boolean contains(Artifact artifact) throws IOException;

    public boolean contains(String groupId, String artifactId) throws IOException;

    public boolean contains(String groupId, String artifactId, String version) throws IOException;

    public String getName();

    public URL getLocation();

    public InputStream fetch(RepositoryItem item) throws IOException;
}
