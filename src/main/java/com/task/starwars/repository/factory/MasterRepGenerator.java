package com.task.starwars.repository.factory;

import com.task.starwars.repository.JediRepository;
import com.task.starwars.repository.MasterInMemoryRepository;

public class MasterRepGenerator extends JediRepoCharGenerator {

    @Override
    public JediRepository generateJediRepository() {
        return MasterInMemoryRepository.getInstance();
    }
}
