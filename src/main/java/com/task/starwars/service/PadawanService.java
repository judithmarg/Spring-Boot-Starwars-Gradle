package com.task.starwars.service;

import com.task.starwars.dto.PadawanDto;
import com.task.starwars.dto.PadawanUpdateDto;
import com.task.starwars.entity.Padawan;
import com.task.starwars.exception.CharacterNotFoundException;
import com.task.starwars.exception.CharacterRepeatedException;
import com.task.starwars.exception.DtoBadRequestException;
import com.task.starwars.repository.PadawanInMemoryRepository;
import com.task.starwars.repository.factory.PadawanRepGenerator;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class PadawanService {
    private PadawanInMemoryRepository repository;
    private static PadawanService instance;

    private PadawanService() {
        PadawanRepGenerator generator = new PadawanRepGenerator();
        this.repository = (PadawanInMemoryRepository) generator.generateJediRepository();
    }

    public static PadawanService getInstance() {
        if(PadawanService.instance == null) {
            PadawanService.instance = new PadawanService();
        }
        return PadawanService.instance;
    }

    private PadawanDto convertToDto(Padawan padawan) {
        return new PadawanDto(
                padawan.getId(),
                padawan.getName(),
                padawan.getTotalMissions(),
                padawan.getRank()
        );
    }

    private Padawan convertToPadawan(PadawanDto padawanDto) {
        return new Padawan(
                padawanDto.id(),
                padawanDto.name(),
                padawanDto.totalMissions(),
                padawanDto.rank()
        );
    }

    public List<PadawanDto> getAllPadawans() {
        return this.repository.findAll().stream().map(this::convertToDto).toList();
    }

    public PadawanDto getPadawanById(int id) {
        var padawanFound = this.repository.findById(id);
        if (padawanFound == null) { throw new CharacterNotFoundException(id, "Padawan");}
        return convertToDto(padawanFound);
    }

    public PadawanDto createPadawan(PadawanDto newPadawan) {
        var padawanFound = this.repository.findById(newPadawan.id());
        if (padawanFound != null) { throw new CharacterRepeatedException(newPadawan.id(), "Padawan");}
        return convertToDto(this.repository.save(convertToPadawan(newPadawan)));
    }

    public PadawanDto updatePadawanEntirely(int id, PadawanUpdateDto updatedPadawan) {
        var padawanFound = getPadawanById(id);
        if (padawanFound == null) { throw new CharacterNotFoundException(id, "Padawan");}
        Padawan padawan = new Padawan(
                padawanFound.id(),
                updatedPadawan.name(),
                updatedPadawan.totalMissions(),
                updatedPadawan.rank()
        );
        return convertToDto(this.repository.replace(id, padawan));
    }

    public PadawanDto updatePadawanPartially(int id, PadawanUpdateDto updatedPadawan) {
        var padawanFound = getPadawanById(id);
        if (padawanFound == null) { throw new CharacterNotFoundException(id, "Padawan");}
        Padawan padawanToUpdate = convertToPadawan(padawanFound);
        if (updatedPadawan.name() == null && updatedPadawan.totalMissions() == null && updatedPadawan.rank() == null) {
            throw new DtoBadRequestException("Los campos no estan bien definidos");
        }
        HashMap<Runnable, Object> valuesPadawan = new HashMap<>();
        valuesPadawan.put(()-> padawanToUpdate.setName(updatedPadawan.name()), updatedPadawan.name());
        valuesPadawan.put(()-> padawanToUpdate.setTotalMissions(updatedPadawan.totalMissions()),
                updatedPadawan.totalMissions());
        valuesPadawan.put(()-> padawanToUpdate.setRank(updatedPadawan.rank()), updatedPadawan.rank());

        for (var entry : valuesPadawan.entrySet()) {
            if(entry.getValue() != null) {
                entry.getKey().run();
            }
        }
        return convertToDto(this.repository.replace(id, padawanToUpdate));
    }

    public PadawanDto deletePadawan(int id) {
        var padawanRemoved = this.repository.delete(id);
        if (padawanRemoved == null) { throw new CharacterNotFoundException(id, "Padawan");}
        return convertToDto(padawanRemoved);
    }

    public void assignMaster(int id, int masterId) {
        var padawanFound = getPadawanById(id);
        if (padawanFound == null) { throw new CharacterNotFoundException(id, "Padawan");}
        this.repository.assign(id, masterId);
    }

    public List<PadawanDto> filterByRank(String rank) {
        return this.repository.findByAttribute(rank)
                .stream().map(this::convertToDto)
                .toList();
    }
}
