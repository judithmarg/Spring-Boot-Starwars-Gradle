package com.task.starwars.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class Master extends Jedi{
    private Set<Integer> padawans;
    private String speciality;

    public Master(int id, String name, int missions, String speciality){
        super(id, name, missions);
        this.speciality = speciality;
        padawans = new HashSet<>();
    }
}
