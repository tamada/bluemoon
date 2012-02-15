package com.github.bluemoon.maven;

/**
 * 
 * @author Haruaki Tamada
 */
class MavenPathUtils{
    private MavenPathUtils(){
    }

    static String getPath(String groupId, String artifactId){
        return getPath(groupId, artifactId, null);
    }

    static String getPath(Artifact artifact){
        return getPath(
            artifact.getGroupId(),
            artifact.getArtifactId(),
            artifact.getVersion()
        );
    }

    static String getPath(String groupId, String artifactId, String version){
        StringBuilder sb = new StringBuilder();
        sb.append(groupId.replace('.', '/'));
        sb.append("/");
        sb.append(artifactId);
        if(version != null){
            sb.append("/");
            sb.append(version);
        }
        return new String(sb);
    }
}
