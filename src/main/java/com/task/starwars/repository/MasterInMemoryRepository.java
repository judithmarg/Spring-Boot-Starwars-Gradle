package com.task.starwars.repository;

import com.task.starwars.entity.Master;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Repository
public class MasterInMemoryRepository implements JediRepository<Master> {
    private List<Master> masters = new LinkedList<>();
    private static MasterInMemoryRepository instance;

    private MasterInMemoryRepository() {}

    public static MasterInMemoryRepository getInstance() {
        if(MasterInMemoryRepository.instance == null) {
            MasterInMemoryRepository.instance = new MasterInMemoryRepository();
            MasterInMemoryRepository.instance.seed();
        }
        return MasterInMemoryRepository.instance;
    }

    @PostConstruct
    void seed() {
        var c1 = new Master(1, "Yoda", 25, "magic");
        var c2 = new Master(2, "Obi-Wan", 45, "lightsaber");
        masters.addAll(List.of(c1, c2));
        MasterInMemoryRepository.instance = this;
    }



    @Override
    public List<Master> findAll() {
        return new ArrayList<>(this.masters);
    }

    @Override
    public Master findById(int id) {
        ArrayList<Master> mastersList = new ArrayList<>(masters);
        return mastersList
                .stream()
                .filter(master -> master.getId() == id)
                .findAny()
                .orElse(null);
    }

    @Override
    public Master save(Master master) {
        return this.masters.add(master)? master : null;
    }

    @Override
    public Master replace(int id, Master master) {
        Master m = this.findById(id);
        int indexFound = this.masters.indexOf(m);
        this.masters.set(indexFound, master);
        return indexFound > -1 ? master: null;
    }

    @Override
    public Master delete(int id) {
        Master m = this.findById(id);
        int indexFound = this.masters.indexOf(m);
        return indexFound > -1? this.masters.remove(indexFound): null;
    }

    @Override
    public void assign(int id, int padawanID) {
        Master m = this.findById(id);
        Set<Integer> learners = m.getPadawans();
        learners.add(padawanID);
        m.setPadawans(learners);
    }

    @Override
    public List<Master> findByAttribute(String attribute) {
        return this.masters
                .stream()
                .filter(m -> m.getSpeciality().equalsIgnoreCase(attribute.trim()))
                .toList();
    }

    public Set<Integer> getPadawans(int id) {
        Master master = this.findById(id);
        return master.getPadawans();
    }
}
