package com.task.starwars.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representa un reporte de la academia")
public record ReportDto(
        @Schema(description = "numero de maestros", example = "2")
        int totalMasters,
        @Schema(description = "numero de aprendices", example = "3")
        int totalPadawans,
        @Schema(description = "promedio de aprendices por maestro", example = "2")
        double averagePadawansPerMaster
) {
}
