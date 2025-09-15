package com.task.starwars.repository.factory;

import com.task.starwars.repository.JediRepository;
import com.task.starwars.repository.PadawanInMemoryRepository;

public class PadawanRepGenerator extends JediRepoCharGenerator {
    @Override
    public JediRepository generateJediRepository() {
        return PadawanInMemoryRepository.getInstance();
    }
}
