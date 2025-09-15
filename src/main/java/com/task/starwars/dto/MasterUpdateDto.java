package com.task.starwars.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Schema(description = "Representa data un maestro jedi para actualizarse")
public record MasterUpdateDto (
        @Schema(description = "nombre del maestro", example = "Han")
        @Size(min = 3, max = 20)
        String name,
        @Schema(description = "numero de misiones", example = "4")
        @Min(0)
        Integer totalMissions,
        @Schema(description = "especialidad", example = "strategy")
        @Size(min = 3, max = 30)
        String speciality
){}
