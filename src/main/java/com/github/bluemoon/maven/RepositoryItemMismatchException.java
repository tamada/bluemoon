package com.github.bluemoon.maven;

/**
 * 
 * @author Haruaki Tamada
 */
public class RepositoryItemMismatchException extends RepositoryIOException{
    private static final long serialVersionUID = 1326957817213619812L;

    public RepositoryItemMismatchException(){
        super();
    }

    public RepositoryItemMismatchException(String message, Throwable cause){
        super(message, cause);
    }

    public RepositoryItemMismatchException(String message){
        super(message);
    }

    public RepositoryItemMismatchException(Throwable cause){
        super(cause);
    }
}
