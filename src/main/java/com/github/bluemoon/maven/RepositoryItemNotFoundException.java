package com.github.bluemoon.maven;

/**
 * 
 * @author Haruaki Tamada
 */
public class RepositoryItemNotFoundException extends RepositoryIOException{
    private static final long serialVersionUID = -5487722162891227351L;

    public RepositoryItemNotFoundException(){
        super();
    }

    public RepositoryItemNotFoundException(String message, Throwable cause){
        super(message, cause);
    }

    public RepositoryItemNotFoundException(String message){
        super(message);
    }

    public RepositoryItemNotFoundException(Throwable cause){
        super(cause);
    }
}
