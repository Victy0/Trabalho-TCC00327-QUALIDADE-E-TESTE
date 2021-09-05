package com.uff.sem_barreiras.exceptions;

public class InsertWithAttributeException extends Exception{

    private static final long serialVersionUID = 1L;

    public InsertWithAttributeException( String objeto, String campo ){
        super( "Ausência ou incoerência no campo " + campo + " para realizar o salvamento d" + objeto);
    }



}
