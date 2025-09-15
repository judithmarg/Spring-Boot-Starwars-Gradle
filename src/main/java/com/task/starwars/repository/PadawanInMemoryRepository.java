package com.task.starwars.repository;

import com.task.starwars.entity.Padawan;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Repository
public class PadawanInMemoryRepository implements JediRepository<Padawan>{
    private List<Padawan> padawans = new LinkedList<>();
    private static PadawanInMemoryRepository instance;

    private PadawanInMemoryRepository() { }

    public static PadawanInMemoryRepository getInstance() {
        if(PadawanInMemoryRepository.instance == null) {
            PadawanInMemoryRepository.instance = new PadawanInMemoryRepository();
            PadawanInMemoryRepository.instance.seed(); //singleton con seed
        }
        return PadawanInMemoryRepository.instance;
    }

    @PostConstruct
    void seed() {
        instance = this;
        var c1 = new Padawan(100, "Anakin", 17, "knight");
        var c2 = new Padawan(101, "Rey", 5, "apprentice");
        var c3 = new Padawan(102, "Ezra", 12, "knight");
        List.of(c1, c2, c3).forEach(p -> padawans.add(p));
    }

    @Override
    public List<Padawan> findAll() {
        return new ArrayList<>(this.padawans);
    }

    @Override
    public Padawan findById(int id) {
        ArrayList<Padawan> padawanArrayList = new ArrayList<>(this.padawans);
        return padawanArrayList
                .stream()
                .filter(padawan -> padawan.getId() == id)
                .findAny()
                .orElse(null);
    }

    @Override
    public Padawan save(Padawan jedi) {
        return this.padawans.add(jedi)? jedi : null;
    }

    @Override
    public Padawan replace(int id, Padawan jedi) {
        Padawan p = findById(id);
        int indexFound = this.padawans.indexOf(p);
        this.padawans.set(indexFound, jedi);
        return indexFound > -1? jedi: null;
    }

    @Override
    public Padawan delete(int id) {
        Padawan p = findById(id);
        int indexFound = this.padawans.indexOf(p);
        return indexFound > -1? this.padawans.remove(indexFound): null;
    }

    @Override
    public void assign(int toId, int masterId) {
        Padawan padawanFound = findById(toId);
        padawanFound.setJediMaster(masterId);
    }

    @Override
    public List<Padawan> findByAttribute(String attribute) {
        return this.padawans
                .stream()
                .filter(p -> p.getRank().equalsIgnoreCase(attribute.trim()))
                .toList();
    }
}
