package com.uff.sem_barreiras.exceptions;

public class InsertWithAttributeException extends Exception{

    private static final long serialVersionUID = 1L;

    public InsertWithAttributeException( String objeto, String campo ){
        super( "AusĂȘncia ou incoerĂȘncia no campo " + campo + " para realizar o salvamento d" + objeto);
    }



}
