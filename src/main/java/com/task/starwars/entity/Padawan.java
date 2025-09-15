package com.task.starwars.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Padawan extends Jedi{
    private int jediMaster;
    private String rank;

    public Padawan(int id, String name, int missions, String rank) {
        super(id, name, missions);
        this.rank = rank;
        this.jediMaster = -1;
    }
}
