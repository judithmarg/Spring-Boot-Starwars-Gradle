package com.task.starwars.repository;

import com.task.starwars.entity.Jedi;

import java.util.List;

public interface JediRepository<T extends Jedi> {
    List<T> findAll();
    T findById(int id);
    T save(T jedi);
    T replace(int id, T jedi);
    T delete(int id);
    void assign(int toId, int id);
    List<T> findByAttribute(String attribute);
}
