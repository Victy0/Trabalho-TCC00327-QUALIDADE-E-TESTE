package com.uff.sem_barreiras.exceptions;

public class InsertException extends Exception{

    private static final long serialVersionUID = 1L;

    public InsertException( String objeto ){
        super( "AusĂȘncia ou incoerĂȘncia em campos para realizar o salvamento d" + objeto);
    }



}
