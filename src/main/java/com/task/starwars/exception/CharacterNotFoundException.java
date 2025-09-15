package com.task.starwars.exception;

public class CharacterNotFoundException extends RuntimeException{
    public CharacterNotFoundException(int id, String name){
        super("Personaje " + name + " no encontrado con id: "+ id +".");
    }
}
