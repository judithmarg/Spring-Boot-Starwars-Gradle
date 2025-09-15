package com.task.starwars.repository.factory;

import com.task.starwars.repository.JediRepository;

public abstract class JediRepoCharGenerator {
    public abstract JediRepository generateJediRepository();

    protected void start(){
        generateJediRepository();
    }
}
