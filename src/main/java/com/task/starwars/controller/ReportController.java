package com.task.starwars.controller;

import com.task.starwars.dto.ReportDto;
import com.task.starwars.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/academia")
public class ReportController {
    private ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/resumen")
    public ReportDto getReport(){
        return this.reportService.obtainReport();
    }
}
