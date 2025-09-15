package com.task.starwars.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Jedi {
    private int id;
    private String name;
    private int totalMissions;
}
