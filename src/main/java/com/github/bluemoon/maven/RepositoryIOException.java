package com.github.bluemoon.maven;

import java.io.IOException;

/**
 * 
 * @author Haruaki Tamada
 */
public class RepositoryIOException extends IOException{
    private static final long serialVersionUID = -537262017828528590L;

    public RepositoryIOException(){
        super();
    }

    public RepositoryIOException(String message, Throwable cause){
        super(message, cause);
    }

    public RepositoryIOException(String message){
        super(message);
    }

    public RepositoryIOException(Throwable cause){
        super(cause);
    }
}
