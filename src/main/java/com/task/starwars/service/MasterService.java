package com.task.starwars.service;

import com.task.starwars.dto.MasterDto;
import com.task.starwars.dto.MasterUpdateDto;
import com.task.starwars.dto.PadawanDto;
import com.task.starwars.entity.Master;
import com.task.starwars.exception.CharacterNotFoundException;
import com.task.starwars.exception.CharacterRepeatedException;
import com.task.starwars.exception.DtoBadRequestException;
import com.task.starwars.repository.MasterInMemoryRepository;
import com.task.starwars.repository.factory.JediRepoCharGenerator;
import com.task.starwars.repository.factory.MasterRepGenerator;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class MasterService {
    private MasterInMemoryRepository repository;
    private static MasterService instance;

    private MasterService() {
        JediRepoCharGenerator generator = new MasterRepGenerator();
        this.repository = (MasterInMemoryRepository) generator.generateJediRepository();
    }

    public static MasterService getInstance() {
        if(MasterService.instance == null) {
            MasterService.instance = new MasterService();
        }
        return MasterService.instance;
    }

    private MasterDto convertToDto(Master master) {
        return new MasterDto(
                master.getId(),
                master.getName(),
                master.getTotalMissions(),
                master.getSpeciality()
        );
    }

    private Master convertToMaster(MasterDto masterDto) {
        return new Master(
                masterDto.id(),
                masterDto.name(),
                masterDto.totalMissions(),
                masterDto.speciality()
        );
    }

    public List<MasterDto> getAllMasters() {
        return this.repository.findAll().stream().map(this::convertToDto).toList();
    }

    public MasterDto getMasterById(int id) {
        var masterFound = this.repository.findById(id);
        if(masterFound == null) { throw new CharacterNotFoundException(id, "Master");}
        return convertToDto(masterFound);
    }

    public MasterDto createMaster(MasterDto newMaster) {
        var masterFound = this.repository.findById(newMaster.id());
        if(masterFound != null) { throw new CharacterRepeatedException(newMaster.id(), "Master");}
        return convertToDto(this.repository.save(convertToMaster(newMaster)));
    }

    public MasterDto updateMasterEntirely(int id, MasterUpdateDto updatedMaster) {
        var masterFound = getMasterById(id);
        if(masterFound == null) { throw new CharacterNotFoundException(id, "Master");}
        Master master = new Master(
                masterFound.id(),
                updatedMaster.name(),
                updatedMaster.totalMissions(),
                updatedMaster.speciality()
        );
        return convertToDto(this.repository.replace(id, master));
    }

    public MasterDto updateMasterPartially(int id, MasterUpdateDto updatedMaster) {
        var masterFound = getMasterById(id);
        if (masterFound == null) { throw new CharacterNotFoundException(id, "Master");}
        Master masterToUpdate = convertToMaster(masterFound);
        if(updatedMaster.name() == null && updatedMaster.totalMissions() == null && updatedMaster.speciality() == null) {
            throw new DtoBadRequestException("Los campos no estan bien definidos");
        }
        HashMap<Runnable, Object> valuesMaster = new HashMap<>();
        valuesMaster.put(()-> masterToUpdate.setName(updatedMaster.name()), updatedMaster.name());
        valuesMaster.put(()-> masterToUpdate.setTotalMissions(updatedMaster.totalMissions()),
                updatedMaster.totalMissions());
        valuesMaster.put(()-> masterToUpdate.setSpeciality(updatedMaster.speciality()), updatedMaster.speciality());

        for (var entry : valuesMaster.entrySet()) {
            if(entry.getValue() != null) {
                entry.getKey().run();
            }
        }
        return convertToDto(this.repository.replace(id, masterToUpdate));
    }

    public MasterDto deleteMaster(int id) {
        var masterRemoved = this.repository.delete(id);
        if (masterRemoved == null) {throw new CharacterNotFoundException(id, "Master");}
        return convertToDto(masterRemoved);
    }

    public void assignPadawan(int id, int padawanId) {
        var masterFound = getMasterById(id);
        if (masterFound == null) { throw new CharacterNotFoundException(id, "Master");}
        this.repository.assign(id, padawanId);
        PadawanService padawanService= PadawanService.getInstance();
        padawanService.assignMaster(padawanId, id);

    }
    public List<PadawanDto> getOwnPadawans(int id) {
        PadawanService padawanService= PadawanService.getInstance();
        return this.repository.getPadawans(id)
                .stream()
                .map(i -> padawanService.getPadawanById(i))
                .toList();
    }

    public List<MasterDto> filterBySpeciality(String speciality) {
        return this.repository.findByAttribute(speciality)
                .stream().map(this::convertToDto)
                .toList();
    }
}
