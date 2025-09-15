package com.task.starwars.exception;

public class CharacterRepeatedException extends RuntimeException{
    public CharacterRepeatedException(int id, String clase){
        super("Personaje " + clase + " ya registrado con id: "+ id +".");
    }
}
