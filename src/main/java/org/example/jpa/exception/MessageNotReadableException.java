package org.example.jpa.exception;

public class MessageNotReadableException extends RuntimeException{
    public MessageNotReadableException(){
        super("unable to read request data");
    }
}
