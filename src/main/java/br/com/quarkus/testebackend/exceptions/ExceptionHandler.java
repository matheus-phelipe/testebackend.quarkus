package br.com.quarkus.testebackend.exceptions;

public class ExceptionHandler extends Exception{
    public ExceptionHandler(String message) {
        super(message);
    }
}
