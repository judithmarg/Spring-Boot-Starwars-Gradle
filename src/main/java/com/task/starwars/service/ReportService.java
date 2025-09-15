package com.task.starwars.service;

import com.task.starwars.dto.ReportDto;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private MasterService masterService;
    private PadawanService padawanService;

    public ReportService() {
        this.masterService = MasterService.getInstance();
        this.padawanService = PadawanService.getInstance();
    }

    private double getAveragePadPerMaster() {
        int totalPadPerMaster = masterService.getAllMasters()
                .stream()
                .map(masterDto -> masterService.getOwnPadawans(masterDto.id()).size())
                .reduce(0, (acc, numPadawans) -> acc + numPadawans);
        double average = (double) totalPadPerMaster / masterService.getAllMasters().size();
        return Math.round(average * 1000) / 1000.0;
    }
    public ReportDto obtainReport() {
        return new ReportDto(
                masterService.getAllMasters().size(),
                padawanService.getAllPadawans().size(),
                getAveragePadPerMaster()
        );
    }
}
